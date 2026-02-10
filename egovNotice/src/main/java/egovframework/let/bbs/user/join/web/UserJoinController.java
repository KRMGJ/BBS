package egovframework.let.bbs.user.join.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.let.bbs.user.join.service.UserJoinService;
import egovframework.let.bbs.user.vo.ComtnUserVO;

@Controller
@RequestMapping("/bbs/user")
public class UserJoinController {

	@Resource(name = "userJoinService")
	private UserJoinService userJoinService;

	/** 회원가입 화면 */
	@RequestMapping(value = "/joinView.do", method = RequestMethod.GET)
	public String joinView() {
		return "user/join";
	}

	/**
	 * 회원가입 처리
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> join(ComtnUserVO user, Model model) throws Exception {

		userJoinService.join(user);

		Map<String, Object> res = new HashMap<>();
		res.put("result", "OK");

		return ResponseEntity.ok(res);
	}

	/**
	 * 아이디 중복 체크
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUserId.do", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Model> checkUserId(@RequestParam("userId") String userId, Model model) throws Exception {

		boolean duplicated = userJoinService.isDuplicatedUserId(userId);
		model.addAttribute("duplicated", duplicated);

		return ResponseEntity.ok(model);
	}
}
