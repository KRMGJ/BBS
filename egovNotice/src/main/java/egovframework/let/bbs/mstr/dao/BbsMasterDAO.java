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
}
