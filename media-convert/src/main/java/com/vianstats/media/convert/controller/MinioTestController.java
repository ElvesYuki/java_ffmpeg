package com.vianstats.media.convert.controller;

import com.vianstats.media.convert.service.MinioTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
@Api(tags = "类别相关接口")
@RestController
@RequestMapping("test")
public class MinioTestController {

    @Resource
    private MinioTestService minioTestService;

    @ApiOperation("用户上传头像")
    @PostMapping("/oss/putObject")
    public String putObject(@RequestParam("file") MultipartFile file) {

        String url = minioTestService.putObject(file);

        return url;
    }

    @ApiOperation("用户上传头像测试")
    @PostMapping("/oss/putObject01")
    public String putObject01(@RequestParam("file") MultipartFile file) {

        System.out.println(file.getContentType());

        return file.getContentType();
    }
}
