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
}
