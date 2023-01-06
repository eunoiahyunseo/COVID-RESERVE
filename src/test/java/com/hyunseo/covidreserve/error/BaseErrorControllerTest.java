package com.hyunseo.covidreserve.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ihyeonseo
 */

@WebMvcTest(BaseErrorControllerTest.class)
class BaseErrorControllerTest {

    private final MockMvc mvc;

    public BaseErrorControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }
    @DisplayName("[view][GET] 에러 페이지 - 페이지 없음")
    @Test
    void givenWrongUri_whenRequestingPage_thenReturns404ErrorPage() throws Exception {
        // given

        // when && then
        mvc.perform(get("/wrong-uri"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}