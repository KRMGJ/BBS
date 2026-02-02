package egovframework.com.cmm.vo;

import java.io.Serializable;

public class LoginVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uniqId;
	private String name;
	private String email;

	public String getUniqId() {
		return uniqId;
	}

	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
