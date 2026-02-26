package egovframework.let.bbs.ntt.vo;

import java.util.Date;

public class NoticeLikeVO {
	private String likeId; // 좋아요 ID
	private String nttId; // 게시물 ID
	private String userId; // 사용자 ID
	private Date likeDt; // 좋아요 날짜
	private String useAt; // 사용 여부 (Y/N)

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public String getNttId() {
		return nttId;
	}

	public void setNttId(String nttId) {
		this.nttId = nttId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLikeDt() {
		return likeDt;
	}

	public void setLikeDt(Date likeDt) {
		this.likeDt = likeDt;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
}
