package com.obeast.bendan.common.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wxl
 * @version 1.0
 * @description: 类工具
 * @date 2022/7/27 11:17
 */
@Slf4j
public class ClassUtils {

    public static <T> T sourceToTarget(Object source, Class<T> target){
        if (source == null) {
            return null;
        }

        T targetObject = null;
        try{
            targetObject = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetObject);
        }catch (Exception e){
            log.error("Exception while converting source to target is error");

        }

        return targetObject;
    }
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) return null;

        List<T> targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList){
                T targetObject = target.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception while converting sourceList to target ara error");
        }
        return targetList;
    }


    /**
     * Description: 获取对象属性
     * @author wxl
     * Date: 2022/8/11 15:21
     * @param clazz class
     * @return java.util.Map<java.lang.Integer,java.lang.String>
     */
    @SneakyThrows
    public static <T> Map<Integer, String> getProperty(Class<T> clazz){
        Map<Integer, String> operatorCache = new HashMap<>();
        T t = clazz.getDeclaredConstructor().newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            String name = declaredFields[i].getName();
            operatorCache.put((Integer) declaredFields[i].get(t), name);
        }
        return operatorCache;
    }




}
