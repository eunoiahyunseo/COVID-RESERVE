package com.hyunseo.covidreserve.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 5:55 PM
 */

@Controller
public class BaseController implements ErrorController {
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
