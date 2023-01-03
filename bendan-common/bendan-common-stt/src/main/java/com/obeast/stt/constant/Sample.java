package com.obeast.stt.constant;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;

class Sample {
    public static final String API_KEY = "Cj0nryiS9e1VQv0Ap6BIL9N2";
    public static final String SECRET_KEY = "GdoNzVHXXaIG77tUIoDAao4lu7HGRvdL";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

//    {"log_id":16727269543801759,"task_status":"Created","task_id":"63b3c9aae21bb100014a869b"}

//    public static void main(String []args) throws IOException{
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"speech_url\":\"http://127.0.0.1:9000/bendan/1-sdk_demo.pcm?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=P5NPYXQZ24G0TMO4X60F%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T064157Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJQNU5QWVhRWjI0RzBUTU80WDYwRiIsImV4cCI6MTY3Mjc2OTg1MSwicGFyZW50IjoibWluaW9yb290In0.vsWkoODEfy1efITIBuOz8P9d1TTxGcJWrvfOfrZNLDBm7qZKDS3TBaZS_MIMDUv9uiswdFjWq7J0b_s9uKAjLQ&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=21f35de2f453465c97344401521a3cbfc59b3736c601149cb6995f42bfd99f9b\",\"format\":\"pcm\",\"pid\":80001,\"rate\":16000}");
//        Request request = new Request.Builder()
//            .url("https://aip.baidubce.com/rpc/2.0/aasr/v1/create?access_token=" + getAccessToken())
//            .method("POST", body)
//            .addHeader("Content-Type", "application/json")
//            .addHeader("Accept", "application/json")
//            .build();
//        Response response = HTTP_CLIENT.newCall(request).execute();
//        System.out.println(response.body().string());
//
//    }

    public static void main(String []args) throws IOException{
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"task_ids\":[\"63b3ce74de4c0b0001c91fce\"]}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/aasr/v1/query?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());

    }
    
    
    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
    
}