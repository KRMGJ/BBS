package egovframework.let.bbs.user.auth.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.vo.ApiVO;
import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.user.auth.service.UserAuthService;
import egovframework.let.bbs.user.vo.ComtnUserVO;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/bbs/user")
public class UserAuthController {

	@Resource(name = "userAuthService")
	private UserAuthService userAuthService;

	/** 로그인 화면 */
	@RequestMapping(value = "/loginView.do", method = RequestMethod.GET)
	public String loginView() {
		return "user/login";
	}

	/**
	 * 로그인 처리
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ResponseEntity<ApiVO<?>> login(@RequestBody ComtnUserVO vo) throws Exception {
		LoginVO loginVO = userAuthService.login(vo.getUserId(), vo.getPassword());
		Map<String, Object> result = new HashMap<>();
		result.put("result", "OK");
		result.put("loginVO", loginVO);
		return ResponseEntity.ok(ApiVO.success("로그인 성공", result));
	}

	/**
	 * 로그아웃
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public ResponseEntity<?> logout() throws Exception {
		userAuthService.logout();
		Map<String, Object> result = new HashMap<>();
		result.put("result", "OK");
		return ResponseEntity.ok(ApiVO.success("로그아웃 성공", result));
	}

	/**
	 * 현재 로그인 사용자
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/me.do", method = RequestMethod.GET)
	public ResponseEntity<ApiVO<?>> me() throws Exception {
		LoginVO loginVO = userAuthService.meOrNull();
		Map<String, Object> result = new HashMap<>();
		result.put("isLogin", loginVO != null);
		result.put("loginVO", loginVO);
		return ResponseEntity.ok(ApiVO.success("현재 로그인 사용자 정보", result));
	}
}
