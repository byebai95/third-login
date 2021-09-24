package app.controller;

import app.config.GithubProperty;
import app.model.TokenVO;
import app.model.UserInfoVO;
import app.util.HttpRequestUtils;
import app.util.HttpRequestVO;
import app.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class LoginController {

    private final GithubProperty property;


    @GetMapping
    public String login() {
        return "login";
    }

    @GetMapping("/thirdLoginCallBack")
    public ModelAndView thirdLoginCallBack(HttpServletRequest request) {

        String code = request.getParameter("code");
        log.info("回调获取 code : {}", code);

        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("client_id", property.getClientId());
        requestParam.put("client_secret", property.getClientSecret());
        requestParam.put("code", code);
        requestParam.put("redirect_uri", property.getRedirectUri());
        //根据 code 拿 token
        HttpRequestVO requestToken = new HttpRequestVO();
        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");
        requestToken.createBodyRequestByMap(property.getTokenUri(), requestParam, header);
        String response = HttpRequestUtils.postRequest(requestToken);
        log.info("根据 code 获取到 access_token:{}", response);
        TokenVO tokenVO = JsonUtils.json2obj(response,TokenVO.class);
        log.info("access_token:{}",tokenVO.getAccessToken());

        //根据token 获取用户信息
        HttpRequestVO requestUserInfo = new HttpRequestVO();
        Map<String, Object> header2 = new HashMap<>();
        header2.put("Authorization", "token "+tokenVO.getAccessToken());
        header2.put("accept","application/json");
        requestUserInfo.createQueryRequestByMap(property.getUserInfo(),null,header2);
        String response2 = HttpRequestUtils.getRequest(requestUserInfo);
        log.info("获取到用户信息：{}",response2);
        UserInfoVO userInfoVO = JsonUtils.json2obj(response2,UserInfoVO.class);
        ModelAndView model = new ModelAndView();
        model.addObject("username",userInfoVO.getLogin());
        model.setViewName("index");
        log.info(userInfoVO.getLogin());
        return model;
    }


}
