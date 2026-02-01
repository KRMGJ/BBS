package egovframework.let.bbs.ntt.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.let.bbs.ntt.service.NoticeService;
import egovframework.let.bbs.ntt.vo.NoticeVO;

@Controller
public class NoticeController {

	@Resource(name = "noticeService")
	private NoticeService noticeService;

	/**
	 * 공지사항 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model    - 화면모델
	 * @return 공지사항 목록 View
	 * @throws Exception
	 */
	@RequestMapping("/notice/list.do")
	public String noticeList(NoticeVO searchVO, Model model) throws Exception {
		if (searchVO.getBbsId() == null || searchVO.getBbsId().isEmpty()) {
			searchVO.setBbsId("BBSMSTR_000000000001");
		}
		if (searchVO.getPageIndex() < 1) {
			searchVO.setPageIndex(1);
		}

		if (searchVO.getPageUnit() < 1) {
			searchVO.setPageUnit(10);
		}

		if (searchVO.getPageSize() < 1) {
			searchVO.setPageSize(10);
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<NoticeVO> noticeList = noticeService.selectNoticeList(searchVO);
		List<NoticeVO> pinnedList = noticeService.selectNoticePinnedList(searchVO);

		int totCnt = noticeService.selectNoticeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pinnedList", pinnedList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

		return "notice/noticeList";
	}

	/**
	 * 공지사항 등록 화면으로 이동한다.
	 * 
	 * @param vo    - 등록할 정보가 담긴 VO
	 * @param model - 화면모델
	 * @return 공지사항 등록 View
	 * @throws Exception
	 */
	@RequestMapping(value = "/notice/form.do", method = RequestMethod.GET)
	public String noticeForm(@ModelAttribute("notice") NoticeVO vo, Model model) throws Exception {
		// bbsId가 없으면 임시 기본값
		if (vo.getBbsId() == null || vo.getBbsId().isEmpty()) {
			vo.setBbsId("BBSMSTR_000000000001");
		}
		model.addAttribute("notice", vo);
		return "notice/noticeForm";
	}

	@RequestMapping(value = "/notice/insert.do", method = RequestMethod.POST)
	public String insertNotice(@ModelAttribute("notice") NoticeVO vo, RedirectAttributes redirectAttributes)
			throws Exception {

		if (vo.getBbsId() == null || vo.getBbsId().isEmpty()) {
			vo.setBbsId("BBSMSTR_000000000001");
		}
		if (vo.getNttSj() == null || vo.getNttSj().trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "제목은 필수입니다.");
			return "redirect:/notice/form.do";
		}
		if (vo.getNttCn() == null || vo.getNttCn().trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "내용은 필수입니다.");
			return "redirect:/notice/form.do";
		}

		// 작성자(로그인 붙이기 전 임시)
		if (vo.getFrstRegisterId() == null || vo.getFrstRegisterId().isEmpty()) {
			vo.setFrstRegisterId("admin"); // TODO: 로그인 연동 시 세션 사용자ID로 교체
		}

		String nttId = noticeService.insertNotice(vo);

		// 상세로 보내거나 목록으로 보냄
		redirectAttributes.addFlashAttribute("msg", "등록되었습니다.");
//		return "redirect:/notice/detail.do?nttId=" + nttId;
		return "redirect:/notice/list.do";
	}
}
