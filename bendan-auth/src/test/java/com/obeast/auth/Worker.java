package com.obeast.auth;

import lombok.Builder;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author wxl
 * Date 2022/11/1 9:37
 * @version 1.0
 * Description:
 */
@Builder
public class Worker {
    String username;
    String password;
    String email;
    LocalDateTime createTime;
    Instant UpdateTime;
}
