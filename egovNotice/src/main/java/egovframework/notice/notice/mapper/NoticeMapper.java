package egovframework.notice.notice.mapper;

import java.util.List;

import egovframework.notice.notice.service.NoticeVO;

public interface NoticeMapper {
	/**
	 * 공지사항 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 목록
	 */
	List<NoticeVO> selectNoticeList(NoticeVO searchVO);

	/**
	 * 공지사항 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 총 갯수
	 */
	int selectNoticeListTotCnt(NoticeVO searchVO);

	/**
	 * 공지사항 상단고정 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 상단고정 목록
	 */
	List<NoticeVO> selectNoticePinnedList(NoticeVO searchVO);

	/**
	 * 공지사항을 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 VO
	 * @return nttId - 등록된 게시물ID
	 */
	int insertNotice(NoticeVO vo);
}
