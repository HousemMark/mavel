package com.skyline.shield.common.utils;

import com.skyline.shield.common.emuns.ReturnCode;
import com.skyline.shield.common.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> List<T> copyArray(Collection sourceList, Class<T> clz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.EMPTY_LIST;
        }
        List<T> result = new ArrayList<>(sourceList.size());
        for (Object source: sourceList) {
            T target = null;
            try {
                target = clz.newInstance();
            } catch (Exception e) {
                throw new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(), ReturnCode.SYSTEM_ERROR.getMsg() + e);
            }
            copyProperties(source, target);
            result.add(target);
        }
        return result;
    }

    public static <T> T copyBean(Object source, Class<T> clz) {
        T target = null;
        try {
            target = clz.newInstance();
            copyProperties(source, target);
        } catch (Exception e) {
            throw new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(), ReturnCode.SYSTEM_ERROR.getMsg() + e);
        }
        return target;
    }

}
