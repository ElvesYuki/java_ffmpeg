package com.vianstats.media.convert.controller;

import com.vianstats.media.convert.service.MediaSpliceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
@Api(tags = "视频拼接接口")
@RestController
@RequestMapping("media")
public class MediaSpliceController {

    @Resource
    private MediaSpliceService mediaSpliceService;

    @ApiOperation("上传待拼接的视频")
    @PostMapping("/video/addVideoSpliceSource")
    public String addVideoSpliceSource(@RequestParam("file") MultipartFile file) {

        String url = mediaSpliceService.addVideoSpliceSource(file);

        return url;
    }

    @ApiOperation("上传视频拼接返回")
    @PostMapping("/video/addVideoSplice")
    public String addVideoSplice(@RequestParam("file") MultipartFile file) {

        String url = mediaSpliceService.addVideoSplice(file);

        return url;
    }


}
