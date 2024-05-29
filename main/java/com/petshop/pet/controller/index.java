package com.petshop.pet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class index {
    @GetMapping("/v")
    public String Home() {

        return "index";
    }
}
