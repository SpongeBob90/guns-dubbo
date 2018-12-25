package com.stylefeng.guns.gateway.modular.example;

import com.stylefeng.guns.gateway.common.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : wyw 2018-12-25 14:24
 **/
@Controller
@RequestMapping("hello")
public class ExampleController {

    @RequestMapping("")
    public ResponseEntity hello(){
        System.out.println(CurrentUser.getUserId());
        return ResponseEntity.ok("请求成功");
    }

}
