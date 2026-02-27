package egovframework.let.bbs.ntt.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.vo.ApiVO;
import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.cmm.fms.service.FileMngService;
import egovframework.let.bbs.cmm.fms.service.impl.FileMngServiceImpl.FileSaveResult;
import egovframework.let.bbs.cmm.fms.vo.FileVO;
import egovframework.let.bbs.cmm.util.EgovUtil;
import egovframework.let.bbs.ntt.dao.NoticeDAO;
import egovframework.let.bbs.ntt.service.NoticeService;
import egovframework.let.bbs.ntt.vo.NoticeVO;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/bbs/notice")
public class NoticeController {

	@Resource(name = "noticeService")
	private NoticeService noticeService;

	@Resource(name = "fileMngService")
	private FileMngService fileMngService;

	@Resource(name = "noticeDAO")
	private NoticeDAO noticeDAO;

	private static final String NOTICE_BBS_ID = "BBSMSTR_000000000001";

	/**
	 * 공지사항 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model    - 화면모델
	 * @return 공지사항 목록 View
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public ResponseEntity<?> noticeList(NoticeVO searchVO) throws Exception {

		searchVO.setBbsId(NOTICE_BBS_ID);

		int pageIndex = searchVO.getPageIndex() < 1 ? 1 : searchVO.getPageIndex();
		int pageUnit = searchVO.getPageUnit() < 1 ? 10 : searchVO.getPageUnit();
		int pageSize = searchVO.getPageSize() < 1 ? 10 : searchVO.getPageSize();

		searchVO.setPageIndex(pageIndex);
		searchVO.setPageUnit(pageUnit);
		searchVO.setPageSize(pageSize);

		int totalCount = noticeService.selectNoticeParentListTotCnt(searchVO);

		int totalPageCount = (int) Math.ceil((double) totalCount / pageUnit);

		int startPage = ((pageIndex - 1) / pageSize) * pageSize + 1;
		int endPage = startPage + pageSize - 1;

		if (endPage > totalPageCount) {
			endPage = totalPageCount;
		}

		int firstIndex = (pageIndex - 1) * pageUnit;
		searchVO.setFirstIndex(firstIndex);
		searchVO.setRecordCountPerPage(pageUnit);

		List<NoticeVO> noticeList = noticeService.selectNoticeList(searchVO);
		List<NoticeVO> pinnedList = noticeService.selectNoticePinnedList(searchVO);

		Map<String, Object> response = new HashMap<>();
		response.put("noticeList", noticeList);
		response.put("pinnedList", pinnedList);
		response.put("searchVO", searchVO);
		response.put("totalCount", totalCount);
		response.put("totalPageCount", totalPageCount);
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return ResponseEntity.ok(ApiVO.success("게시물 목록 조회 성공", response));
	}

	/**
	 * 공지사항 등록 화면으로 이동한다.
	 * 
	 * @param vo    - 등록할 정보가 담긴 VO
	 * @param model - 화면모델
	 * @return 공지사항 등록 View
	 * @throws Exception
	 */
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String noticeForm(@ModelAttribute("notice") NoticeVO vo, Model model) throws Exception {
		vo.setBbsId(NOTICE_BBS_ID);

		model.addAttribute("notice", vo);
		return "ntt/noticeForm";
	}

	/**
	 * 공지사항을 등록한다.
	 * 
	 * @param vo                 - 등록할 정보가 담긴 VO
	 * @param files              - 업로드된 파일들
	 * @param redirectAttributes - 리다이렉트 속성
	 * @param session            - 세션
	 * @return 공지사항 목록 View로 리다이렉트
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insertNotice(@ModelAttribute("notice") NoticeVO vo,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			RedirectAttributes redirectAttributes, HttpSession session) throws Exception {

		vo.setBbsId(NOTICE_BBS_ID);

		if (vo.getSubject() == null || vo.getSubject().trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "제목은 필수입니다.");
			return "redirect:/bbs/notice/form.do";
		}

		if (vo.getContent() == null || vo.getContent().trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "내용은 필수입니다.");
			return "redirect:/bbs/notice/form.do";
		}

		LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
		vo.setFrstRegisterId(loginVO.getUniqId());

		List<String> savedFilePaths = new ArrayList<>();

		try {
			if (files != null && files.length > 0) {
				FileSaveResult fsr = fileMngService.saveFilesAppendWithTrace(null, files);
				vo.setAtchFileId(fsr.getAtchFileId());
				savedFilePaths.addAll(fsr.getSavedPaths());
			}

			noticeService.insertNotice(vo);

		} catch (Exception e) {
			for (String path : savedFilePaths) {
				fileMngService.deletePhysicalFile(path);
			}
			throw e;
		}

		redirectAttributes.addFlashAttribute("msg", "등록되었습니다.");
		return "redirect:/bbs/notice/list.do";
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
	@RequestMapping("/selectNoticeDetail.do")
	public String selectNoticeDetail(@ModelAttribute("searchVO") NoticeVO searchVO, HttpServletRequest request,
			Model model) throws Exception {

		String viewerId = EgovUtil.getLoginIdOrNull(request.getSession());

		NoticeVO notice = noticeService.selectNoticeDetail(searchVO, viewerId);
		if (notice == null) {
			throw new IllegalStateException("존재하지 않거나 삭제된 게시물입니다.");
		}

		boolean liked = false;

		if (viewerId != null) {
			int cnt = noticeDAO.selectNoticeLikeCountByUser(searchVO.getNttId(), viewerId);
			liked = cnt > 0;
		}

		model.addAttribute("liked", liked);

		// 첨부파일 목록 (우리 파일 모듈 사용)
		if (notice.getAtchFileId() != null && !notice.getAtchFileId().isBlank()) {
			List<FileVO> fileList = fileMngService.selectFileList(notice.getAtchFileId());
			model.addAttribute("fileList", fileList);
		}

		// 권한: 일단 "작성자ID == 세션 loginId" 기준(관리자 확장 가능)
		String loginId = EgovUtil.getLoginIdOrNull(request.getSession());
		boolean canEdit = (loginId != null && loginId.equals(notice.getFrstRegisterId()));

		model.addAttribute("notice", notice);
		model.addAttribute("canEdit", canEdit);

		return "ntt/noticeDetail";
	}

	/**
	 * 다운로드 (소속검증 포함) JSP에서 POST: nttId + atchFileId + fileSn
	 */
	@RequestMapping("/downloadNoticeFile.do")
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

