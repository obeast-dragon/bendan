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
//    default BendanOAuth2Authorization findByUnknownTokenType(String unknownToken){
//        List<BendanOAuth2Authorization> list = this.selectList(
////                new QueryWrapper<BendanOAuth2Authorization>("")
//        );
//    }


    /**
     * Description: 通过state查询认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param state state
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    BendanOAuth2Authorization findByState(String state);


    /**
     * Description: 通过授权码获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param authorizationCode 授权码
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    BendanOAuth2Authorization findByAuthorizationCode(String authorizationCode);


    /**
     * Description: 通过访问令牌获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:46
     * @param accessToken accessToken
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    BendanOAuth2Authorization findByAccessToken(String accessToken);

    /**
     * Description: 通过刷新令牌获取认证信息
     * @author wxl
     * Date: 2022/10/26 18:48
     * @param refreshToken refreshToken
     * @return com.obeast.auth.business.entity.BendanOAuth2Authorization
     */
    BendanOAuth2Authorization findByRefreshToken(String refreshToken);

}
