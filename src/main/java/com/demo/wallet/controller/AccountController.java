package com.demo.wallet.controller;

import com.demo.wallet.controller.message.RegisterReq;
import com.demo.wallet.controller.message.RegisterResp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AccountController {

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public RegisterResp registerAccount(@RequestBody RegisterReq req) {
        return null;
    }
}
