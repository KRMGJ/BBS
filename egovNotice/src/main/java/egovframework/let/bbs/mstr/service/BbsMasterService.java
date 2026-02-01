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

	/**
	 * 게시판 마스터 정보를 등록한다.
	 * 
	 * @param bbsMasterVO - 등록할 정보가 담긴 VO
	 * @return String - 등록한 게시판 ID
	 * @throws Exception
	 */
	String insertBbsMaster(BbsMasterVO vo) throws Exception;

	/**
	 * 게시판 마스터 상세정보를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 상세정보
	 * @throws Exception
	 */
	BbsMasterVO selectBbsMasterDetail(BbsMasterVO vo) throws Exception;

	/**
	 * 게시판 마스터 정보를 수정한다.
	 * 
	 * @param bbsMasterVO - 수정할 정보가 담긴 VO
	 * @return int - 수정 결과
	 * @throws Exception
	 */
	int updateBbsMaster(BbsMasterVO vo) throws Exception;
}
