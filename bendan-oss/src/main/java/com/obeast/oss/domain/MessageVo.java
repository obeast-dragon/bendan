package com.obeast.oss.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 * @version 1.0
 * @description: SseEmitterVo
 * @date 2022/7/8 13:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 传输数据体(json)
     */
    private String data;
}
