package egovframework.com.cmm.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiVO<T> {
	private String message;
	private T data;

	public static <T> ApiVO<T> success(String message, T data) {
		return new ApiVO<>(message, data);
	}

	public static <T> ApiVO<T> error(String message) {
		return new ApiVO<>(message, null);
	}
}
