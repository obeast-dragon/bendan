package com.obeast.stt;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

public class SttUtil {
    static class SttUtilClient {
        public static final String APP_ID = "28296012";
        public static final String API_KEY = "Cj0nryiS9e1VQv0Ap6BIL9N2";
        public static final String SECRET_KEY = "GdoNzVHXXaIG77tUIoDAao4lu7HGRvdL";

        public static final AipSpeech AIP_SPEECH = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

    }

    /**
     * .m4a 用不了
     * Description: 获取单例AipSpeech
     * @author wxl
     * Date: 2023/1/3 13:50
     * @return com.baidu.aip.speech.AipSpeech
     */
    public static AipSpeech getInstance(){
        return SttUtilClient.AIP_SPEECH;
    }


    public static void main(String[] args) throws Exception {
//        String url = "bendan-common/bendan-common-stt/src/main/resources/test.wav";
        String url = """
                http://175.178.183.32:9000/bendan/1-16k.pcm?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T055514Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=13f8e4407d04ce2b969df07e72d68c136148b8df6c6f44af729c70d7c5fafd82
                """;


//        String url = "src/main/resources/16k.pcm";
        stt_Client(url);
    }

    /**
     * Description: 语音识别 客户端
     *
     * @author wxl
     * Date: 2022/11/10 10:27
     */
    public static void stt_Client(String path) {
        // 对本地语音文件进行识别
        JSONObject asrRes = getInstance().asr(path, "wav", 16000, null);
        System.out.println(asrRes);
    }


//    /**
//     * Description: 语音识别  无客户端
//     *
//     * @return java.lang.String
//     * @author wxl
//     * Date: 2022/11/10 10:27
//     */
//    public static String stt_No_Client(String url) throws Exception {
//        String bodyStr = """
//                {
//                    "format":"wav",
//                    "rate":16000,
//                    "dev_pid":80001,
//                    "channel":1,
//                    "token":xxx,
//                    "cuid":"baidu_workshop",
//                    "len":4096,
//                    "speech":"xxx"
//                }
//                """;
//        String[] split = url.split("\\.");
//        String Format = split[1];
//        try {
//            List<Serializable> res = getBase64AndLength(url);
//            Serializable length = res.get(0);
//            String speech = (String) res.get(1);
//            JSONObject body = new JSONObject(bodyStr);
//            body.put("token", getOAuth(API_KEY, SECRET_KEY));
//            body.put("speech", speech);
//            body.put("len", length);
//            body.put("format", Format);
//            HttpClient httpClient = getHttpClient();
//            HttpRequest httpRequest = HttpRequest.newBuilder()
//                    .uri(URI.create("https://vop.baidu.com/pro_api"))
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
//                    .build();
//            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            return response.body();
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//    }


//    /**
//     * Description: 获取token
//     *
//     * @param ak API_KEY
//     * @param sk SECRET_KEY
//     * @return java.lang.String
//     * @author wxl
//     * Date: 2022/11/10 9:48
//     * token :24.c163bc97d8cd35e60c55d30a213b0cf9.2592000.1670638172.282335-28296012
//     */
//    public static String getOAuth(String ak, String sk) throws IOException, InterruptedException {
//        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
//        String getAccessTokenUrl = authHost
//                // 1. grant_type为固定参数
//                + "grant_type=client_credentials"
//                // 2. 官网获取的 API Key
//                + "&client_id=" + ak
//                // 3. 官网获取的 Secret Key
//                + "&client_secret=" + sk;
//        HttpClient httpClient = getHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(getAccessTokenUrl))
//                .timeout(Duration.ofSeconds(2))
//                .build();
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        JSONObject jsonObject = new JSONObject(response.body());
//        return jsonObject.getString("access_token");
//
//    }
//
//    public static HttpClient getHttpClient() {
//        return HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_1_1)
//                .build();
//    }
//
//    private static List<Serializable> getBase64AndLength(String filePath) throws Exception {
//        try {
//            FileInputStream inputFile = new FileInputStream(filePath);
//            byte[] bytes = inputFile.readAllBytes();
//            String base64 = Base64.getEncoder().encodeToString(bytes);
//            return Arrays.asList(bytes.length, base64);
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//    }

}