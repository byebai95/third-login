package app.service;

import app.config.FacebookProperty;
import app.model.facebook.FaceBookTokenVO;
import app.util.HttpRequestUtils;
import app.util.HttpRequestVO;
import app.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Component
public class FaceBookLoginService {

    private final FacebookProperty property;

    public String thirdLoginByFaceBook(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.info("code = {}", code);

        //获取 token
        String token = getToken(code);

        //获取重定向地址
        return getRedirectUrl(token);
    }

    private String getToken(String code){
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("client_id", property.getClientId());
        requestParam.put("redirect_uri", property.getRedirectUrl());
        requestParam.put("client_secret", property.getClientSecret());
        requestParam.put("code", code);

        HttpRequestVO requestToken = new HttpRequestVO();

        requestToken.createQueryRequestByMap(property.getTokenUrl(), requestParam,new HashMap<>());
        String response = HttpRequestUtils.getRequest(requestToken);

        if(response == null){
            return null;
        }

        log.info("根据 code 获取到 access_token:{}", response);
        FaceBookTokenVO tokenVO = JsonUtils.json2obj(response, FaceBookTokenVO.class);
        log.info("access_token:{}",tokenVO.getAccessToken());
        return tokenVO.getAccessToken();
    }

    private String getRedirectUrl(String accessToken){

        if(accessToken == null){
            return property.getBaseUrl() + "/error.html";
        }

        HttpRequestVO requestUserInfo = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", accessToken);
        header.put("accept","application/json");

        requestUserInfo.createQueryRequestByMap(property.getUserInfoUrl(),null,header);
        String response = HttpRequestUtils.getRequest(requestUserInfo);
        if(StringUtils.isEmpty(response)){
            return property.getBaseUrl() + "/error.html";
        }

        log.info("获取到用户信息：{}",response);

        return property.getBaseUrl() + "/index.html";
    }
}
