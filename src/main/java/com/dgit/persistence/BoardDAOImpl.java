package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Inject
	private SqlSession session;
	private static String namespace = "com.dgit.persistence.BoardDAO";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		session.insert(namespace+".create", vo);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return session.selectOne(namespace+".read", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		session.update(namespace+".update", vo);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(namespace+".delete", bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return session.selectList(namespace+".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		if(page <= 0){
			page = -1;
		}
		page = (page-1) * 10; 	// 해당 page의 시작 게시물 index를 구함
		return session.selectList(namespace+".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return session.selectList(namespace+".listCriteria", cri);
	}

	@Override
	public int totalCount() throws Exception {
		return session.selectOne(namespace+".totalCount");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return session.selectList(namespace+".listSearch", cri);
	}

	@Override
	public int searchCount(SearchCriteria cri) throws Exception {
		return session.selectOne(namespace+".searchCount",cri);
	}

	@Override	/*안씀*/
	public void updateReplyCnt(int bno, int amount) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		session.update(namespace+".updateReplyCnt", map);
	}


	@Override
	public void addAttach(String fullname) throws Exception {
		session.insert(namespace +".addAttach", fullname);
	}
	
	@Override
	public List<String> getAttach(int bno) throws Exception {	    
		return session.selectList(namespace+".getAttach", bno);
	}

	@Override
	public void addAttachByBno(String fullname, int bno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullname", fullname);
		session.insert(namespace + ".addAttachByBno", map);
	}

	@Override
	public void deleteAttachByFullName(int bno, String fullname) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullname", fullname);
		session.delete(namespace + ".deleteAttachByFullName", map);
	}

	@Override
	public void deleteAttachByBno(int bno) throws Exception {
		session.delete(namespace + ".deleteAttachByBno", bno);
	}

 
}
