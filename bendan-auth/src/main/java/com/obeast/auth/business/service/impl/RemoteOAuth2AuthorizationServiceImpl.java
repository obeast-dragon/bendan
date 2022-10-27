package com.obeast.auth.business.service.impl;

import com.obeast.auth.business.dao.BendanOAuth2AuthorizationDao;
import com.obeast.auth.business.service.OAuth2AuthorizationConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.util.Optional;

@Service("OAuth2AuthorizationService")
public class RemoteOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {
    @Autowired
    private OAuth2AuthorizationConvertor oAuth2AuthorizationConvertor;

    @Autowired
    private BendanOAuth2AuthorizationDao bendanOAuth2AuthorizationDao;
    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        OAuth2Authorization existingAuthorization = findById(authorization.getId());
        if (existingAuthorization == null) {
            insertAuthorization(authorization);
        } else {
            updateAuthorization(authorization);
        }
    }

    private void updateAuthorization(OAuth2Authorization authorization) {
        bendanOAuth2AuthorizationDao.updateById(oAuth2AuthorizationConvertor.from(authorization));
    }

    private void insertAuthorization(OAuth2Authorization authorization) {
        bendanOAuth2AuthorizationDao.insert(oAuth2AuthorizationConvertor.from(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        bendanOAuth2AuthorizationDao.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return Optional.ofNullable(bendanOAuth2AuthorizationDao.selectById(id)).map(a -> oAuth2AuthorizationConvertor.to(a)).orElse(null);
    }


    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
//        Assert.hasText(token, "token cannot be empty");
//        if (tokenType == null) {
//            return oAuth2AuthorizationConvertor.to(bendanOAuth2AuthorizationDao.findByUnknownTokenType(token));
//        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
//            return oAuth2AuthorizationConvertor.to(bendanOAuth2AuthorizationDao.findByState(token));
//        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
//            return oAuth2AuthorizationConvertor.to(bendanOAuth2AuthorizationDao.findByAuthorizationCode(token));
//        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
//            return oAuth2AuthorizationConvertor.to(bendanOAuth2AuthorizationDao.findByAccessToken(token));
//        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
//            return oAuth2AuthorizationConvertor.to(bendanOAuth2AuthorizationDao.findByRefreshToken(token));
//        }
        return null;
    }
}
