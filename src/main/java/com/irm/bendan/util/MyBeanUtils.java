package com.irm.bendan.util;


import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    /**
     *获取所有的属性值为空属性名数组
     * @param source
     * @return
     */
    public static String[] getNullPropertNames(Object source) {
        BeanWrapper beanWrapper =new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertNames = pd.getName();
            if (beanWrapper.getPropertyValue(propertNames) == null){
                nullPropertNames.add(propertNames);
            }
        }

        return nullPropertNames.toArray(new String[nullPropertNames.size()]);
    }
}
