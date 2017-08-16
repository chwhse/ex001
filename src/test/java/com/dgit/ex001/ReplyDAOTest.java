package com.dgit.ex001;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class ReplyDAOTest {
	@Autowired
	private ReplyDAO dao;
	

	@Test
	public void testInsertReply()throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBno(421);
		vo.setReplytext("댓글001");
		vo.setReplyer("댓글러001");
		
		dao.create(vo);
	
	}

	/*	
	@Test
	public void testListReply()throws Exception {
		
		List<ReplyVO> vo = dao.list(421);
		
		for(ReplyVO rvo : vo){
			System.out.println(rvo);
		}
		
	
	}

	@Test
	public void testUpdateReply()throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setRno(6);
		vo.setReplytext("수정된 댓글02");
		vo.setReplyer("수정한사람02");
		
		dao.update(vo);
	}
	
	
	@Test
	public void testDeleteReply()throws Exception {
		
		dao.delete(8);
		
		
	}
*/	

}
