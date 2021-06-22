package com.skyline.shield.snowflake.client;

import com.skyline.shield.snowflake.enums.SnowFlakeEntityEnum;
import com.skyline.shield.snowflake.exception.SnowFlakeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeSerial implements Serial {

    private static final Logger log = LoggerFactory.getLogger(SnowFlakeSerial.class);

    @Override
    public Long getLongSerialNum() throws SnowFlakeException {
        return null;
    }

    @Override
    public Long getLongSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException {
        return null;
    }

    @Override
    public String getStringSerialNum() throws SnowFlakeException {
        return null;
    }

    @Override
    public String getStringSerialNum(SnowFlakeEntityEnum snowFlakeEntityEnum) throws SnowFlakeException {
        return null;
    }
}
