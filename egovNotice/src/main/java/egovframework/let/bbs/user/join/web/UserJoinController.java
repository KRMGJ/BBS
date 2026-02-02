package egovframework.let.bbs.user.join.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.let.bbs.user.join.service.UserJoinService;
import egovframework.let.bbs.user.vo.ComtnUserVO;

@Controller
public class UserJoinController {

	@Resource(name = "userJoinService")
	private UserJoinService userJoinService;

	/** 회원가입 화면 */
	@RequestMapping(value = "/user/joinView.do", method = RequestMethod.GET)
	public String joinView() {
		return "user/join";
	}

	/** 회원가입 처리 */
	@RequestMapping(value = "/user/join.do", method = RequestMethod.POST)
	public String join(ComtnUserVO user, Model model) {

		userJoinService.join(user);

		model.addAttribute("result", "OK");
		return "jsonView";
	}

	/** 아이디 중복 체크 */
	@RequestMapping(value = "/user/checkUserId.do", method = RequestMethod.GET)
	public String checkUserId(@RequestParam("userId") String userId, Model model) {

		boolean duplicated = userJoinService.isDuplicatedUserId(userId);
		model.addAttribute("duplicated", duplicated);

		return "jsonView";
	}
}
