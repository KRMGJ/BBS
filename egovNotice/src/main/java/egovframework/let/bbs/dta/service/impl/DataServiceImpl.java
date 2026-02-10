package egovframework.let.bbs.dta.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.bbs.dta.dao.DataDAO;
import egovframework.let.bbs.dta.service.DataService;
import egovframework.let.bbs.dta.vo.DataVO;

@Service("dataService")
public class DataServiceImpl implements DataService {

	@Resource(name = "dataDAO")
	private DataDAO dataDAO;

	@Override
	public List<DataVO> selectDataList(DataVO searchVO) throws Exception {
		return dataDAO.selectDataList(searchVO);
	}

	@Override
	public int selectDataListCnt(DataVO searchVO) throws Exception {
		return dataDAO.selectDataListCnt(searchVO);
	}

}
