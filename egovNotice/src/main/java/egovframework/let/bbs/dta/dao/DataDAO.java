package egovframework.let.bbs.dta.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.dta.vo.DataVO;

@Repository("dataDAO")
public class DataDAO extends EgovAbstractMapper {

	/**
	 * 자료실 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @return 자료실 목록
	 * @throws Exception
	 */
	public List<DataVO> selectDataList(DataVO searchVO) throws Exception {
		return selectList("DataDAO.selectDataList", searchVO);
	}

	/**
	 * 자료실 목록 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @return 자료실 목록 총 갯수
	 * @throws Exception
	 */
	public int selectDataListCnt(DataVO searchVO) throws Exception {
		return selectOne("DataDAO.selectDataListCnt", searchVO);
	}
}
