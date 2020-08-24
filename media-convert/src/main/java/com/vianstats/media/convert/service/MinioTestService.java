package com.vianstats.media.convert.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
public interface MinioTestService {

    /**
     *
     * @param file
     * @return
     */
    String putObject(MultipartFile file);
}
