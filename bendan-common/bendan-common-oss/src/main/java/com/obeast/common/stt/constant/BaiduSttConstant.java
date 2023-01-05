package com.obeast.common.stt.constant;

/**
 * @author wxl
 * Date 2023/1/3 13:29
 * @version 1.0
 * Description: stt常量
 */
public interface BaiduSttConstant {

    enum FileAsrSuffix {
        PCM("pcm"),
        WAV("wav")
        ;
        private final String name;

        FileAsrSuffix(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
