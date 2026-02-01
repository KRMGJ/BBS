package egovframework.let.bbs.mstr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.bbs.mstr.dao.BbsMasterDAO;
import egovframework.let.bbs.mstr.service.BbsMasterService;
import egovframework.let.bbs.mstr.vo.BbsMasterVO;

@Service("bbsMasterService")
public class BbsMasterServiceImpl implements BbsMasterService {

	@Resource(name = "bbsMasterDAO")
	private BbsMasterDAO bbsMasterDAO;

	/**
	 * 게시판 마스터 목록을 조회한다.
	 */
	@Override
	public List<BbsMasterVO> selectBbsMasterList(BbsMasterVO searchVO) throws Exception {
		return bbsMasterDAO.selectBbsMasterList(searchVO);
	}

	/**
	 * 게시판 마스터 총 갯수를 조회한다.
	 */
	@Override
	public int selectBbsMasterListCnt(BbsMasterVO searchVO) throws Exception {
		return bbsMasterDAO.selectBbsMasterListCnt(searchVO);
	}

}