		String downloadName = EgovUtil.encodeFilename(f.getOrignlFileNm());

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
		response.setHeader("Content-Length", String.valueOf(file.length()));

		try (FileInputStream in = new FileInputStream(file)) {
			in.transferTo(response.getOutputStream());
		}
	}

	/**
	 * 공지사항 수정 화면으로 이동한다.
	 * 
	 * @param vo    - 수정할 정보가 담긴 VO
	 * @param model - 화면모델
	 * @return 공지사항 수정 View
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNoticeView.do", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute("notice") NoticeVO vo, Model model) throws Exception {

		vo.setBbsId(NOTICE_BBS_ID);

		NoticeVO result = noticeService.selectNoticeDetail(vo, null);
		model.addAttribute("notice", result);

		if (result.getAtchFileId() != null && !result.getAtchFileId().isBlank()) {
			model.addAttribute("fileList", fileMngService.selectFileList(result.getAtchFileId()));
		}

		return "ntt/noticeForm";
	}

	/**
	 * 공지사항을 수정한다.
	 * 
	 * @param vo                 - 수정할 정보가 담긴 VO
	 * @param files              - 업로드된 파일들
	 * @param delFileSn          - 삭제할 파일일련번호 배열
	 * @param redirectAttributes - 리다이렉트 속성
	 * @param session            - 세션
	 * @return 공지사항 상세 View로 리다이렉트
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updateNotice(@ModelAttribute("notice") NoticeVO vo,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "delFileSn", required = false) int[] delFileSn, RedirectAttributes redirectAttributes,
			HttpSession session) throws Exception {

		List<String> savedFilePaths = new ArrayList<>();

		try {
			if (files != null && files.length > 0 && !files[0].isEmpty()) {
				FileSaveResult fsr = fileMngService.saveFilesAppendWithTrace(vo.getAtchFileId(), files);

				vo.setAtchFileId(fsr.getAtchFileId());
				savedFilePaths.addAll(fsr.getSavedPaths());
			}

			if (delFileSn != null && vo.getAtchFileId() != null) {
				for (int sn : delFileSn) {
					fileMngService.deleteFile(vo.getAtchFileId(), sn);
				}
			}

			noticeService.updateNotice(vo);

			redirectAttributes.addFlashAttribute("msg", "수정되었습니다.");
			return "redirect:/bbs/notice/detail.do?nttId=" + vo.getNttId();

		} catch (Exception e) {
			for (String path : savedFilePaths) {
				fileMngService.deletePhysicalFile(path);
			}
			throw e;
		}
	}

	/**
	 * 공지사항을 삭제한다.
	 * 
	 * @param nttIdList - 삭제할 공지사항 ID 리스트
	 * @return ResponseEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteList.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteNoticeList(
			@RequestParam(value = "nttIdList", required = false) List<String> nttIdList,
			@RequestParam(value = "nttId", required = false) String nttId) throws Exception {

		if ((nttIdList == null || nttIdList.isEmpty()) && nttId != null && !nttId.isBlank()) {
			nttIdList = List.of(nttId);
		}

		noticeService.deleteNoticeList(nttIdList);

		return ResponseEntity.ok().build();
	}

	/**
	 * 공지사항 답변을 등록한다.
	 * 
	 * @param vo                 - 등록할 정보가 담긴 VO
	 * @param session            - 세션
	 * @param redirectAttributes - 리다이렉트 속성
	 * @return 공지사항 목록 View로 리다이렉트
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply.do", method = RequestMethod.POST)
	public String insertReply(@ModelAttribute NoticeVO vo, HttpSession session, RedirectAttributes redirectAttributes)
			throws Exception {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vo.setFrstRegisterId(loginVO.getUniqId() == null ? "user1" : loginVO.getUniqId());
		vo.setBbsId(NOTICE_BBS_ID);

		noticeService.insertReply(vo);

		redirectAttributes.addFlashAttribute("msg", "답글이 등록되었습니다.");
		return "redirect:/bbs/notice/list.do";
	}

	@RequestMapping(value = "/like.do", method = RequestMethod.POST)
	@ResponseBody
	public String likeNotice(@ModelAttribute NoticeVO vo, HttpSession session) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = loginVO.getUniqId();

		String result = noticeService.likeNotice(vo, userId);

		return result;
	}
}
