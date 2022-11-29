package com.obeast.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;


/**
 * @author wxl
 * Date 2022/11/29 17:40
 * @version 1.0
 * Description: ListValue 注解解析器
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    private final Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.vals();
        for (int val : values) {
            set.add(val);
        }
    }



    /**
     * Description: 是否合法
     * @author wxl
     * Date: 2022/11/29 17:39
     * @param value value
     * @param context  http
     * @return boolean
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
