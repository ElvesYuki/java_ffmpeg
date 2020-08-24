package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.advice.VianException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author LuoHuan
 * @date 2020/7/6
 */
@Component
public class MinioSpliceVideoApplyUtils {


    /**
     * 上传视频，添加文件头，并上传至文件服务器
     * @param file
     * @return
     */
    public static String convertAndUploadVideoSplice(MultipartFile file) {

        //上传临时的带拼接的minio对象
        String uploadOSSTempFile = MinioApplyUtils.uploadOSSTempFile(file);

        //拼接文件服务器链接
        /*StringBuilder inputFileUrl = new StringBuilder();
        inputFileUrl
                .append("http://")
                .append(MinioPropertiesUtils.END_POINT)
                .append(":")
                .append(MinioPropertiesUtils.PORT)
                .append(uploadOSSGeneralFile);*/

        String inputFileUrl = MinioUtils.getFileUrlWithHttp(uploadOSSTempFile);

        //生成待拼接的.ts文件
        File outputFile = DiskFileItemUtils.createTSTempFile();
        String outputFileUrl = outputFile.getAbsolutePath();

        SpliceVideoUtils.convertVideoToTs(inputFileUrl,outputFile);

        //拼接路径
        StringBuilder inputFileWithTitle = new StringBuilder();
        inputFileWithTitle
                .append("concat:")
                .append("C:\\vian\\本地测试\\测试视频文件\\测试合并视频\\temp\\10df8d96ddd4405cba3b21f45b483197.ts")
                //.append("C:\\vian\\本地测试\\测试视频文件\\测试合并视频\\temp\\0a4bab8a95554c3cb711ea18985e3312.ts")
                .append("|")
                .append(outputFileUrl);

        //生成临时Mp4文件
        File outputFileWithTitle = DiskFileItemUtils.createMp4TempFile();

        SpliceVideoUtils.spliceTsVideoToMp4(inputFileWithTitle.toString(), outputFileWithTitle);


        String bucketName = MinioPropertiesUtils.BUCKET_NAME;

        String fileUrl = "test/splice/";

        String generateFileUrl = MinioUtils.generateFileUrl(".mp4", fileUrl);

        MinioSpliceVideoUploadUtils.uploadSpliceVideoFile(outputFileWithTitle,bucketName,generateFileUrl);

        String bucketNameFileUrl = MinioUtils.getBucketNameFileUrl(bucketName, generateFileUrl);

        //删除的临时的带拼接的minio对象
        MinioUtils.deleteOSSFile(uploadOSSTempFile);
        //删除.ts临时文件
        DiskFileItemUtils.deleteTempFile(outputFile);
        //删除MP4临时
        DiskFileItemUtils.deleteTempFile(outputFileWithTitle);

        return bucketNameFileUrl;

    }


}
