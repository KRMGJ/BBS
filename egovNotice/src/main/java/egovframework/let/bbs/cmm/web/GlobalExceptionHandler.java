package egovframework.let.bbs.cmm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import egovframework.com.cmm.vo.ApiErrorVO;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

	/**
	 * 사용자 입력 오류(예: 로그인 실패, 파라미터 검증 실패)
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setStatus(400);
		model.addAttribute("error", new ApiErrorVO("FAIL", e.getMessage(), "BAD_REQUEST"));
		return "jsonView";
	}

	/**
	 * 권한/상태 오류(예: 로그인 필요, 계정잠금, 접근불가)
	 */
	@ExceptionHandler(IllegalStateException.class)
	public String handleIllegalState(IllegalStateException e, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		response.setStatus(401);
		model.addAttribute("error", new ApiErrorVO("FAIL", e.getMessage(), "UNAUTHORIZED"));
		return "jsonView";
	}

	/**
	 * 나머지 전부(서버 오류)
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, HttpServletRequest request, HttpServletResponse response, Model model) {
		response.setStatus(500);
		model.addAttribute("error", new ApiErrorVO("FAIL", "서버 오류", "SERVER_ERROR"));
		return "jsonView";
	}
}
