package com.dgit.persistence;

import com.dgit.domain.MemberVO;

public interface MemberDao {
	public String readTime() throws Exception;
	public void createMember(MemberVO vo) throws Exception;
	public MemberVO readMember(String userid) throws Exception;
	public MemberVO login(String userid, String userpw) throws Exception;
	public void updateMember(MemberVO vo) throws Exception;
	public void deleteMember(String userid) throws Exception;
}
