package com.elex.mark.util.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//参考资源：http://www.coolfancy.com/log/47.html ，
//参考资源：http://www.oschina.net/code/snippet_811941_14131
//参考资源： http://www.xfocus.net/articles/200508/816.html
//参考资源：http://www.symantec.com/connect/articles/detection-sql-injection-and-cross-site-scripting-attacks


@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckSqlInjectionValidator.class)
@Documented
/**
 * Created by sun on 2017/7/6.
 */
public @interface CheckSqlInjection {
    String message() default "{SQL注入验证未通过}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
