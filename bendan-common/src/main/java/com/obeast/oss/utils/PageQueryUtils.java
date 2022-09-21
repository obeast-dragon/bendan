package com.obeast.oss.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.obeast.oss.constant.PageConstant;
import com.obeast.oss.domain.PageObjects;
import com.obeast.oss.xss.SQLFilter;

import java.util.List;
import java.util.Map;

/**
 * @author wxl
 * @version 1.0
 * @description: 分页查询工具类
 * @date 2022/7/16 22:19
 */
public class PageQueryUtils<T> {


    public <T> PageObjects<T> getPageObjects(IPage<T> page, Class<T> targetClass) {
        return getPageObjects(page.getRecords(), page.getTotal(), targetClass);

    }

    public  <T> PageObjects<T> getPageObjects(List<?> items, long total, Class<T> targetClass) {
        List<T> targetList = ClassUtils.sourceToTarget(items, targetClass);
        return new PageObjects<>(total, targetList);

    }


    /**
     * @description:
     * @author wxl
     * @date 2022/7/26 17:13
     * @param params -> page : v; limit : v 分页参数
     * @param defaultOrderField 排序字段
     **/
    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField,  boolean isAsc) {
        long curPage = 1;
        long limit = 10;

//        测试用
//        if (params.get(PageConstant.CUR) != null) {
//            Integer integer = (Integer) params.get(PageConstant.CUR);
//            curPage = integer.longValue();
//        }
//        if (params.get(PageConstant.LIMIT) != null) {
//            Integer integer = (Integer) params.get(PageConstant.LIMIT);
//            limit = integer.longValue();
//        }

//        接口用
        if (params.get(PageConstant.CUR) != null) {
            curPage = Long.parseLong((String) params.get(PageConstant.CUR));
        }
        if (params.get(PageConstant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(PageConstant.LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(PageConstant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String) params.get(PageConstant.ORDER_FIELD));
        String order = (String) params.get(PageConstant.ORDER);

        //前端字段排序
        if (StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)) {
            if (PageConstant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }


}
