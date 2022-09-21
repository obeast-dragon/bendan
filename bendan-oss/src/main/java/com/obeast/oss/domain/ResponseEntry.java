package com.obeast.oss.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * @version 1.0
 * @description: 分片文件全局共享信息
 * @date 2022/7/6 18:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class ResponseEntry extends HashMap<String, Map<String, Object>> implements Serializable {


}
