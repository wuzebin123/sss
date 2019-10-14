package com.baizhi.controller;

import com.baizhi.service.PoemServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("poem")
public class PoemController {

    @RequestMapping("find")
    @ResponseBody
    public List<Object> findAll(String s) throws Exception {
        System.out.println(s);
        PoemServiceImpl poemService = new PoemServiceImpl();
        List<Object> all = poemService.findAll(s);
        return all;
    }
}
