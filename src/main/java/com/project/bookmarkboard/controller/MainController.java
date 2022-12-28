package com.project.bookmarkboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bookmarkboard.mapper.MainMapper;
import com.project.bookmarkboard.mapper.SearchMapper;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("")
public class MainController {
	
	@Autowired
	SearchMapper searchMapper;
	MainMapper mainMapper;
	
    @GetMapping({"/"})
    public String getMain(Model model) {
    	model.addAttribute("is_stared", mainMapper.getAllFromMyFolderis_stared());
    	// model.addAttribute("recommended", mainMapper.getAllFromOurFolderis_recommended());
    	
        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "fail", required = false) boolean isFail) {
        model.addAttribute("fail", isFail);

        return "login";
    }

    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    
    @GetMapping({"/search"})
    public String search(Model model) {
    	model.addAttribute("myFolder", searchMapper.getAllFromMyFolder());
    	model.addAttribute("ourFolder", searchMapper.getAllFromOurFolder());

		
        return "search";
    }
}
