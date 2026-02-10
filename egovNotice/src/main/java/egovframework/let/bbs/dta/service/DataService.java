package egovframework.let.bbs.dta.service;

import java.util.List;

import egovframework.let.bbs.dta.vo.DataVO;

public interface DataService {
	List<DataVO> selectDataList(DataVO searchVO) throws Exception;

	int selectDataListCnt(DataVO searchVO) throws Exception;
}
