package egovframework.com.cmm.vo;

import java.io.Serializable;

public class ApiErrorVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String result; // "FAIL"
	private String message; // 사용자에게 보여줄 메시지
	private String code; // 선택: "BAD_REQUEST", "UNAUTHORIZED", "SERVER_ERROR"

	public ApiErrorVO() {
	}

	public ApiErrorVO(String result, String message, String code) {
		this.result = result;
		this.message = message;
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
