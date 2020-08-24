package com.vianstats.media.convert.service.impl;

import com.vianstats.media.convert.service.MediaSpliceService;
import com.vianstats.media.convert.utils.*;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
@Service
public class MediaSpliceServiceImpl implements MediaSpliceService {

    /*@Resource
    private MinioClient minioClientBean;*/

    @Override
    public String addVideoSpliceSource(MultipartFile file) {

        String OSSGeneralFileUrl = MinioApplyUtils.uploadOSSGeneralFile(file);

        //拼接文件服务器链接
        StringBuilder inputFileUrl = new StringBuilder();
        inputFileUrl
                .append("http://")
                .append(MinioPropertiesUtils.END_POINT)
                .append(":")
                .append(MinioPropertiesUtils.PORT)
                .append(OSSGeneralFileUrl);

        File outputFile = DiskFileItemUtils.createTSTempFile();

        SpliceVideoUtils.convertVideoToTs(inputFileUrl.toString(),outputFile);

        return OSSGeneralFileUrl;

    }


    @Override
    public String addVideoSplice(MultipartFile file) {

        String convertAndUploadVideoSplice = MinioSpliceVideoApplyUtils.convertAndUploadVideoSplice(file);

        return convertAndUploadVideoSplice;

    }


}
