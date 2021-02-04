package com.skyline.shield.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统线程上下文工具
 */
public abstract class AbstractThreadContext {

    protected abstract ThreadLocal<Map<String, Object>> getThreadContext();

    /**
     * 储存上下文对象
     */
    public void set(final String key, final Object value) {
        // 获取上下文，判空
        ThreadLocal<Map<String, Object>> threadContext = getThreadContext();
        Map<String, Object> threadMap = threadContext.get();
        if (Objects.equals(null, threadMap)) {
            threadMap = new HashMap<>();
        }
        threadMap.put(key, value);
        if (value instanceof String) {
            threadMap.put((String) value, Thread.currentThread().getName());
        }
        threadContext.set(threadMap);
        return;
    }

    /**
     * 获取上下文对象
     */
    public Object get(String key) {
        ThreadLocal<Map<String, Object>> threadContext = getThreadContext();
        Map<String, Object> map = threadContext.get();
        if (Objects.equals(null, map)) {
            return null;
        } else {
            return map.get(key);
        }
    }

    /**
     * 移除上下文部分对象
     * @param key
     */
    public void removeKey(String key) {
        ThreadLocal<Map<String, Object>> threadContext = getThreadContext();
        Map<String, Object> threadMap = threadContext.get();
        if (Objects.equals(null, threadMap)) {
            threadMap = new HashMap<>();
        }
        threadMap.remove(key);
        threadContext.set(threadMap);
        return;
    }

    /**
     * 清除上下文
     */
    public void clean() {
        ThreadLocal<Map<String,Object>> threadContext = getThreadContext();
        threadContext.remove();
    }
}
