package egovframework.let.bbs.cmm.fms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.let.bbs.cmm.fms.dao.FileMngDAO;
import egovframework.let.bbs.cmm.fms.service.FileMngService;
import egovframework.let.bbs.cmm.fms.util.FileStorageUtil;
import egovframework.let.bbs.cmm.fms.vo.FileVO;

@Service("fileMngService")
public class FileMngServiceImpl implements FileMngService {

	@Resource(name = "fileMngDAO")
	private FileMngDAO fileMngDAO;

	private FileStorageUtil storage;

	private String uploadPath = "C:/egovframework/upload"; // 기본 업로드 경로

	@PostConstruct
	public void init() {
		this.storage = new FileStorageUtil(uploadPath);
	}

	/**
	 * 파일그룹 생성 및 파일등록
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveFilesNewGroup(MultipartFile[] files) throws Exception {
		String atchFileId = newAtchFileId();
		fileMngDAO.insertFileGroup(atchFileId);
		saveFilesInternal(atchFileId, files);
		return atchFileId;
	}

	/**
	 * 파일그룹에 파일추가 등록
	 */
	@Override
	public String saveFilesAppend(String atchFileId, MultipartFile[] files) throws Exception {
		if (atchFileId == null || atchFileId.isBlank()) {
			return saveFilesNewGroup(files);
		}
		saveFilesInternal(atchFileId, files);
		return atchFileId;
	}

	/**
	 * 파일저장 내부처리
	 */
	private void saveFilesInternal(String atchFileId, MultipartFile[] files) throws Exception {
		if (files == null || files.length == 0) {
			return;
		}

		for (MultipartFile mf : files) {
			if (mf == null || mf.isEmpty()) {
				continue;
			}

			Integer nextSn = fileMngDAO.selectNextFileSn(atchFileId);
			if (nextSn == null) {
				nextSn = 1;
			}

			FileStorageUtil.StoredFile stored = storage.store(mf);

			FileVO vo = new FileVO();
			vo.setAtchFileId(atchFileId);
			vo.setFileSn(nextSn);
			vo.setFileStreCours(stored.storedPath);
			vo.setStreFileNm(stored.storedName);
			vo.setOrignlFileNm(stored.originalName);
			vo.setFileExtsn(stored.ext);
			vo.setFileSize(stored.size);
			vo.setUseAt("Y");

			fileMngDAO.insertFileDetail(vo);
		}
	}

	/**
	 * 파일목록 조회
	 */
	@Override
	public List<FileVO> selectFileList(String atchFileId) throws Exception {
		if (atchFileId == null || atchFileId.isBlank()) {
			return List.of();
		}
		return fileMngDAO.selectFileList(atchFileId);
	}

	/**
	 * 파일상세 조회
	 */
	@Override
	public FileVO selectFileOne(String atchFileId, int fileSn) throws Exception {
		Map<String, Object> p = new HashMap<>();
		p.put("atchFileId", atchFileId);
		p.put("fileSn", fileSn);
		return fileMngDAO.selectFileOne(p);
	}

	/**
	 * 파일삭제
	 */
	@Override
	public void deleteFile(String atchFileId, int fileSn) throws Exception {
		Map<String, Object> p = new HashMap<>();
		p.put("atchFileId", atchFileId);
		p.put("fileSn", fileSn);
		int updated = fileMngDAO.updateFileUseAtN(p);
		if (updated == 0) {
			throw new IllegalStateException("삭제할 파일이 없습니다.");
		}
	}

	/**
	 * 파일그룹삭제
	 */
	@Override
	public void deleteFileGroup(String atchFileId) throws Exception {
		int updated = fileMngDAO.updateFileGroupUseAtN(atchFileId);
		if (updated == 0) {
			throw new IllegalStateException("삭제할 파일그룹이 없습니다.");
		}
	}

	private String newAtchFileId() {
		// eGov 스타일처럼 접두어를 원하면 "FILE_" + ... 로 바꿔도 됨
		return "FILE_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
	}

}
