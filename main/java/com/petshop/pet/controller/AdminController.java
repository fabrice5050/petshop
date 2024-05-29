package com.petshop.pet.controller;

import com.petshop.pet.model.CheckOut;
import com.petshop.pet.services.checkOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private checkOutService checkoutService;

    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String adminHome() {
        return "admin/dashboard";
    }

    @GetMapping("/checkouts")
    public String getCheckouts(Model model) {
        List<CheckOut> checkouts = checkoutService.listofcheckout();
        model.addAttribute("checkouts", checkouts);
        return "checked_out";
    }
}
