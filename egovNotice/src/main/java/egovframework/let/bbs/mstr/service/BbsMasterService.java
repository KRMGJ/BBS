package egovframework.let.bbs.mstr.service;

import java.util.List;

import egovframework.let.bbs.mstr.vo.BbsMasterVO;

public interface BbsMasterService {

	/**
	 * 게시판 마스터 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 목록
	 * @throws Exception
	 */
	List<BbsMasterVO> selectBbsMasterList(BbsMasterVO searchVO) throws Exception;

	/**
	 * 게시판 마스터 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 총 갯수
	 * @throws Exception
	 */
	int selectBbsMasterListCnt(BbsMasterVO searchVO) throws Exception;
}
