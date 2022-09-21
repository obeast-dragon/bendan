package com.obeast.bendan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.bendan.entity.TypeEntity;
import com.obeast.bendan.excel.TypeExcel;
import com.obeast.bendan.common.domain.PageObjects;

import java.util.Map;
import java.util.List;


/**
 * @author obeast-dragon
 * Date 2022-09-21 10:03:18
 * @version 1.0
 * Description: 
 */
public interface TypeService extends IService<TypeEntity> {


    /**
     * Description: 分页查询
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param params 分页参数
     * @return PageObjects<TypeEntity>
     */
    PageObjects<TypeEntity> queryPage(Map<String, Object> params);


    /**
     * Description: 根据Id查询
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param id id  of entity
     * @return TypeEntity
     */
     TypeEntity queryById(Long id);



    /**
     * Description: 查询所有
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @return List<TypeEntity>
     */
    List<TypeEntity> queryAll();


    /**
     * Description: 根据条件查询
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @return List<TypeEntity>
     */
    List<TypeEntity> queryByConditions();


    /**
     * Description: 根据条件查询Excel
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @return List<TypeExcel>
     */
    List<TypeExcel> queryExcelByConditions();

    /**
     * Description: 新增
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param typeEntity entity object
     * @return boolean
     */
    boolean add(TypeEntity typeEntity);


    /**
     * Description: 批量新增
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param data entity objects
     * @return boolean
     */
    boolean addList(List<TypeEntity> data);


    /**
     * Description: 更新
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param typeEntity entity object
     * @return boolean
     */
    boolean replace(TypeEntity typeEntity);


    /**
     * Description: 批量更新
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param data entity objects
     * @return boolean
     */
    boolean replaceList(List<TypeEntity> data);


    /**
     * Description: 根据Id删除
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param id id of entity
     * @return boolean
     */
    boolean deleteById(Long id);


    /**
     * Description: 根据Id批量删除
     * @author obeast-dragon
     * Date: 2022-09-21 10:03:18
     * @param ids list of ids
     * @return boolean
     */
    boolean deleteByIds(List<Long> ids);

}

