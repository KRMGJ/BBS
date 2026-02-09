package egovframework.let.bbs.ntt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.let.bbs.ntt.dao.NoticeDAO;
import egovframework.let.bbs.ntt.service.NoticeService;
import egovframework.let.bbs.ntt.vo.NoticeVO;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource(name = "noticeDAO")
	private NoticeDAO noticeDAO;

	@Resource(name = "noticeNttIdGnrService")
	private EgovIdGnrService noticeNttIdGnrService;

	/**
	 * 공지사항 목록을 조회한다.
	 */
	@Override
	public List<NoticeVO> selectNoticeList(NoticeVO searchVO) throws Exception {
		return noticeDAO.selectNoticeList(searchVO);
	}

	/**
	 * 공지사항 총 갯수를 조회한다.
	 */
	@Override
	public int selectNoticeListTotCnt(NoticeVO searchVO) throws Exception {
		return noticeDAO.selectNoticeListTotCnt(searchVO);
	}

	/**
	 * 공지사항 상단고정 목록을 조회한다.
	 */
	@Override
	public List<NoticeVO> selectNoticePinnedList(NoticeVO searchVO) throws Exception {
		return noticeDAO.selectNoticePinnedList(searchVO);
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

		noticeDAO.insertNotice(vo);
		return vo.getNttId();
	}

	/**
	 * 공지사항 상세를 조회한다.
	 */
	@Override
	public NoticeVO selectNoticeDetail(NoticeVO vo, boolean increaseViewCnt) throws Exception {
		if (increaseViewCnt) {
			noticeDAO.updateInqireCo(vo);
		}
		return noticeDAO.selectNoticeDetail(vo);
	}

	/**
	 * 다운로드 소속검증용
	 */
	@Override
	public String selectAtchFileIdByNttId(NoticeVO vo) throws Exception {
		return noticeDAO.selectAtchFileIdByNttId(vo);
	}

	@Override
	public void updateNotice(NoticeVO vo) throws Exception {
		noticeDAO.updateNotice(vo);
	}
}
