package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO dao;
	
	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
         if(board.getFiles() != null){//보호처리
        	 for(String fullname : board.getFiles()){
                 dao.addAttach(fullname);
              }
         }
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		BoardVO vo = dao.read(bno);
		List<String> list = dao.getAttach(bno);
		vo.setFiles(list.toArray(new String[list.size()]));
		return vo;
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);
	}
	
	@Override
	public void remove(Integer bno) throws Exception {
		dao.delete(bno);
        dao.deleteAttachByBno(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int totalCount() throws Exception {
		return dao.totalCount();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int searchCount(SearchCriteria cri) throws Exception {
		return dao.searchCount(cri);
	}

	@Transactional
	@Override
	public void modifyUpload(BoardVO board, String[] delFiles) throws Exception {
		// TODO Auto-generated method stub
		
		if(delFiles != null){ // db에서 지울파일을 찾아서 지움
			for(String file: delFiles){
				dao.deleteAttachByFullName(board.getBno(), file);
			}
		}
		
		if(board.getFiles() == null)//보호처리
			return;
		
		for(String fullname : board.getFiles()){//추가된 파일은  db에 넣어줌		
			dao.addAttachByBno(fullname, board.getBno());
		}		
		dao.update(board);//tbl_board에 수정처리
	}

	@Override
	public List<String> getAttachList(int bno) throws Exception{
		return dao.getAttach(bno);
	}



}
