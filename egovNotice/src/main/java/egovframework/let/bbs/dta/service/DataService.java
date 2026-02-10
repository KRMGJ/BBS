package egovframework.let.bbs.dta.service;

import java.util.List;

import egovframework.let.bbs.dta.vo.DataVO;

public interface DataService {
	/**
	 * 자료실 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @return 자료실 목록
	 * @throws Exception
	 */
	List<DataVO> selectDataList(DataVO searchVO) throws Exception;

	/**
	 * 자료실 목록 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @return 자료실 목록 총 갯수
	 * @throws Exception
	 */
	int selectDataListCnt(DataVO searchVO) throws Exception;

	/**
	 * 자료실 글을 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 DataVO
	 * @return 등록한 글의 NTT_ID
	 * @throws Exception
	 */
	String insertData(DataVO vo) throws Exception;

	/**
	 * 자료실 글 상세를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @param increase - 조회수 증가 여부
	 * @return 자료실 글 상세
	 * @throws Exception
	 */
	DataVO selectDataDetail(DataVO searchVO, boolean increase) throws Exception;

	/**
	 * NTT_ID로 첨부파일 ID를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 DataVO
	 * @return 첨부파일 ID
	 * @throws Exception
	 */
	String selectAtchFileIdByNttId(DataVO vo) throws Exception;
}
