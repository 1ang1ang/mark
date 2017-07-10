package com.elex.mark.util.annotation;

import com.elex.mark.enums.ApiAuthorityType;

import java.lang.annotation.*;

/**
 * Created by sun on 2017/7/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {
    ApiAuthorityType[] authorityTypes();
}
