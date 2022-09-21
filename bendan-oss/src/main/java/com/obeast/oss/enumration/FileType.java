package com.obeast.oss.enumration;

public enum FileType {
    Image(1, " 图片"),
    Attachment(2, "附件"),
    Document(2, "文档"),
    Excel(2, "Excel"),
    Video(2, "视频"),
    Audio(2, "音频"),
    License(2, "证书");

    private final Integer code;
    private final String desc;

    FileType(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
