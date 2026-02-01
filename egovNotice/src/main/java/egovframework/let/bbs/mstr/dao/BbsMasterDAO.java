package egovframework.let.bbs.mstr.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.mstr.vo.BbsMasterVO;

@Repository("bbsMasterDAO")
public class BbsMasterDAO extends EgovAbstractMapper {

	/**
	 * 게시판 마스터 목록을 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 목록
	 */
	public List<BbsMasterVO> selectBbsMasterList(BbsMasterVO vo) {
		return selectList("BbsMasterDAO.selectBbsMasterList", vo);
	}

	/**
	 * 게시판 마스터 총 갯수를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 총 갯수
	 */
	public int selectBbsMasterListCnt(BbsMasterVO vo) {
		return selectOne("BbsMasterDAO.selectBbsMasterListCnt", vo);
	}

	/**
	 * 게시판 마스터 정보를 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 VO
	 */
	public void insertBbsMaster(BbsMasterVO vo) {
		insert("BbsMasterDAO.insertBbsMaster", vo);
	}

	/**
	 * 게시판 마스터 상세정보를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 VO
	 * @return 게시판 마스터 상세정보
	 */
	public BbsMasterVO selectBbsMasterDetail(BbsMasterVO vo) {
		return selectOne("BbsMasterDAO.selectBbsMasterDetail", vo);
	}

	/**
	 * 게시판 마스터 정보를 수정한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 VO
	 * @return int - 수정 결과
	 */
	public int updateBbsMaster(BbsMasterVO vo) {
		return update("BbsMasterDAO.updateBbsMaster", vo);
	}
}
