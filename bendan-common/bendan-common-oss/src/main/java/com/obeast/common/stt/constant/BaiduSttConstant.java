package com.obeast.common.stt.constant;

/**
 * @author wxl
 * Date 2023/1/3 13:29
 * @version 1.0
 * Description: stt常量
 */
public interface BaiduSttConstant {

    /**
     * 采样率
     */
    Integer rate = 16000;

    /**
     * @author wxl
     * Date 2023/1/6 14:30
     * @version 1.0
     * Description: 音频文件枚举
     */
    enum FileAsrSuffix {
        PCM("pcm"),
        WAV("wav"),
        ;
        private final String name;

        FileAsrSuffix(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }



    /**
     * @author wxl
     * Date 2023/1/6 14:30
     * @version 1.0
     * Description: pid的参数枚举
     */
    enum Pid {
        PID_80001(80001),
        PID_1737(1737),
        ;
        private final Integer num;

        Pid(Integer num) {
            this.num = num;
        }

        public Integer getNum() {
            return num;
        }
    }


    /**
     * @author wxl
     * Date 2023/1/6 14:37
     * @version 1.0
     * Description: 任务状态
     */
    enum TaskStatus {

        Success("翻译成功"),

        Running("翻译进行中"),

        Failure("翻译失败"),

        Created("翻译任务创建"),
        ;
        private final String name;

        TaskStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
