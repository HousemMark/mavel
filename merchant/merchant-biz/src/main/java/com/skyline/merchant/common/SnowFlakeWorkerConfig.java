package com.skyline.merchant.common;

import cn.hutool.core.net.NetUtil;
import com.skyline.merchant.common.enums.MerchantReturnCode;
import com.skyline.shield.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeWorkerConfig implements ApplicationRunner {

    Logger log = LoggerFactory.getLogger(SnowFlakeWorkerConfig.class);
    private static Long MAX_WORKER_ID = 31L;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CommonFields.workerId = initId();
    }

    private Long initId() {
        Long workerId = null;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) % MAX_WORKER_ID;
            log.debug("------ Mark's record : workerId =" + workerId);
        } catch (Exception e) {
            log.error("获取当前机器码失败...");
            throw new ServiceException(MerchantReturnCode.ERROR_4001);
        }
        return workerId;
    }
}
