package com.skyline.shield.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * 获取TTL上下文工具包（单例）
 */
public class ThreadContextUtil extends AbstractThreadContext {

    private static ThreadLocal<Map<String, Object>> threadLocal = new TransmittableThreadLocal();

    private volatile static ThreadContextUtil app = null;

    // 关闭外界创建途径
    private ThreadContextUtil() {
    }

    // 单例模式
    public static synchronized ThreadContextUtil getInstance() {
        if (app == null) {
            synchronized (ThreadContextUtil.class) {
                if (app == null) {
                    app = new ThreadContextUtil();
                }
            }
        }
        return app;
    }

    @Override
    protected ThreadLocal<Map<String, Object>> getThreadContext() {
        return threadLocal;
    }
}
