package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.ReplyVO;
import com.dgit.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	ReplyService service;
	
	//replies/all/{bno}
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	// ResponseEntity<> 오류날 가능성을 고려해 리턴값으로 오류처리랑 값 넘김 같이 하기위함 
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try{
			List<ReplyVO> list = service.listReply(bno); 
			entity = new ResponseEntity<List<ReplyVO>>( list, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 error
		}
		return entity;
	}
	
	@Transactional
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseEntity<String> add(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("replies ADD");
		
		try{
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST); // 400 error
		}
		return entity;
	}
	
	@Transactional
	@RequestMapping(value="/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable Integer rno){
		ResponseEntity<String> entity = null;
		
		logger.info("replies remove DELETE");
			
		try{
			service.removeReply(rno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/{rno}", method=RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("replies update PUT");
		
		try{
			vo.setRno(rno);
			service.modifyReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		
		
		return entity;
	}
	
}
