package com.obeast.core.config.fegin;

import com.obeast.core.constant.BendanResHeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Collections;

/**
 * @author wxl
 * Date 2022/11/22 15:40
 * @version 1.0
 * Description: feign 调用添加头部
 */
@AutoConfiguration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(BendanResHeaderConstant.from, Collections.singletonList(BendanResHeaderConstant.bendanValue));
    }
}
