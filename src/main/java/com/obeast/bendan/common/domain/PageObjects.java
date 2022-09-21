package com.obeast.bendan.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author wxl
 * Date 2022/8/12 14:43
 * @version 1.0
 * Description: 分页数据
 */
@Data
@NoArgsConstructor
public class PageObjects<T> implements Serializable {
    /**
     * 查询总数
     * */
    private Long total;

    /**
     * 列表数据
     * */
    private List<T> items;

    public PageObjects(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
