package com.project.bookmarkboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/folder")
@Controller
public class FolderController {
    @GetMapping("")
    public String getFolderPage() {
        return "folder/list";
    }
}
