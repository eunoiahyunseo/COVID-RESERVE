package com.hyunseo.covidreserve.controller;

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
        throw new Exception("테스트");
//        return "index";
    }
}
