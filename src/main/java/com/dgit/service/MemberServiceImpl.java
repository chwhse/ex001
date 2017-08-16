package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao dao;
	
	@Override
	public void createMember(MemberVO vo) throws Exception {
		dao.createMember(vo);
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		return dao.readMember(userid);
	}

	@Override
	public void updateMember(MemberVO vo) throws Exception {
		dao.updateMember(vo);
	}

	@Override
	public void deleteMember(String userid) throws Exception {
		dao.deleteMember(userid);
	}

	@Override
	public MemberVO login(String userid, String userpw) throws Exception {
		return dao.login(userid, userpw);
	}

}
