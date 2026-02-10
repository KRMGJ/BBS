package egovframework.let.bbs.cmm.vo;

import java.io.Serializable;

public class BbsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nttId; // NTT_ID (게시물ID)
	private String bbsId; // BBS_ID (게시판ID)
	private String subject; // 제목
	private String content; // 내용
	private String frstRegisterId; // 작성자
	private String frstRegistPnttm;// 작성일(문자 처리: 나중에 DATE로 바꿔도 됨)
	private int viewCnt; // 조회수

	private String pinnedAt; // 고정여부(Y/N)
	private String startDate; // YYYYMMDD
	private String endDate; // YYYYMMDD

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

	public String getBbsId() {
		return bbsId;
	}

	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getPinnedAt() {
		return pinnedAt;
	}

	public void setPinnedAt(String pinnedAt) {
		this.pinnedAt = pinnedAt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getNttId() {
		return nttId;
	}

	public void setNttId(String nttId) {
		this.nttId = nttId;
	}
}
