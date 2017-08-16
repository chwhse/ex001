package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")  
public class SBoardController {
	private static final Logger logger = 
			LoggerFactory.getLogger(SBoardController.class);
	
	@Resource(name="uploadPath")// id로(DI) 주입받을때사용
	private String uploadPath;
	
	
	@Autowired
	BoardService service;
	
	@RequestMapping(value="listPage", method=RequestMethod.GET)
	public String listPage(@ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception{
		logger.info("=================listPage===============");
		model.addAttribute("list",service.listSearch(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchCount(cri));
		model.addAttribute("pageMaker",pageMaker);

		return "sboard/listPage";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerGET() throws Exception{
		return "sboard/register";
		
	}
	
/*	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, Criteria cri) throws Exception{
		service.regist(vo);
		return "redirect:listPage?page="+cri.getPage();
		
	}*/
	@RequestMapping(value="register", method=RequestMethod.POST)
	   public String registerPOST(BoardVO vo, List<MultipartFile> imgFiles) throws Exception{
	      
	      ArrayList<String> list = new ArrayList<>();
	      
	      for(MultipartFile file : imgFiles){
	         logger.info("filename : " + file.getOriginalFilename());
	      
	         String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
	      
	         list.add(thumb);
	      }
	      vo.setFiles(list.toArray(new String[list.size()]));
	      
	      service.regist(vo);
	      
	      logger.info(vo.toString());
	      //return "sboard/success";
	      return "redirect:listPage";
	  }	
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public String read(int bno, Model model,@ModelAttribute("cri")SearchCriteria cri, boolean isModify) throws Exception{
		BoardVO vo = service.read(bno);
		if(isModify != true){
			vo.setViewcnt(vo.getViewcnt()+1);
			service.modify(vo);
		}
		
		model.addAttribute("board", vo);
		return "sboard/read";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int bno,Criteria cri) throws Exception{
		service.remove(bno);
		
		return "redirect:listPage?page="+cri.getPage();
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modifyGET(int bno,Model model,@ModelAttribute("cri")SearchCriteria cri) throws Exception{
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
		return "sboard/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPOST(String[] deletefiles,
							List<MultipartFile> modImgFiles,
							BoardVO vo,
							SearchCriteria cri,
							RedirectAttributes rttr) 	throws Exception{
		
		System.out.println("=======MOD POST========");
		
		ArrayList<String> list = new ArrayList<>();
	      for(MultipartFile file : modImgFiles){
	         logger.info("filename : " + file.getOriginalFilename());
	         logger.info("size : " + file.getSize());
	         if(file.getSize()==0)
	        	 continue;
	         
	         String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
	      
	         list.add(thumb);
	      }
	     vo.setFiles(list.toArray(new String[list.size()]));
	     
	     service.modifyUpload(vo,deletefiles);
	     
	     if(deletefiles != null){
	    	 for(String file : deletefiles){
	    		 UploadFileUtils.deletefile9(uploadPath, file);
	    	 }
	     }
	     rttr.addAttribute("isModify",true);
	     rttr.addAttribute("bno", vo.getBno());
	     rttr.addAttribute("page", cri.getPage());
	     rttr.addAttribute("perPageNum", cri.getPerPageNum());
	     rttr.addAttribute("searchType", cri.getSearchType());
	     rttr.addAttribute("keyword", cri.getKeyword());
		
		
		
		return "redirect:read";
		
	}
	

	@ResponseBody
	   @RequestMapping(value = "displayFile") // displayFile?filename=##############.jpg
	   public ResponseEntity<byte[]> displayFile(String filename) throws IOException {
	      ResponseEntity<byte[]> entity = null;

	      InputStream in = null;

	      logger.info("----displayFile : " + filename);

	      try {
	         String formatName = filename.substring(filename.lastIndexOf(".") + 1);
	         MediaType mType = MediaUtils.getMediaType(formatName);
	         HttpHeaders header = new HttpHeaders();
	         header.setContentType(mType);

	         in = new FileInputStream(uploadPath + "/" + filename);
	         entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), header, HttpStatus.CREATED);
	      } catch (Exception e) {
	         e.printStackTrace();
	         entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);

	      } finally {
	         in.close();
	      }
	      return entity;
	}
	   
	   
	   @ResponseBody             //이거 안넣을려면 맨위에 컨트롤러에 레스트컨트롤러로 
	   @RequestMapping(value="deleteFile", method=RequestMethod.POST)
	   public ResponseEntity<String> deleteFile(String filename){
	      ResponseEntity<String> entity = null;
	      logger.info("-----------------deleteFile POST---------------------");
	      logger.info("filename : " + filename);
	      
	      try{
	         //thumbnail
	         File file = new File(uploadPath + filename);   // /가 안붙어잇으면 넣어야됨 넣어있어서 안넣어도됨
	          file.delete();
	          
	          //원본
	          String front = filename.substring(0, 12); //0~11자리까지
	          String end = filename.substring(14);   //14~끝까지
	          String originalName = front + end; 
	          
	          File file2 = new File(uploadPath + originalName);
	          file2.delete();
	          
	          
	          
	         entity = new ResponseEntity<String>("success", HttpStatus.OK);
	      }catch (Exception e) {
	         // TODO: handle exception
	         entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
	      }
	      return entity;
	   }
}
