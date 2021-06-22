package com.skyline.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.skyline.gateway.entity.SelfUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     * @Param  authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
        SelfUserEntity selfUserEntity = (SelfUserEntity)authentication.getPrincipal();
        log.info("------------- Mark's record -----------");
        log.info("【authentication】："+JSONObject.toJSONString(authentication));
        log.info(JSONObject.toJSONString(targetUrl));
        log.info(JSONObject.toJSONString(permission));
        log.info("------------- Mark's record -----------");
        // 查询用户权限(这里可以将权限放入缓存中提升效率)
//        Set<String> permissions = new HashSet<>();
//        EntityWrapper<Usermeta> roleWrapper = new EntityWrapper<>();
//        roleWrapper.eq("user_id",selfUserEntity.getUserId());
//        roleWrapper.eq("meta_key","wp_user_level");
//        List<Usermeta> sysMenuEntityList = usermetaService.selectList(roleWrapper);
//        for (Usermeta sysMenuEntity:sysMenuEntityList) {
//            permissions.add(sysMenuEntity.getMetaValue());
//        }
//        // 权限对比
//        if (permissions.contains(permission.toString())){
//            return true;
//        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
