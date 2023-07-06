package com.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="요청한 도서 분야를 찾을 수 없습니다.")
//예외 발생 시 상태코드 404와 메시지 출력

public class CategoryException extends RuntimeException {

}
