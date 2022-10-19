package com.obeast.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.blog.entity.BlogEntity;
import com.obeast.blog.excel.BlogExcel;
import com.obeast.common.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
public interface BlogService extends IService<BlogEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param params 分页参数
     * @return PageObjects<BlogEntity>
     */
    PageObjects<BlogEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param id id  of entity
     * @return BlogEntity
     */
     BlogEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<BlogEntity>
     */
    List<BlogEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<BlogEntity>
     */
    List<BlogEntity> queryByConditions();


    /**
     * Description: 根据条件查询Excel
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @return List<BlogExcel>
     */
    List<BlogExcel> queryExcelByConditions();

    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param blogEntity entity object
     * @return boolean
     */
    boolean add(BlogEntity blogEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<BlogEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param blogEntity entity object
     * @return boolean
     */
    boolean replace(BlogEntity blogEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<BlogEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-09-21 12:18:12
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

