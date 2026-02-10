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

	/**
	 * 자료실 글을 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 DataVO
	 */
	public void insertData(DataVO vo) {
		insert("DataDAO.insertData", vo);
	}

	/**
	 * 자료실 조회수를 증가한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 */
	public void updateViewCnt(DataVO searchVO) {
		update("DataDAO.updateViewCnt", searchVO);

	}

	/**
	 * 자료실 글 상세를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 DataVO
	 * @return 자료실 글 상세
	 */
	public DataVO selectDataDetail(DataVO searchVO) {
		return selectOne("DataDAO.selectDataDetail", searchVO);
	}

	/**
	 * 자료실 글에 첨부된 파일 ID를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 DataVO
	 * @return 첨부파일 ID
	 */
	public String selectAtchFileIdByNttId(DataVO vo) {
		return selectOne("DataDAO.selectAtchFileIdByNttId", vo);
	}
}
