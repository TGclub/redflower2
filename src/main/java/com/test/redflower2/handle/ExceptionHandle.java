package com.test.redflower2.handle;

import com.test.redflower2.pojo.dto.Result;
import com.test.redflower2.pojo.dto.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一异常处理
 */
//全局异常处理，捕获所有Controller中抛出的异常。
@RestControllerAdvice
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandle(Exception e) {
        if (e instanceof RuntimeException) {
            logger.error("服务器发生错误：{}", e);
            e.printStackTrace();
            return ResultBuilder.fail("服务器内部错误");
        }

        logger.error("错误信息:{}", e);
        return ResultBuilder.fail();
    }
}
