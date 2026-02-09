package egovframework.let.bbs.ntt.web;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.cmm.fms.service.FileMngService;
import egovframework.let.bbs.cmm.fms.vo.FileVO;
import egovframework.let.bbs.ntt.service.NoticeService;
import egovframework.let.bbs.ntt.vo.NoticeVO;

@Controller
public class NoticeController {

	@Resource(name = "noticeService")
	private NoticeService noticeService;

	@Resource(name = "fileMngService")
	private FileMngService fileMngService;

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

		return "ntt/noticeList";
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
		return "ntt/noticeForm";
	}

	@RequestMapping(value = "/notice/insert.do", method = RequestMethod.POST)
	public String insertNotice(@ModelAttribute("notice") NoticeVO vo, RedirectAttributes redirectAttributes,
			HttpSession session) throws Exception {

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
		LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
		vo.setFrstRegisterId(loginVO.getUniqId());

		String nttId = noticeService.insertNotice(vo);

		// 상세로 보내거나 목록으로 보냄
		redirectAttributes.addFlashAttribute("msg", "등록되었습니다.");
//		return "redirect:/notice/selectNoticeDetail.do?nttId=" + nttId;
		return "redirect:/notice/list.do";
	}

	/**
	 * 공지사항 상세를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param request  - HttpServletRequest
	 * @param model    - 화면모델
	 * @return 공지사항 상세 View
	 * @throws Exception
	 */
	@RequestMapping("/notice/selectNoticeDetail.do")
	public String selectNoticeDetail(@ModelAttribute("searchVO") NoticeVO searchVO, HttpServletRequest request,
			Model model) throws Exception {

		// 조회수 세션 중복방지
		boolean increase = shouldIncreaseViewCount(request.getSession(), searchVO.getNttId());

		NoticeVO result = noticeService.selectNoticeDetail(searchVO, increase);
		if (result == null) {
			throw new IllegalStateException("존재하지 않거나 삭제된 게시물입니다.");
		}

		// 첨부파일 목록 (우리 파일 모듈 사용)
		if (result.getAtchFileId() != null && !result.getAtchFileId().isBlank()) {
			List<FileVO> fileList = fileMngService.selectFileList(result.getAtchFileId());
			model.addAttribute("fileList", fileList);
		}

		// 권한: 일단 "작성자ID == 세션 loginId" 기준(관리자 확장 가능)
		String loginId = getLoginIdOrNull(request.getSession());
		boolean canEdit = (loginId != null && loginId.equals(result.getFrstRegisterId()));

		model.addAttribute("result", result);
		model.addAttribute("canEdit", canEdit);

		return "ntt/noticeDetail";
	}

	/**
	 * 다운로드 (소속검증 포함) JSP에서 POST: nttId + atchFileId + fileSn
	 */
	@RequestMapping("/notice/downloadNoticeFile.do")
	public void downloadNoticeFile(@ModelAttribute("searchVO") NoticeVO vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reqAtchFileId = request.getParameter("atchFileId");
		String reqFileSn = request.getParameter("fileSn");

		if (vo.getNttId() == null || vo.getNttId().isBlank()) {
			throw new IllegalArgumentException("nttId가 없습니다.");
		}
		if (reqAtchFileId == null || reqAtchFileId.isBlank()) {
			throw new IllegalArgumentException("atchFileId가 없습니다.");
		}
		if (reqFileSn == null || reqFileSn.isBlank()) {
			throw new IllegalArgumentException("fileSn이 없습니다.");
		}

		int fileSn;
		try {
			fileSn = Integer.parseInt(reqFileSn);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("fileSn 형식이 올바르지 않습니다.");
		}

		// 소속검증: nttId의 atchFileId == 요청 atchFileId
		String dbAtchFileId = noticeService.selectAtchFileIdByNttId(vo);
		if (dbAtchFileId == null || dbAtchFileId.isBlank() || !dbAtchFileId.equals(reqAtchFileId)) {
			throw new IllegalStateException("잘못된 요청(파일 소속 불일치)입니다.");
		}

		// 파일 조회
		FileVO f = fileMngService.selectFileOne(reqAtchFileId, fileSn);
		if (f == null || !"Y".equals(f.getUseAt())) {
			throw new IllegalStateException("파일이 존재하지 않습니다.");
		}

		// 3) 디스크 스트리밍
		File file = new File(f.getFileStreCours(), f.getStreFileNm());
		if (!file.exists()) {
			throw new IllegalStateException("파일이 존재하지 않습니다.");
		}

		String downloadName = encodeFilename(f.getOrignlFileNm());

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
		response.setHeader("Content-Length", String.valueOf(file.length()));

		try (FileInputStream in = new FileInputStream(file)) {
			in.transferTo(response.getOutputStream());
		}
	}

	/**
	 * 세션 기반 조회수 증가 여부 판단
	 */
	private boolean shouldIncreaseViewCount(HttpSession session, String nttId) {
		if (nttId == null || nttId.isBlank()) {
			return false;
		}
		String key = "NOTICE_VIEWED_" + nttId;
		if (session.getAttribute(key) != null) {
			return false;
		}
		session.setAttribute(key, Boolean.TRUE);
		return true;
	}

	/**
	 * 세션에서 loginId를 얻는다.
	 */
	private String getLoginIdOrNull(HttpSession session) {
		LoginVO vo = (LoginVO) session.getAttribute("loginVO");
		return (vo == null) ? null : vo.getUniqId();
	}

	private String encodeFilename(String name) {
		if (name == null || name.isBlank()) {
			name = "file";
		}
		String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8);
		return encoded.replaceAll("\\+", "%20");
	}
}
