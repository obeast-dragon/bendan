package com.obeast.auth.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.obeast.auth.business.entity.SysMenuEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wxl
 * Date 2022/10/12 23:10
 * @version 1.0
 * Description:
 */
@Data
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SysMenuVo implements Serializable {


    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单名
     */
    private String title;

    /**
     * 跳转链接
     */
    private String isLink;

    /**
     * 路径
     */
    private String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SysMenuEntity> children;
}
