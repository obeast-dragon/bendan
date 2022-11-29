package com.obeast.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 * @version 1.0
 * @description: EasyExcel工具类
 * @date 2022/8/4 17:06
 */
@Slf4j
public class EasyExcelUtils<T> {

    /**
     * 操作数据实体类
     */
    private final Class<T> clazz;



    public EasyExcelUtils(Class<T> clazz) {
        this.clazz = clazz;
    }

    public EasyExcelUtils(){this.clazz = null;}


    /**
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     *                  throws IOException
     * @description: 对list数据源将其里面的数据导入到excel表单
     * @author wxl
     * Date 2022/8/4 17:35
     **/

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String fileName) {
        exportExcel(response, list, sheetName, fileName, null);
    }
    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String fileName, Map<Integer, List<Integer>> mapList) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
        try {
            ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), clazz);

            write
                    .sheet(sheetName)
                    .doWrite(list);
//            log.info("导出{}文件成功", URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
            log.info("导出{}文件成功", fileName + ".xlsx");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("create excel file failed");
    }



//    /**
//     * Description: 动态修改注解的常量
//     *
//     * @param clazz       注解所在的实体类的字节码对象
//     * @param claaa       注解类的字节码对象
//     * @param annotationName 要修改的注解属性名
//     * @param columnsName 要修改的字段属性名
//     * @param value       设置的值
//     * @author wxl
//     * Date: 2022/9/5 16:26
//     */
//    @SneakyThrows
//    private static <A extends Annotation> void setColumnsValue(Class<?> clazz, Class<A> claaa, String annotationName, String columnsName, Object value) throws NoSuchFieldException {
//        Field field = clazz.getDeclaredField(columnsName);
//        A annotation = field.getAnnotation(claaa);
//        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
//        Field memberValues = handler.getClass().getDeclaredField("memberValues");
//        memberValues.setAccessible(true);
//        Map memberValue = (Map) memberValues.get(handler);
//        memberValue.put(annotationName, value);
//    }



}
