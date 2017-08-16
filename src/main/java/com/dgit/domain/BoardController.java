package com.dgit.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController { 
	private static final Logger logger = 
			LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerGET() throws Exception{
		return "board/register";
		
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception{
		service.regist(vo);
		return "redirect:listAll";
		
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public String listAll(Model model) throws Exception{
		List<BoardVO> list = service.listAll();
		model.addAttribute("list",list);
		return "board/listAll";
	}
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public String listPage(Criteria cri, Model model) throws Exception{
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list",list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());
		
		model.addAttribute("pageMaker",pageMaker);
		
		return "board/listPage";
	}
	
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public String read(int bno, Model model,@ModelAttribute("cri")Criteria cri, boolean isModify) throws Exception{
		BoardVO vo = service.read(bno);
		if(isModify != true){
			vo.setViewcnt(vo.getViewcnt()+1);
			service.modify(vo);
		}
		model.addAttribute("board", vo);
		return "board/read";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int bno,Criteria cri) throws Exception{
		service.remove(bno);
		return "redirect:listPage?page="+cri.getPage();
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modifyGET(int bno, Model model,@ModelAttribute("cri")Criteria cri) throws Exception{
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPOST(BoardVO vo/*, String regdateStr*/,Criteria cri, Model model) throws Exception{
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date regdate = df.parse(regdateStr);
		vo.setRegdate(regdate);*/
		model.addAttribute("isModify",true);
		service.modify(vo);
		return "redirect:read?bno="+vo.getBno()+"&page="+cri.getPage();
		
	}
	
}
