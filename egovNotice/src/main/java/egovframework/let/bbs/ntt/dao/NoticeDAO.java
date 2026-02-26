package egovframework.let.bbs.ntt.dao;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.ntt.vo.NoticeLikeVO;
import egovframework.let.bbs.ntt.vo.NoticeVO;

@Repository("noticeDAO")
public class NoticeDAO extends EgovAbstractMapper {
	/**
	 * 공지사항 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 목록
	 */
	public List<NoticeVO> selectNoticeList(NoticeVO searchVO) {
		return selectList("NoticeDAO.selectNoticeList", searchVO);
	}

	/**
	 * 공지사항 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 총 갯수
	 */
	public int selectNoticeListTotCnt(NoticeVO searchVO) {
		return selectOne("NoticeDAO.selectNoticeListTotCnt", searchVO);
	}

	/**
	 * 공지사항 상단고정 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 상단고정 목록
	 */
	public List<NoticeVO> selectNoticePinnedList(NoticeVO searchVO) {
		return selectList("NoticeDAO.selectNoticePinnedList", searchVO);
	}

	/**
	 * 공지사항을 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 VO
	 * @return nttId - 등록된 게시물ID
	 */
	public int insertNotice(NoticeVO vo) {
		return insert("NoticeDAO.insertNotice", vo);
	}

	/**
	 * 공지사항 상세를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 VO
	 * @return 공지사항 상세정보
	 */
	public NoticeVO selectNoticeDetail(NoticeVO vo) {
		return selectOne("NoticeDAO.selectNoticeDetail", vo);
	}

	/**
	 * 공지사항을 수정한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 VO
	 * @return void
	 */
	public int updateInqireCo(NoticeVO vo) {
		return update("NoticeDAO.updateInqireCo", vo);
	}

	/** 다운로드 소속검증용 */
	public String selectAtchFileIdByNttId(NoticeVO vo) {
		return selectOne("NoticeDAO.selectAtchFileIdByNttId", vo);
	}

	/**
	 * 공지사항을 수정한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 VO
	 */
	public void updateNotice(NoticeVO vo) {
		update("NoticeDAO.updateNotice", vo);
	}

	/**
	 * 공지사항을 삭제한다.
	 * 
	 * @param nttIdList - 삭제할 공지사항 ID 목록
	 */
	public void deleteNoticeList(List<String> nttIdList) {
		delete("NoticeDAO.deleteNoticeList", nttIdList);
	}

	/**
	 * 공지사항 트리 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 트리 목록
	 */
	public List<NoticeVO> selectNoticeTreeList(NoticeVO searchVO) {
		return selectList("NoticeDAO.selectNoticeTreeList", searchVO);
	}

	/**
	 * 공지사항 부모 트리 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 부모 트리 목록
	 */
	public List<NoticeVO> selectNoticeParentList(NoticeVO searchVO) {
		return selectList("NoticeDAO.selectNoticeParentList", searchVO);
	}

	/**
	 * 공지사항 자식 트리 목록을 조회한다.
	 * 
	 * @param paramMap - 조회할 정보가 담긴 Map
	 * @return 공지사항 자식 트리 목록
	 */
	public List<NoticeVO> selectNoticeChildTreeList(Map<String, Object> paramMap) {
		return selectList("NoticeDAO.selectNoticeChildTreeList", paramMap);
	}

	/**
	 * 공지사항 부모 트리 목록 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 부모 트리 목록 총 갯수
	 */
	public int selectNoticeParentListTotCnt(NoticeVO searchVO) {
		return selectOne("NoticeDAO.selectNoticeParentListTotCnt", searchVO);
	}

	/**
	 * 공지사항 조회이력을 등록한다.
	 * 
	 * @param vo       - 조회할 정보가 담긴 VO
	 * @param viewerId - 조회자 ID
	 */
	public void insertViewHistory(NoticeVO vo, String viewerId) {
		Map<String, Object> paramMap = Map.of("cvlId", vo.getCvlId(), "nttId", vo.getNttId(), "viewerId", viewerId);
		insert("NoticeDAO.insertViewHistory", paramMap);
	}

	/**
	 * 공지사항 조회이력을 조회한다.
	 * 
	 * @param vo       - 조회할 정보가 담긴 VO
	 * @param viewerId - 조회자 ID
	 * @return 조회이력 수
	 */
	public int selectViewHistory(NoticeVO vo, String viewerId) {
		return selectOne("NoticeDAO.selectViewHistory", Map.of("nttId", vo.getNttId(), "viewerId", viewerId));
	}

	/**
	 * 공지사항 좋아요를 조회한다.
	 * 
	 * @param vo     - 조회할 정보가 담긴 VO
	 * @param userId - 좋아요를 누른 사용자 ID
	 * @return NoticeLikeVO - 좋아요 정보가 담긴 VO
	 */
	public NoticeLikeVO selectLike(NoticeVO vo, String userId) {
		return selectOne("NoticeDAO.selectLike", Map.of("nttId", vo.getNttId(), "userId", userId));
	}

	/**
	 * 공지사항 좋아요를 등록한다.
	 * 
	 * @param vo     - 등록할 정보가 담긴 VO
	 * @param userId - 좋아요를 누른 사용자 ID
	 */
	public void insertLike(NoticeVO vo, String userId) {
		insert("NoticeDAO.insertLike", Map.of("likeId", vo.getLikeId(), "nttId", vo.getNttId(), "userId", userId));
	}

	/**
	 * 공지사항 좋아요 수를 업데이트한다.
	 * 
	 * @param vo - 업데이트할 정보가 담긴 VO
	 * @param i  - 업데이트할 좋아요 수 (증가 또는 감소)
	 */
	public void updateLikeCount(NoticeVO vo, int i) {
		update("NoticeDAO.updateLikeCount", Map.of("nttId", vo.getNttId(), "likeCount", i));
	}

	/**
	 * 공지사항 좋아요 사용 여부를 업데이트한다.
	 * 
	 * @param vo     - 업데이트할 정보가 담긴 VO
	 * @param userId - 좋아요를 누른 사용자 ID
	 * @param string - 업데이트할 사용 여부 (Y/N)
	 */
	public void updateLikeUseAt(NoticeVO vo, String userId, String string) {
		update("NoticeDAO.updateLikeUseAt", Map.of("nttId", vo.getNttId(), "userId", userId, "useAt", string));
	}

	/**
	 * 공지사항 좋아요 수를 조회한다.
	 * 
	 * @param nttId    - 공지사항 ID
	 * @param viewerId - 조회자 ID
	 * @return 좋아요 수
	 */
	public int selectNoticeLikeCountByUser(String nttId, String viewerId) {
		return selectOne("NoticeDAO.selectNoticeLikeCountByUser", Map.of("nttId", nttId, "userId", viewerId));
	}
}
