package egovframework.notice.notice.service;

import java.io.Serializable;

public class NoticeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nttId; // NTT_ID (게시물ID)
	private String bbsId; // BBS_ID (게시판ID)
	private String nttSj; // 제목
	private String nttCn; // 내용
	private String frstRegisterId; // 작성자
	private String frstRegistPnttm;// 작성일(문자 처리: 나중에 DATE로 바꿔도 됨)
	private int inqireCo; // 조회수

	private String noticeAt; // 공지여부(Y/N)
	private String noticeBgnde; // YYYYMMDD
	private String noticeEndde; // YYYYMMDD

	private String delAt; // 삭제여부(Y/N)
	private String useAt; // 사용여부(Y/N)

	private String atchFileId; // 첨부그룹ID

	private String searchCondition; // 0:제목, 1:내용, 2:작성자
	private String searchKeyword; // 검색어

	private int pageIndex = 1; // 현재 페이지
	private int pageUnit = 10; // 페이지당 목록 개수
	private int pageSize = 10; // 페이지 리스트 크기

	private int firstIndex; // 시작 row (0-based 또는 1-based는 SQL에서 맞춤)
	private int recordCountPerPage;

	public String getNttId() {
		return nttId;
	}

	public void setNttId(String nttId) {
		this.nttId = nttId;
	}

	public String getBbsId() {
		return bbsId;
	}

	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	public String getNttSj() {
		return nttSj;
	}

	public void setNttSj(String nttSj) {
		this.nttSj = nttSj;
	}

	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public int getInqireCo() {
		return inqireCo;
	}

	public void setInqireCo(int inqireCo) {
		this.inqireCo = inqireCo;
	}

	public String getNoticeAt() {
		return noticeAt;
	}

	public void setNoticeAt(String noticeAt) {
		this.noticeAt = noticeAt;
	}

	public String getNoticeBgnde() {
		return noticeBgnde;
	}

	public void setNoticeBgnde(String noticeBgnde) {
		this.noticeBgnde = noticeBgnde;
	}

	public String getNoticeEndde() {
		return noticeEndde;
	}

	public void setNoticeEndde(String noticeEndde) {
		this.noticeEndde = noticeEndde;
	}

	public String getDelAt() {
		return delAt;
	}

	public void setDelAt(String delAt) {
		this.delAt = delAt;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNttCn() {
		return nttCn;
	}

	public void setNttCn(String nttCn) {
		this.nttCn = nttCn;
	}
}
