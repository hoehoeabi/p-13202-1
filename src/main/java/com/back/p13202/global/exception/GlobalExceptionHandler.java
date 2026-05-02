package com.back.p13202.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 404 예외처리
    @ExceptionHandler(DataNotFoundException.class)
    public String handleDataNotFoundException(DataNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    // 모든 5XX 에러 및 기타 처리되지 않은 예외 처리
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        log.error("Internal Server Error: ", ex);

        model.addAttribute("errorMessage", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.\n" + ex.getMessage());
        return "error/500";
    }
}
