package com.obeast.business.vo;

import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/28 15:25
 * @version 1.0
 * Description: OAuth2RefreshVo
 */
@Data
public class OAuth2RefreshVo  {

    String refresh_token;

    String grant_type;
}
