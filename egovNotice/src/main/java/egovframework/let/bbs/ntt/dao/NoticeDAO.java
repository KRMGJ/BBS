package egovframework.let.bbs.ntt.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

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
}
