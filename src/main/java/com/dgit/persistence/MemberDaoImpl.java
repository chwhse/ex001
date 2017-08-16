package com.dgit.persistence;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	@Autowired
	private SqlSession sqlSession;

	private static final String namespace = "com.dgit.persistence.MemberDao";
	
	@Override
	public String readTime() throws Exception {
		Object object = sqlSession.selectOne(namespace+".getTime");
		return (String) object;
	}

	@Override
	public void createMember(MemberVO vo) throws Exception {
		sqlSession.insert(namespace+".insertMember", vo);
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		Object object = sqlSession.selectOne(namespace+".readMember", userid);
		return (MemberVO) object;
	}

	@Override
	public MemberVO login(String userid, String userpw) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		return sqlSession.selectOne(namespace+".login", map);
	}

	@Override
	public void updateMember(MemberVO vo) throws Exception {
		sqlSession.update(namespace+".updateMember", vo);
		
	}

	@Override
	public void deleteMember(String userid) throws Exception {
		sqlSession.delete(namespace+".deleteMember", userid);
		
	}
	
	
	
}
