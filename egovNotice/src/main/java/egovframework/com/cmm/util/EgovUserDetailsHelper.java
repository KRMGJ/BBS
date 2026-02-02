package egovframework.com.cmm.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import egovframework.com.cmm.vo.LoginVO;

public class EgovUserDetailsHelper {

	private static final String SESSION_KEY = "loginVO";

	private EgovUserDetailsHelper() {
	}

	/** 인증 사용자 객체 반환(LoginVO 아니면 null 가능) */
	public static Object getAuthenticatedUser() {
		try {
			RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
			if (attrs == null) {
				return null;
			}
			return attrs.getAttribute(SESSION_KEY, RequestAttributes.SCOPE_SESSION);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isAuthenticated() {
		return getAuthenticatedUser() != null;
	}

	/** 인증 사용자 객체 저장 */
	public static void setAuthenticatedUser(LoginVO loginVO) {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs == null) {
			return;
		}
		attrs.setAttribute(SESSION_KEY, loginVO, RequestAttributes.SCOPE_SESSION);
	}

	/** 인증 사용자 객체 삭제 */
	public static void clearAuthenticatedUser() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs == null) {
			return;
		}
		attrs.removeAttribute(SESSION_KEY, RequestAttributes.SCOPE_SESSION);
	}
}