package com.obeast.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wxl
 * Date 2022/10/4 10:56
 * @version 1.0
 * Description: mybatis-plus自动填充配置
 */
@Component("FieldFillConfig")
public class FieldFillConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
//        帐号启用状态：0->禁用；1->启用
        this.setFieldValByName("disableStatus", 1, metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
