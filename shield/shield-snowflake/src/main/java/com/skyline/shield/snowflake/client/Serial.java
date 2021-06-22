package com.skyline.shield.snowflake.client;

import com.skyline.shield.snowflake.enums.SnowFlakeEntityEnum;
import com.skyline.shield.snowflake.exception.SnowFlakeException;

public interface Serial {

    Long getLongSerialNum() throws SnowFlakeException;

    Long getLongSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException;

    String getStringSerialNum() throws SnowFlakeException;

    String getStringSerialNum(SnowFlakeEntityEnum snowFlakeEntityEnum) throws SnowFlakeException;
}
