package egovframework.let.bbs.ntt.vo;

import egovframework.let.bbs.cmm.vo.BbsVO;

public class NoticeVO extends BbsVO {
	private static final long serialVersionUID = 1L;
	private String nttId; // NTT_ID (게시물ID)

	public String getNttId() {
		return nttId;
	}

	public void setNttId(String nttId) {
		this.nttId = nttId;
	}
}
