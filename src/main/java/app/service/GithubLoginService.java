package app.service;

import app.config.GithubProperty;
import app.model.github.GithubTokenVO;
import app.model.github.GithubUserInfoVO;
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
public class GithubLoginService {

    private final GithubProperty property;


    public String thirdLoginByGitHub(HttpServletRequest request){
        String code = request.getParameter("code");
        log.info("【Github 三方登录】 回调获取 code 成功: {}", code);

        //根据 code 拿 token
        String accessToken = getToken(code);

        //根据token 获取用户信息
        return getRedirectUrl(accessToken);
    }

    /**
     * 根据 code 获取 token
     * @param code
     * @return
     */
    private String getToken(String code){
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("client_id", property.getClientId());
        requestParam.put("client_secret", property.getClientSecret());
        requestParam.put("code", code);
        requestParam.put("redirect_uri", property.getRedirectUrl());

        HttpRequestVO requestToken = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");

        requestToken.createBodyRequestByMap(property.getTokenUrl(), requestParam, header);
        String response = HttpRequestUtils.postRequest(requestToken);

        if(response == null){
            log.error("【Github 三方登录】 请求 token 返回为空");
            return null;
        }

        GithubTokenVO githubTokenVO = JsonUtils.json2obj(response, GithubTokenVO.class);
        log.info("【Github 三方登录】 请求 token 成功：{}",githubTokenVO.getAccessToken());

        return githubTokenVO.getAccessToken();
    }

    /**
     * @param accessToken
     * @return
     */
    private String getRedirectUrl(String accessToken){

        if(accessToken == null){
            return property.getBaseUrl() + "/error.html";
        }

        HttpRequestVO requestUserInfo = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", "token "+accessToken);
        header.put("accept","application/json");

        requestUserInfo.createQueryRequestByMap(property.getUserInfoUrl(),new HashMap<>(),header);
        String response = HttpRequestUtils.getRequest(requestUserInfo);
        if(StringUtils.isEmpty(response)){
            log.error("【Github 三方登录】 查询用户信息失败");
            return property.getBaseUrl() + "/error.html";
        }

        GithubUserInfoVO githubUserInfoVO = JsonUtils.json2obj(response, GithubUserInfoVO.class);

        log.info("【Github 三方登录】 查询用户信息成功：{}",githubUserInfoVO.getLogin());
        return property.getBaseUrl() + "/index.html";
    }

}
