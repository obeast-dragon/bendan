package com.obeast.common.oss.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/12/26 20:56
 * @version 1.0
 * Description: 分片文件全局共享信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlyweightRes extends HashMap<Long, Map<String, Object>> implements Serializable {

}
