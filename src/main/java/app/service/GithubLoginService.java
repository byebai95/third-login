package app.service;

import app.config.GithubProperty;
import app.model.github.TokenVO;
import app.model.github.UserInfoVO;
import app.util.HttpRequestUtils;
import app.util.HttpRequestVO;
import app.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        log.info("回调获取 code : {}", code);

        //根据 code 拿 token
        String accessToken = getToken(code);

        //根据token 获取用户信息
        String username = getUserName(accessToken);

        return username;
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
        requestParam.put("redirect_uri", property.getRedirectUri());
        HttpRequestVO requestToken = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");
        requestToken.createBodyRequestByMap(property.getTokenUri(), requestParam, header);
        String response = HttpRequestUtils.postRequest(requestToken);
        log.info("根据 code 获取到 access_token:{}", response);
        TokenVO tokenVO = JsonUtils.json2obj(response,TokenVO.class);
        log.info("access_token:{}",tokenVO.getAccessToken());
        return tokenVO.getAccessToken();
    }

    /**
     * 根据 token 获取用户信息
     * @param accessToken
     * @return
     */
    private String getUserName(String accessToken){
        HttpRequestVO requestUserInfo = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", "token "+accessToken);
        header.put("accept","application/json");
        requestUserInfo.createQueryRequestByMap(property.getUserInfo(),null,header);
        String response = HttpRequestUtils.getRequest(requestUserInfo);
        log.info("获取到用户信息：{}",response);
        UserInfoVO userInfoVO = JsonUtils.json2obj(response,UserInfoVO.class);
        return userInfoVO.getLogin();
    }

}
