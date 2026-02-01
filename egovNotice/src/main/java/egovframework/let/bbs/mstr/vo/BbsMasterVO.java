package egovframework.let.bbs.mstr.vo;

import java.io.Serializable;
import java.util.Date;

public class BbsMasterVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageIndex = 1; // 현재 페이지
	private int pageUnit = 10; // 페이지당 게시물 수
	private int pageSize = 10; // 페이지 리스트 크기(ui:pagination)
	private int firstIndex = 0; // 첫번째 인덱스
	private int lastIndex = 0; // 마지막 인덱스
	private int recordCountPerPage = 10; // 페이지당 레코드 수

	private String searchCondition; // 0:게시판명, 1:게시판ID
	private String searchKeyword; // 검색어
	private String useAt; // '', Y, N

	private String bbsId; // 게시판ID
	private String bbsNm; // 게시판명
	private String bbsIntrcn; // 게시판소개
	private String bbsTyCode; // 게시판유형코드

	private String fileAtchPosblAt; // 파일첨부가능여부
	private int atchPosblFileNumber; // 첨부가능파일건수
	private int atchPosblFileSize; // 첨부가능파일용량

	private String useAtCol; // 컬럼 useAt과 검색 useAt 구분용(선택)

	private String frstRegisterId; // 최초등록자ID
	private Date frstRegistPnttm; // 최초등록시점
	private String lastUpdusrId; // 최종수정자ID
	private Date lastUpdtPnttm; // 최종수정시점

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

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
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

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getBbsId() {
		return bbsId;
	}

	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	public String getBbsNm() {
		return bbsNm;
	}

	public void setBbsNm(String bbsNm) {
		this.bbsNm = bbsNm;
	}

	public String getBbsIntrcn() {
		return bbsIntrcn;
	}

	public void setBbsIntrcn(String bbsIntrcn) {
		this.bbsIntrcn = bbsIntrcn;
	}

	public String getBbsTyCode() {
		return bbsTyCode;
	}

	public void setBbsTyCode(String bbsTyCode) {
		this.bbsTyCode = bbsTyCode;
	}

	public String getFileAtchPosblAt() {
		return fileAtchPosblAt;
	}

	public void setFileAtchPosblAt(String fileAtchPosblAt) {
		this.fileAtchPosblAt = fileAtchPosblAt;
	}

	public int getAtchPosblFileNumber() {
		return atchPosblFileNumber;
	}

	public void setAtchPosblFileNumber(int atchPosblFileNumber) {
		this.atchPosblFileNumber = atchPosblFileNumber;
	}

	public int getAtchPosblFileSize() {
		return atchPosblFileSize;
	}

	public void setAtchPosblFileSize(int atchPosblFileSize) {
		this.atchPosblFileSize = atchPosblFileSize;
	}

	public String getUseAtCol() {
		return useAtCol;
	}

	public void setUseAtCol(String useAtCol) {
		this.useAtCol = useAtCol;
	}

	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	public Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

	public Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	public void setLastUpdtPnttm(Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

}
