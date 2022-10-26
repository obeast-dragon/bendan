package com.obeast.auth;

import com.nimbusds.jose.shaded.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @author wxl
 * Date 2022/10/25 13:55
 * @version 1.0
 * Description:
 */
public class OAuthTest {


    private HttpClient httpClient = HttpClients.createDefault();
    private static final String ENDPOINT = "http://localhost:18812";
    private static final String TOKEN_ENDPOINT = ENDPOINT+"/oauth2/token";

    private static final String REVOKE_TOKEN_ENDPOINT =ENDPOINT +"/oauth2/revoke";

    private Gson gson = new Gson();

    private HttpPost createHttpPost(){
        String clientId = "messaging-client3";
        String clientSecret = "secret";
        return createHttpPost(clientId,clientSecret);
    }

    private HttpPost createHttpPost(String clientId,String clientSecret){
        HttpPost httpPost = new HttpPost(TOKEN_ENDPOINT);
        String secret = (clientSecret == null ? "":clientSecret);
        httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic "+ Base64.getEncoder().encodeToString((clientId+":"+secret).getBytes())));
        return httpPost;
    }

    private void execute(HttpPost httpPost) throws IOException {
        HttpEntity entity = httpClient.execute(httpPost).getEntity();
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        System.out.println(json);
    }

    @Test
    void password(){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));
    }


    @Test
    @DisplayName("password模式")
    void testPassword() throws IOException {
        HttpPost httpPost = createHttpPost();
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"password"));
        params.add(new BasicNameValuePair(OAuth2ParameterNames.USERNAME,"user1"));
        params.add(new BasicNameValuePair(OAuth2ParameterNames.PASSWORD,"password"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        execute(httpPost);
        //{"access_token":"eyJraWQiOiI5N2JiN2FiMC1jYTgxLTRjODgtYjFjYS01OTJiZGYyMjdiNzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDcyNzMyMzEsInNjb3BlIjpbIm9wZW5pZCIsIm1lc3NhZ2UucmVhZCIsIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzI3MzUzMSwiaWF0IjoxNjQ3MjczMjMxfQ.fwT2VynDthPG-AlPeQaAD_5HusKkhmNLt23NNwZ25fSndnO2Wjw78RzGK3IZJFGfUJHhcKOU_q4gOjC8Uff0UpQ6GqaxY4ex2GGod09RNYqZPdQODJFTM_OBxsgjZdEExKJS6K-4qD-QDIMZ4JsgTnWu_ERjhMiS1OfSjx2q1jLNpelqDRl2pHvCZKsk8Ey1QaTKZeB_fck8AOrICD5DWPC7MnN2DTBac0dYc_3HOan2IW5DN2g7XkdgH8cvn0w_EN70HysC1-AHuV4Lu4rQL8ijpfEfEUejpQxlUeMCJkyBji5RnFaHlSrn07iewzyp43-egmzgoKmBaeh16SpACA","refresh_token":"BcWkO5Yu4UL1sk8JpUpABz0JnRjk7E-Vfik4smWlYjU9yWjqo6VVqyeUEHPwu5dm6h8MAi9YGR-m4i5GizzN9k_Kn73iLSfbuIl-247PU164pZl2mC1UFBZVdXYw2IKe","sub":"user1","aud":["messaging-client"],"scope":"openid message.read message.write","iss":"http://auth-server:9000","token_type":"Bearer","expires_in":300}
    }


    @Test
    @DisplayName("password并使用openid")
    void testPasswordOpenid() throws IOException {
        HttpPost httpPost = createHttpPost();
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"password"));
        params.add(new BasicNameValuePair(OAuth2ParameterNames.USERNAME,"user1"));
        params.add(new BasicNameValuePair(OAuth2ParameterNames.PASSWORD,"password"));
        params.add(new BasicNameValuePair(OAuth2ParameterNames.SCOPE,"openid"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        execute(httpPost);
    }

    @Test
    @DisplayName("客户端模式")
    void testClient() throws IOException {
        HttpPost httpPost = createHttpPost();
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair(OAuth2ParameterNames.GRANT_TYPE,"client_credentials"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        execute(httpPost);
        //{"access_token":"eyJraWQiOiI5N2JiN2FiMC1jYTgxLTRjODgtYjFjYS01OTJiZGYyMjdiNzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY0NzI3MzIwNiwic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOlwvXC9hdXRoLXNlcnZlcjo5MDAwIiwiZXhwIjoxNjQ3MjczNTA2LCJpYXQiOjE2NDcyNzMyMDZ9.O7zKZ8PiVdnfUlKmbwJoBg5LAmo6-N6L420lKp05JdSOQkhLjoRvnyggDa1qh2J6HzuD9um7-mPSCZXqOwCojqyx6mpiBP3Hdbz6uug4fdtzdWv78peW8plVtoNz8c8-8v0dxIHkvCWBdnLCPPROdclCXMUHLWSXusW7B2bR6CFp_MXRMoxUP7MuGbgElytmhpxkqx4Vfc0NOJgf8E3-PwBwHb6-heMeRFekdvSUmMZgFHgTcxSv95grlw-Gf4qAKYOCQP4ao2UA_YyfEGMS5CgdQmMjIpdHpgO9U2lfmLRunHqnnro6zpX6TO1I4JbJnOVy-0pig6vC1uMyb_gzNw","scope":"openid message.read message.write","token_type":"Bearer","expires_in":299}
    }
}
