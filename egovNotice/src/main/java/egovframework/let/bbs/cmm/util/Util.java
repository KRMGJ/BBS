package egovframework.let.bbs.cmm.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;

import egovframework.com.cmm.vo.LoginVO;

public class Util {
	/**
	 * 세션 기반 조회수 증가 여부 판단
	 */
	public static boolean shouldIncreaseViewCount(HttpSession session, String nttId) {
		if (nttId == null || nttId.isBlank()) {
			return false;
		}
		String key = "NOTICE_VIEWED_" + nttId;
		if (session.getAttribute(key) != null) {
			return false;
		}
		session.setAttribute(key, Boolean.TRUE);
		return true;
	}

	/**
	 * 세션에서 loginId를 얻는다.
	 */
	public static String getLoginIdOrNull(HttpSession session) {
		LoginVO vo = (LoginVO) session.getAttribute("loginVO");
		return (vo == null) ? null : vo.getUniqId();
	}

	public static String encodeFilename(String name) {
		if (name == null || name.isBlank()) {
			name = "file";
		}
		String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8);
		return encoded.replaceAll("\\+", "%20");
	}
}
