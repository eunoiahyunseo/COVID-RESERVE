package com.hyunseo.covidreserve.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 5:55 PM
 */

@Controller
public class BaseController {
    @GetMapping("/")
    public String root() throws Exception {
        throw new Exception("메소드");
//        return "index";
    }
//
//    @GetMapping("/error")
//    public String error() {
//        return "error";
//    }
}
