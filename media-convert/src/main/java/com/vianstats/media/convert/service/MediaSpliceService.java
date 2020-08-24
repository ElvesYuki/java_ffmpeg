package com.vianstats.media.convert.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
public interface MediaSpliceService {

    /**
     * 上传待转换的视频
     * @param file
     * @return
     */
    String addVideoSpliceSource(MultipartFile file);

    /**
     *
     * @param file
     * @return
     */
    String addVideoSplice(MultipartFile file);
}
