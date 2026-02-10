package egovframework.let.bbs.dta.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.let.bbs.dta.dao.DataDAO;
import egovframework.let.bbs.dta.service.DataService;
import egovframework.let.bbs.dta.vo.DataVO;

@Service("dataService")
public class DataServiceImpl implements DataService {

	@Resource(name = "dataDAO")
	private DataDAO dataDAO;

	@Resource(name = "egovNttIdGnrService")
	private EgovIdGnrService nttIdGnrService;

	/** 자료실 목록을 조회한다. */
	@Override
	public List<DataVO> selectDataList(DataVO searchVO) throws Exception {
		return dataDAO.selectDataList(searchVO);
	}

	/** 자료실 목록 총 갯수를 조회한다. */
	@Override
	public int selectDataListCnt(DataVO searchVO) throws Exception {
		return dataDAO.selectDataListCnt(searchVO);
	}

	/** 자료실 글을 등록한다. */
	@Override
	public String insertData(DataVO vo) throws Exception {
		vo.setNttId(nttIdGnrService.getNextStringId());
		dataDAO.insertData(vo);
		return vo.getNttId();
	}

	/** 자료실 글 상세를 조회한다. */
	@Override
	public DataVO selectDataDetail(DataVO searchVO, boolean increase) throws Exception {
		if (increase) {
			dataDAO.updateViewCnt(searchVO);
		}
		return dataDAO.selectDataDetail(searchVO);
	}

	@Override
	public String selectAtchFileIdByNttId(DataVO vo) throws Exception {
		return dataDAO.selectAtchFileIdByNttId(vo);
	}
}
