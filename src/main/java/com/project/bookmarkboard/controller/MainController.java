package com.project.bookmarkboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.FolderDTO;
import com.project.bookmarkboard.service.MainService;
import com.project.bookmarkboard.service.SearchService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("")
public class MainController {
	
	@Autowired
	SearchService sservice;
	@Autowired
	MainService mservice;
	
    @GetMapping({"/"})
    public String getMain(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
    	List<FolderDTO> list =  mservice.getAllFromMyFolderis_stared(customUserDetails.getUserInternalId());
    	model.addAttribute("is_stared", list); // 모델에 저장
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
    public String search(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
    	// List<FolderDTO> list2 = sservice.getAllFromMyFolder(searchkeyword, owner);
   	 	// model.addAttribute("myFolder", list2); // 모델에 저장
 		// List<FolderDTO> list3 = sservice.getAllFromOurFolder(searchkeyword);
	 	// model.addAttribute("ourFolder", list3); // 모델에 저장
	 	
    	// model.addAttribute("myFolder", searchMapper.getAllFromMyFolder());
    	// model.addAttribute("ourFolder", searchMapper.getAllFromOurFolder());
		
        return "search";
    }
}
