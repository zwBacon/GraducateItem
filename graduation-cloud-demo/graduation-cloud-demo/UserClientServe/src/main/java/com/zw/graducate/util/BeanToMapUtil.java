package com.zw.graducate.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/4 20:45
 */

public class BeanToMapUtil {

    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(object, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        //解决方法：⭐在setFieldValueEditor中也需要判空
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "0";
                            } else {
                                fieldValue = fieldValue + "";
                            }
                            return fieldValue;
                        }));
        return stringObjectMap;
    }


    public static <T> T mapToBean(Map map, Class<T> beanClass) {
        return JSONObject.parseObject(JSON.toJSONString(map), beanClass);
    }


}
