package com.security.api.annotation;

import com.security.api.model.board.ParamsPost;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})   // 메소드 레벨에서 사용가능
@Retention(RetentionPolicy.RUNTIME) //애플리케이션이 실행 중일때 정보를 얻을 수 있다
public @interface ForbiddenWordCheck {  // @interface annotation이라 선언
    /**
     * 메서드에서 금칙어 체크할 파라미터의 정보입ㄴ디ㅏ. paramPost라는 이름의 파라미터 객체로부터 content field의 값을 읽어 금칙어
     * 체크를 하겠다는 의미입니다. 만약 금칙어 체크할 파라미터가 객체가 아닌 String일 경우 파라미터 명만 넣으면 됩니다.
     * (@ForbiddenWordCheck(param="파라미터명")
    * */
    String param() default "paramsPost.content";

    /**
     * 금칙어 체크할 파라미터가 객체인 경우 해당 객체의 class정보를 세팅합니다. 금칙어 체크할 파라미터가 String인 경우 사용되지 않습니다.
     */
    Class<?> checkClazz() default ParamsPost.class;
}
