package egovframework.let.bbs.cmm.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;

import egovframework.com.cmm.vo.LoginVO;

public class EgovUtil {

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

	public static String clearXSS(String value) {
		if (value == null) {
			return null;
		}

		String result = value;
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("\\(", "&#40;");
		result = result.replaceAll("\\)", "&#41;");
		result = result.replaceAll("'", "&#39;");
		result = result.replaceAll("\"", "&quot;");
		return result;
	}
}
