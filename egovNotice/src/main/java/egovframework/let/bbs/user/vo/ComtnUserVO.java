package egovframework.let.bbs.user.vo;

import java.io.Serializable;
import java.util.Date;

public class ComtnUserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId; // 사용자ID
	private String userNm; // 사용자명
	private String password; // 비밀번호
	private String email; // 이메일
	private String mobile; // 휴대폰번호
	private String useAt; // 사용여부
	private String lockAt; // 잠금여부
	private String frstRegisterId; // 최초등록자ID
	private Date frstRegistPnttm; // 최초등록시점
	private String lastUpdusrId; // 최종수정자ID
	private Date lastUpdtPnttm; // 최종수정시점

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getLockAt() {
		return lockAt;
	}

	public void setLockAt(String lockAt) {
		this.lockAt = lockAt;
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