package egovframework.let.bbs.dta.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.dta.vo.DataVO;

@Repository("dataDAO")
public class DataDAO extends EgovAbstractMapper {

	public List<DataVO> selectDataList(DataVO searchVO) throws Exception {
		return selectList("DataDAO.selectDataList", searchVO);
	}

	public int selectDataListCnt(DataVO searchVO) throws Exception {
		return selectOne("DataDAO.selectDataListCnt", searchVO);
	}
}
