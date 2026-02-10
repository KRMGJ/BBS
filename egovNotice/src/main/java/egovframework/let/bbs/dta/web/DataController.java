package egovframework.let.bbs.dta.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.cmm.fms.service.FileMngService;
import egovframework.let.bbs.cmm.fms.service.impl.FileMngServiceImpl.FileSaveResult;
import egovframework.let.bbs.cmm.fms.vo.FileVO;
import egovframework.let.bbs.cmm.util.Util;
import egovframework.let.bbs.dta.service.DataService;
import egovframework.let.bbs.dta.vo.DataVO;

@Controller
@RequestMapping("/bbs/dta")
public class DataController {

	@Resource(name = "dataService")
	private DataService dataService;

	@Resource(name = "fileMngService")
	private FileMngService fileMngService;

	private static final String DATA_BBS_ID = "BBSMSTR_000000000002"; // 자료실 BBS_ID

	/** 자료실 목록을 조회한다. */
	@RequestMapping("/list.do")
	public String selectDataList(DataVO searchVO, Model model) throws Exception {

		searchVO.setBbsId(DATA_BBS_ID);

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<DataVO> resultList = dataService.selectDataList(searchVO);
		int totalCnt = dataService.selectDataListCnt(searchVO);

		paginationInfo.setTotalRecordCount(totalCnt);

		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

		return "dta/dataList";
	}

	/** 자료실 글 등록 화면으로 이동한다. */
	@RequestMapping("/form.do")
	public String dataForm(DataVO vo, Model model) throws Exception {
		model.addAttribute("dataVO", vo);
		return "dta/dataRegist";
	}

	/** 자료실 글을 등록한다. */
	@RequestMapping("/insert.do")
	public String insertData(DataVO vo, HttpServletRequest request,
			@RequestParam(value = "files", required = false) MultipartFile[] files, Model model) throws Exception {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vo.setFrstRegisterId(loginVO.getUniqId());

		vo.setBbsId(DATA_BBS_ID);

		List<String> savedFilePaths = new ArrayList<>();

		try {
			if (files != null && files.length > 0) {
				FileSaveResult fsr = fileMngService.saveFilesAppendWithTrace(null, files);
				vo.setAtchFileId(fsr.getAtchFileId());
				savedFilePaths.addAll(fsr.getSavedPaths());
			}

			dataService.insertData(vo);

		} catch (Exception e) {
			for (String path : savedFilePaths) {
				fileMngService.deletePhysicalFile(path);
			}
			throw e;
		}

		return "redirect:/bbs/dta/list.do";
	}

	/** 자료실 글 상세를 조회한다. */
	@RequestMapping("/detail.do")
	public String selectDataDetail(DataVO searchVO, Model model, HttpServletRequest request) throws Exception {

		boolean increase = Util.shouldIncreaseViewCount(request.getSession(), searchVO.getNttId());

		DataVO data = dataService.selectDataDetail(searchVO, increase);

		if (data.getAtchFileId() != null && !data.getAtchFileId().isBlank()) {
			List<FileVO> fileList = fileMngService.selectFileList(data.getAtchFileId());
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", data);

		return "dta/dataDetail";
	}

	/** 자료실 글에 첨부된 파일을 다운로드한다. */
	@RequestMapping("/downloadDataFile.do")
	public void downloadNoticeFile(@ModelAttribute("searchVO") DataVO vo, HttpServletRequest request,
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

		String dbAtchFileId = dataService.selectAtchFileIdByNttId(vo);
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

		String downloadName = Util.encodeFilename(f.getOrignlFileNm());

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
		response.setHeader("Content-Length", String.valueOf(file.length()));

		try (FileInputStream in = new FileInputStream(file)) {
			in.transferTo(response.getOutputStream());
		}
	}
}
