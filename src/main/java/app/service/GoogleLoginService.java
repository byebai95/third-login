package app.service;

import app.config.GoogleProperty;
import app.model.google.GoogleTokenVO;
import app.model.google.GoogleUserInfoVO;
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
public class GoogleLoginService {

    private final GoogleProperty googleProperty;

    public String thirdLoginByGoogle(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.info("【google 登陆】 获取 code = {}", code);

        String accessToken = getGoogleToken(code);

        return getRedirectUrl(accessToken);
    }


    public String getGoogleToken(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", googleProperty.getClientId());
        params.put("client_secret", googleProperty.getClientSecret());
        params.put("code", code);
        params.put("grant_type", googleProperty.getGrantType());
        params.put("redirect_uri", googleProperty.getRedirectUrl());

        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");

        HttpRequestVO requestToken = new HttpRequestVO();
        requestToken.createBodyRequestByMap(googleProperty.getTokenUrl(), params, header);
        String response = HttpRequestUtils.postRequest(requestToken);
        if(StringUtils.isEmpty(response)){
            return null;
        }

        log.info("google 根据 code 获取到 access_token:{}", response);
        GoogleTokenVO googleTokenVO = JsonUtils.json2obj(response, GoogleTokenVO.class);
        return googleTokenVO.getAccessToken();
    }

    public String getRedirectUrl(String accessToken){
        if(accessToken == null){
            return googleProperty.getBaseUrl() + "/error.html";
        }

        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", "Bearer "+accessToken);

        HttpRequestVO requestUserInfo = new HttpRequestVO();
        requestUserInfo.createQueryRequestByMap(googleProperty.getUserInfoUrl(),null,header);
        String response = HttpRequestUtils.getRequest(requestUserInfo);

        if(StringUtils.isEmpty(response)){
            return googleProperty.getBaseUrl() + "/error.html";
        }

        GoogleUserInfoVO googleUserInfoVO = JsonUtils.json2obj(response, GoogleUserInfoVO.class);
        log.info("获取到用户信息：{}",googleUserInfoVO);

        return googleProperty.getBaseUrl() + "/index.html";
    }
}
