package kr.kdev.demo.controller;

import kr.kdev.demo.bean.CurrentUser;
import kr.kdev.demo.bean.User;
import kr.kdev.demo.bean.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;


@Controller
public class IndexController {

    private static Logger LOG = LoggerFactory.getLogger(IndexController.class);

    public IndexController() {}

    @GetMapping("/")
    public String index(HttpServletRequest request, @CurrentUser UserDetails currentUser, Model model) {
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        UserState userState = new UserState();

        if(currentUser != null) {
            if(currentUser instanceof User) {
                userState.setUserId(((User) currentUser).getUserId());
                userState.setName(currentUser.getUsername());
            } else {
                userState.setUserId(UUID.randomUUID().toString());
                userState.setName(currentUser.getUsername());
            }
        }

        userState.setSessionId(sessionId);

        model.addAttribute("state", userState);
        model.addAttribute("entryJS", "/dist/index.js");
        return "index";
    }

    @GetMapping("/console")
    public String console(HttpServletRequest request, Model model) {
        model.addAttribute("entryJS", "/dist/console.js");
        return "index";
    }
}
