package egovframework.let.bbs.user.join.service;

import egovframework.let.bbs.user.vo.ComtnUserVO;

public interface UserJoinService {
	/**
	 * 사용자 등록
	 * 
	 * @param user - 등록할 사용자 정보
	 */
	void join(ComtnUserVO user);

	/**
	 * userId 중복 여부 확인
	 * 
	 * @param userId - 확인할 userId
	 * @return boolean - 중복 여부
	 */
	boolean isDuplicatedUserId(String userId);
}
