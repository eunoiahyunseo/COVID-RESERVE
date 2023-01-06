package com.hyunseo.covidreserve.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 6:46 PM
 */
@RequestMapping("/api")
@RestController
public class APIAuthController {
    @GetMapping("/sign-up")
    public String signUp() {
        return "done.";
    }
    @GetMapping("/login")
    public String login() {
        return "done.";
    }
}
