package com.hyunseo.covidreserve.controller.api;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 6:46 PM
 */

/**
 * Spring Data REST 로 API를 만들어서 당장 필요가 없어진 컨트롤러.
 * 우선 deprecated 하고, 향후 사용 방안을 고민해 본다.
 * 필요에 따라서는 다시 살릴 수도 있음
 *
 * @deprecated 0.1.2
 */

@Deprecated
//@RequestMapping("/api")
//@RestController
public class ApiAuthController {
    @GetMapping("/sign-up")
    public String signUp() {
        return "done.";
    }
    @GetMapping("/login")
    public String login() {
        return "done.";
    }
}
