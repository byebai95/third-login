package app.controller;

import app.service.GithubLoginService;
import app.service.GoogleLoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@Controller
public class LoginController {

    private final GithubLoginService githubLoginService;
    private final GoogleLoginService googleLoginService;

    /**
     * 登陆跳转
     * @return
     */
    @GetMapping("/github")
    public String login() {
        return "github_login";
    }

    @GetMapping("/google")
    public String google() {
        return "google_login";
    }

    /**
     * github 回掉地址
     * @param request
     * @return
     */
    @GetMapping("/github/thirdLoginCallBack")
    public ModelAndView githubThirdLoginCallBack(HttpServletRequest request) {
        String username = githubLoginService.thirdLoginByGitHub(request);
        ModelAndView model = new ModelAndView("index");
        model.addObject("username",username);
        return model;
    }

    @GetMapping("/google/thirdLoginCallBack")
    public ModelAndView googleThirdLoginCallBack(HttpServletRequest request){
        String username = googleLoginService.thirdLoginByGoogle(request);
        ModelAndView model = new ModelAndView("index");
        model.addObject("username",username);
        return model;
    }


}











