package com.zw.graducate.interfaceAop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerAopNote {

}
