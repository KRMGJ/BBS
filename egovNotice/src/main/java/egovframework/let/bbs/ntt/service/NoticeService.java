package egovframework.let.bbs.ntt.service;

import java.util.List;

import egovframework.let.bbs.ntt.vo.NoticeVO;

public interface NoticeService {
	/**
	 * 공지사항 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 목록
	 * @throws Exception
	 */
	List<NoticeVO> selectNoticeList(NoticeVO searchVO) throws Exception;

	/**
	 * 공지사항 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 총 갯수
	 * @throws Exception
	 */
	int selectNoticeListTotCnt(NoticeVO searchVO) throws Exception;

	/**
	 * 공지사항 상단고정 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 상단고정 목록
	 * @throws Exception
	 */
	List<NoticeVO> selectNoticePinnedList(NoticeVO searchVO) throws Exception;

	/**
	 * 공지사항을 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 VO
	 * @return 등록된 게시물ID
	 * @throws Exception
	 */
	String insertNotice(NoticeVO vo) throws Exception;
}
