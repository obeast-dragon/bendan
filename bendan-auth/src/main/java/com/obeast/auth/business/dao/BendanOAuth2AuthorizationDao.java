package com.obeast.auth.business.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.obeast.auth.business.entity.BendanOAuth2Authorization;
import com.obeast.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wxl
 * Date 2022/10/25 13:34
 * @version 1.0
 * Description:
 */
@Mapper
public interface BendanOAuth2AuthorizationDao extends BaseDao<BendanOAuth2Authorization> {

    /**
     * Description: 通过未知token类型查找认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param unknownToken 未知token
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    default BendanOAuth2Authorization findByUnknownTokenType(String unknownToken){
        List<BendanOAuth2Authorization> list = this.selectList(
                new LambdaQueryWrapper<BendanOAuth2Authorization>()
                        .eq(BendanOAuth2Authorization::getAccessTokenType, unknownToken)
                        .or()
                        .eq(BendanOAuth2Authorization::getState, unknownToken)
                        .or()
                        .eq(BendanOAuth2Authorization::getAuthorizationCodeValue, unknownToken)
                        .or()
                        .eq(BendanOAuth2Authorization::getAccessTokenValue, unknownToken)
                        .or()
                        .eq(BendanOAuth2Authorization::getRefreshTokenValue, unknownToken)
        );
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }


    /**
     * Description: 通过state查询认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param state state
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    default BendanOAuth2Authorization findByState(String state){
        List<BendanOAuth2Authorization> list = this.selectList(
                new LambdaQueryWrapper<BendanOAuth2Authorization>()
                        .eq(BendanOAuth2Authorization::getState, state)
        );
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }


    /**
     * Description: 通过授权码获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param authorizationCode 授权码
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    default BendanOAuth2Authorization findByAuthorizationCode(String authorizationCode){
        List<BendanOAuth2Authorization> list = this.selectList(
                new LambdaQueryWrapper<BendanOAuth2Authorization>()
                        .eq(BendanOAuth2Authorization::getAuthorizationCodeValue, authorizationCode)
        );
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }


    /**
     * Description: 通过访问令牌获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param accessToken accessToken
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    default BendanOAuth2Authorization findByAccessToken(String accessToken){
        List<BendanOAuth2Authorization> list = this.selectList(
                new LambdaQueryWrapper<BendanOAuth2Authorization>()
                        .eq(BendanOAuth2Authorization::getAccessTokenValue, accessToken)

        );
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * Description: 通过刷新令牌获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:48
     * @param refreshToken refreshToken
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    default BendanOAuth2Authorization findByRefreshToken(String refreshToken){
        List<BendanOAuth2Authorization> list = this.selectList(
                new LambdaQueryWrapper<BendanOAuth2Authorization>()
                        .eq(BendanOAuth2Authorization::getRefreshTokenValue, refreshToken)
        );
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

}
