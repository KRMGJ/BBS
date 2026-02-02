package egovframework.let.bbs.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.user.service.UserAuthService;

@Controller
public class UserAuthController {

	@Resource(name = "userAuthService")
	private UserAuthService userAuthService;

	/** 로그인 화면 */
	@RequestMapping(value = "/user/loginView.do", method = RequestMethod.GET)
	public String loginView() {
		return "user/login";
	}

	/** 로그인 처리 */
	@RequestMapping(value = "/user/login.do", method = RequestMethod.POST)
	public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, Model model,
			HttpServletResponse response) {
		LoginVO loginVO = userAuthService.login(userId, password);

		model.addAttribute("result", "OK");
		model.addAttribute("loginVO", loginVO);
		return "jsonView";
	}

	/** 로그아웃 */
	@RequestMapping(value = "/user/logout.do", method = RequestMethod.POST)
	public String logout(Model model) {
		userAuthService.logout();
		model.addAttribute("result", "OK");
		return "jsonView";
	}

	/** 현재 로그인 사용자 */
	@RequestMapping(value = "/user/me.do", method = RequestMethod.GET)
	public String me(Model model) {
		LoginVO loginVO = userAuthService.meOrNull();
		model.addAttribute("isLogin", loginVO != null);
		model.addAttribute("loginVO", loginVO);
		return "jsonView";
	}
}
