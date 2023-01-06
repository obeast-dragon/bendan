package com.obeast.common.stt.template;

import cn.hutool.core.util.StrUtil;
import com.obeast.common.stt.config.AtsSpeech;
import com.obeast.common.stt.constant.BaiduSttConstant;
import com.obeast.core.exception.BendanException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.Assert;


/**
 * @author wxl
 * Date 2023/1/4 17:35
 * @version 1.0
 * Description: baidu stt 模板类
 */
@RequiredArgsConstructor
@Slf4j
public class BaiduSttTemplate {

    private final AtsSpeech atsSpeech;


    /**
     * Description: 创建翻译任务
     *
     * @param url           url
     * @param fileAsrSuffix fileAsrSuffix
     * @return org.json.JSONObject
     * @author wxl
     * Date: 2023/1/6 14:27
     */
    public String atsTask(String url, BaiduSttConstant.FileAsrSuffix fileAsrSuffix) {
        JSONObject jsonObject = atsSpeech.atsTask(url, fileAsrSuffix.getName(), BaiduSttConstant.Pid.PID_80001.getNum(), BaiduSttConstant.rate);
        Assert.notNull(jsonObject, "jsonObject can not be null");
        return getTaskId(jsonObject);
    }


    /**
     * Description: 查询翻译任务的结果
     *
     * @param taskIds taskIds
     * @return org.json.JSONObject
     * @author wxl
     * Date: 2023/1/6 14:28
     */
    public JSONArray atsTaskResult(String taskIds) {
        JSONObject jsonObject = atsSpeech.atsTaskResult(new String[]{taskIds});
        log.error("jsonObject: {}", jsonObject);
        JSONArray tasksInfo = jsonObject.getJSONArray("tasks_info");
        JSONObject json = (JSONObject) tasksInfo.get(0);
        String checkTaskStatus = checkTaskStatus(json);
        if (checkTaskStatus.equals(BaiduSttConstant.TaskStatus.Success.name())) {
            JSONObject taskResult = json.getJSONObject("task_result");
            return taskResult.getJSONArray("result");
        } else if (checkTaskStatus.equals(BaiduSttConstant.TaskStatus.Running.name())) {
            atsTaskResult(taskIds);
        }
        throw new BendanException(BaiduSttConstant.TaskStatus.Failure.getName());
    }

    /**
     * Description: 检查任务状态
     * @author wxl
     * Date: 2023/1/6 17:02
     * @param json  json
     * @return java.lang.String
     */
    private String checkTaskStatus(JSONObject json) {
        String taskStatus = json.getString("task_status");
        if (StrUtil.isEmpty(taskStatus)) {
            log.warn(BaiduSttConstant.TaskStatus.Failure + "{}", json.getString("error_msg"));
            return json.getString("error_msg");
        } else {
            return taskStatus;
        }
    }


    /**
     * Description: 获取任务id
     * @author wxl
     * Date: 2023/1/6 17:02
     * @param json json
     * @return java.lang.String
     */
    private String getTaskId(JSONObject json) {
        if (checkTaskStatus(json).equals(BaiduSttConstant.TaskStatus.Created.name())) {
            return json.getString("task_id");
        }
        return null;
    }


}
