package top.jayu.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Code;
import top.jayu.oa.mapper.CodeMapper;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    CodeMapper codeMapper;

    @GetMapping("/list")
    public List<Code> list(){
        return codeMapper.list();
    };

}
