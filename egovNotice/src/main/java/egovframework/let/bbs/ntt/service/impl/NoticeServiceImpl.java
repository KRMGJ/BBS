package egovframework.let.bbs.ntt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.let.bbs.cmm.fms.service.FileMngService;
import egovframework.let.bbs.ntt.dao.NoticeDAO;
import egovframework.let.bbs.ntt.service.NoticeService;
import egovframework.let.bbs.ntt.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource(name = "noticeDAO")
	private NoticeDAO noticeDAO;

	@Resource(name = "egovNttIdGnrService")
	private EgovIdGnrService egovNttIdGnrService;

	@Resource(name = "fileMngService")
	private FileMngService fileMngService;

	/**
	 * 공지사항 목록을 조회한다.
	 */
	@Override
	public List<NoticeVO> selectNoticeList(NoticeVO searchVO) throws Exception {
		List<NoticeVO> parentList = noticeDAO.selectNoticeParentList(searchVO);

		if (parentList.isEmpty()) {
			return parentList;
		}

		List<String> parentIdList = parentList.stream().map(NoticeVO::getNttId).collect(Collectors.toList());

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bbsId", searchVO.getBbsId());
		paramMap.put("parentIdList", parentIdList);

		List<NoticeVO> treeList = noticeDAO.selectNoticeChildTreeList(paramMap);

		return treeList;
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
		if (vo.getPinnedAt() == null || vo.getPinnedAt().isEmpty()) {
			vo.setPinnedAt("N");
		}

		String nextId = egovNttIdGnrService.getNextStringId();
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

	/**
	 * 공지사항을 수정한다.
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateNotice(NoticeVO vo) throws Exception {
		noticeDAO.updateNotice(vo);
	}

	/**
	 * 공지사항을 삭제한다.
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteNoticeList(List<String> nttIdList) throws Exception {

		noticeDAO.deleteNoticeList(nttIdList);
	}

	/**
	 * 공지사항 답변을 등록한다.
	 */
	@Override
	public void insertReply(NoticeVO vo) throws Exception {

		vo.setNttId(egovNttIdGnrService.getNextStringId());
		vo.setPinnedAt("N");
		vo.setUseAt("Y");
		vo.setDelAt("N");
		noticeDAO.insertNotice(vo);
	}

	/**
	 * 공지사항 부모 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 공지사항 부모 총 갯수
	 * @throws Exception
	 */
	@Override
	public int selectNoticeParentListTotCnt(NoticeVO searchVO) throws Exception {
		return noticeDAO.selectNoticeParentListTotCnt(searchVO);
	}
}
