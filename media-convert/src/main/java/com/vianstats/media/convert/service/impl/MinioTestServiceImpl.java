package com.vianstats.media.convert.service.impl;

import com.vianstats.media.convert.service.MinioTestService;
import com.vianstats.media.convert.utils.MinioUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
@Service
public class MinioTestServiceImpl implements MinioTestService {

    @Override
    public String putObject(MultipartFile file) {
        //String s = MinioUtils.uploadImageFile(file, MinioPropertiesUtils.BUCKET_NAME, MinioPropertiesUtils.USER_AVATAR_URL);
        //MinioUploadUtils.uploadOSSFile(file, "apptest", "/user/avatar/asda.jpg");
        return "上传成功";
    }
}
