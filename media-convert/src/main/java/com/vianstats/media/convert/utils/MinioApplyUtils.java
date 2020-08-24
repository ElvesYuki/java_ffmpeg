package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.advice.VianException;
import com.vianstats.media.convert.dto.ImgMetadataDTO;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author LuoHuan
 * @date 2020/7/6
 */
@Component
public class MinioApplyUtils {


    /**
     * 上传OSS文件通用方法
     * @param file
     * @return
     */
    public static String uploadSeminarMsgFile(MultipartFile file) {

        //1M字节为单位,头像文件大小
        long PDFMaxSize = 50*1024*1024;

        //判断文件大小
        if (file.getSize() > PDFMaxSize){
            throw new VianException("请上传小于50M的文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || "".equals(originalFilename)){
            throw new VianException("文件名不合法");
        }

        //获取后缀名和文件类型
        String PDFFileSuffix = MinioUtils.getFileSuffix(originalFilename);
        String PDFFileContentType = file.getContentType();

        //判断文件类型,后缀为pdf或者PDF
        /*String pdf = "pdf";
        String PDF = "PDF";
        List<String> PDFFileType = new LinkedList<>();
        PDFFileType.add(pdf);
        PDFFileType.add(PDF);
        //判断文件后缀不合法
        if (PDFFileType.stream().noneMatch(string -> string.equals(PDFFileSuffix))){
            throw new VianException("上传文件后缀名不合法");
        }*/

        //校验文件contentType
        /*String pdfContentType = "application/pdf";
        if (!pdfContentType.equals(PDFFileContentType)){
            throw new VianException("上传文件格式不合法");
        }*/

        //获取bucketName
        String bucketName = MinioPropertiesUtils.BUCKET_NAME;
        //获取用户头像储存地址
        String seminarFileUrl = MinioPropertiesUtils.SEMINAR_MSG;

        //获取存储路径
        String uploadUrl = MinioUtils.generateFileUrl(file, seminarFileUrl);

        //调用客户端上传
        MinioUploadUtils.uploadOSSFile(file,bucketName,uploadUrl);

        return MinioUtils.getBucketNameFileUrl(bucketName, uploadUrl);
    }

    /**
     * 上传OSS文件通用方法
     * @param file
     * @return
     */
    public static String uploadOSSGeneralFile(MultipartFile file) {

        //1M字节为单位,头像文件大小
        long PDFMaxSize = 5*1024*1024*1024;

        //判断文件大小
        if (file.getSize() > PDFMaxSize){
            throw new VianException("请上传小于5000M的文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || "".equals(originalFilename)){
            throw new VianException("文件名不合法");
        }

        //获取后缀名和文件类型
        String PDFFileSuffix = MinioUtils.getFileSuffix(originalFilename);
        String PDFFileContentType = file.getContentType();

        //判断文件类型,后缀为pdf或者PDF
        /*String pdf = "pdf";
        String PDF = "PDF";
        List<String> PDFFileType = new LinkedList<>();
        PDFFileType.add(pdf);
        PDFFileType.add(PDF);
        //判断文件后缀不合法
        if (PDFFileType.stream().noneMatch(string -> string.equals(PDFFileSuffix))){
            throw new VianException("上传文件后缀名不合法");
        }*/

        //校验文件contentType
        /*String pdfContentType = "application/pdf";
        if (!pdfContentType.equals(PDFFileContentType)){
            throw new VianException("上传文件格式不合法");
        }*/

        //获取bucketName
        String bucketName = MinioPropertiesUtils.BUCKET_NAME;
        //获取用户头像储存地址
        String seminarFileUrl = "test/video/convert/";

        //获取存储路径
        String uploadUrl = MinioUtils.generateFileUrl(file, seminarFileUrl);

        //调用客户端上传
        MinioUploadUtils.uploadOSSFile(file,bucketName,uploadUrl);

        return MinioUtils.getBucketNameFileUrl(bucketName, uploadUrl);
    }

    /**
     * 上传OSS文件通用方法
     * @param file
     * @return
     */
    public static String uploadOSSTempFile(MultipartFile file) {

        //1M字节为单位,头像文件大小
        long PDFMaxSize = 5*1024*1024*1024;

        //判断文件大小
        if (file.getSize() > PDFMaxSize){
            throw new VianException("请上传小于5000M的文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || "".equals(originalFilename)){
            throw new VianException("文件名不合法");
        }

        //获取后缀名和文件类型
        String PDFFileSuffix = MinioUtils.getFileSuffix(originalFilename);
        String PDFFileContentType = file.getContentType();

        //判断文件类型,后缀为pdf或者PDF
        /*String pdf = "pdf";
        String PDF = "PDF";
        List<String> PDFFileType = new LinkedList<>();
        PDFFileType.add(pdf);
        PDFFileType.add(PDF);
        //判断文件后缀不合法
        if (PDFFileType.stream().noneMatch(string -> string.equals(PDFFileSuffix))){
            throw new VianException("上传文件后缀名不合法");
        }*/

        //校验文件contentType
        /*String pdfContentType = "application/pdf";
        if (!pdfContentType.equals(PDFFileContentType)){
            throw new VianException("上传文件格式不合法");
        }*/

        //获取bucketName
        String bucketName = MinioPropertiesUtils.BUCKET_NAME;
        //获取用户头像储存地址
        String seminarFileUrl = MinioPropertiesUtils.SYSTEM_TEMP;

        //获取存储路径
        String uploadUrl = MinioUtils.generateFileUrl(file, seminarFileUrl);

        //调用客户端上传
        MinioUploadUtils.uploadOSSFile(file,bucketName,uploadUrl);

        return MinioUtils.getBucketNameFileUrl(bucketName, uploadUrl);
    }

}
