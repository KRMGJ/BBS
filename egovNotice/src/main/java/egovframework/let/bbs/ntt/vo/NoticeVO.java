package egovframework.let.bbs.ntt.vo;

import egovframework.let.bbs.cmm.vo.BbsVO;

public class NoticeVO extends BbsVO {
	private static final long serialVersionUID = 1L;
	private String parntNttId;
	private String rootId;
	private String likeId;
	private String parentDelAt;
	private String rootDelAt;
	private boolean hidden;
	private int nttLevel;
	private String hasChild;

	public String getParntNttId() {
		return parntNttId;
	}

	public void setParntNttId(String parntNttId) {
		this.parntNttId = parntNttId;
	}

	public String getParentDelAt() {
		return parentDelAt;
	}

	public void setParentDelAt(String parentDelAt) {
		this.parentDelAt = parentDelAt;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getNttLevel() {
		return nttLevel;
	}

	public void setNttLevel(int nttLevel) {
		this.nttLevel = nttLevel;
	}

	public String getRootDelAt() {
		return rootDelAt;
	}

	public void setRootDelAt(String rootDelAt) {
		this.rootDelAt = rootDelAt;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}
}
