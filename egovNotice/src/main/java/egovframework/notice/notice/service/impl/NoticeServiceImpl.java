package egovframework.notice.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.notice.notice.mapper.NoticeMapper;
import egovframework.notice.notice.service.NoticeService;
import egovframework.notice.notice.service.NoticeVO;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource(name = "noticeMapper")
	private NoticeMapper noticeMapper;

	@Resource(name = "noticeNttIdGnrService")
	private EgovIdGnrService noticeNttIdGnrService;

	/**
	 * 공지사항 목록을 조회한다.
	 */
	@Override
	public List<NoticeVO> selectNoticeList(NoticeVO searchVO) throws Exception {
		return noticeMapper.selectNoticeList(searchVO);
	}

	/**
	 * 공지사항 총 갯수를 조회한다.
	 */
	@Override
	public int selectNoticeListTotCnt(NoticeVO searchVO) throws Exception {
		return noticeMapper.selectNoticeListTotCnt(searchVO);
	}

	/**
	 * 공지사항 상단고정 목록을 조회한다.
	 */
	@Override
	public List<NoticeVO> selectNoticePinnedList(NoticeVO searchVO) throws Exception {
		return noticeMapper.selectNoticePinnedList(searchVO);
	}

	/**
	 * 공지사항을 등록한다.
	 */
	@Override
	@Transactional
	public String insertNotice(NoticeVO vo) throws Exception {
		if (vo.getUseAt() == null || vo.getUseAt().isEmpty()) {
			vo.setUseAt("Y");
		}
		if (vo.getDelAt() == null || vo.getDelAt().isEmpty()) {
			vo.setDelAt("N");
		}
		if (vo.getNoticeAt() == null || vo.getNoticeAt().isEmpty()) {
			vo.setNoticeAt("N");
		}

		String nextId = noticeNttIdGnrService.getNextStringId();
		vo.setNttId(nextId);

		noticeMapper.insertNotice(vo);
		return vo.getNttId();
	}

}
