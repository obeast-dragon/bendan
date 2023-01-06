package com.obeast.common.stt.config;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Util;
import org.json.JSONObject;


/**
 * @author wxl
 * Date 2023/1/6 13:46
 * @version 1.0
 * Description: ats 转写客户端
 */
public class AtsSpeech extends BaseClient {

    protected AtsSpeech(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * Description: 创建翻译任务
     * @author wxl
     * Date: 2023/1/6 14:03
     * @param speechUrl speechUrl
     * @param format format
     * @param pid pid
     * @param rate rate
     * @return org.json.JSONObject
     */
    public JSONObject atsTask(String speechUrl, String format, int pid, int rate) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE, "No permission to access data");
        } else {
            request.addBody("speech_url", speechUrl);
            request.addBody("format", format);
            request.addBody("pid", pid);
            request.addBody("rate", rate);
            request.setUri("https://aip.baidubce.com/rpc/2.0/aasr/v1/create?access_token=" + this.accessToken);
            request.setBodyFormat(EBodyFormat.RAW_JSON);
            request.addHeader("Content-Type", "application/json");
            return this.requestServer(request);
        }
    }

    /**
     * Description: 查询翻译任务的结果
     * @author wxl
     * Date: 2023/1/6 14:02
     * @param taskIds task IDs
     * @return org.json.JSONObject
     */
    public JSONObject atsTaskResult(String[] taskIds) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE, "No permission to access data");
        } else {
            request.addBody("task_ids", taskIds);
            request.setUri("https://aip.baidubce.com/rpc/2.0/aasr/v1/query?access_token=" + this.accessToken);
            request.setBodyFormat(EBodyFormat.RAW_JSON);
            request.addHeader("Content-Type", "application/json");
            return this.requestServer(request);
        }
    }
}
