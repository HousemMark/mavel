package com.skyline.merchant.dal.mapper.ext;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantRoleExtMapper {

    List<String> searchRolesByUserId(@Param("userId") Long userId);

}
