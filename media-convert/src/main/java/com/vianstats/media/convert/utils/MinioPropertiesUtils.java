package com.vianstats.media.convert.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LuoHuan
 * @date 2020/5/25
 */
@Component
public class MinioPropertiesUtils {

    @Value("${Minio.endPoint}")
    private String endPoint;

    @Value("${Minio.port}")
    private Integer port;

    @Value("${Minio.uploadUrl.BucketName}")
    private String bucketName;

    @Value("${Minio.uploadUrl.System.temp}")
    private String systemTemp;

    @Value("${Minio.uploadUrl.userAvatarUrl}")
    private String userAvatarUrl;

    @Value("${Minio.uploadUrl.Seminar.seminarImg}")
    private String seminarImg;

    @Value("${Minio.uploadUrl.Seminar.seminarVoice}")
    private String seminarVoice;

    @Value("${Minio.uploadUrl.Seminar.seminarFile}")
    private String seminarFile;

    @Value("${Minio.uploadUrl.Seminar.seminarText}")
    private String seminarText;

    @Value("${Minio.uploadUrl.Seminar.seminarMsg}")
    private String seminarMsg;

    public static String END_POINT;
    public static Integer PORT;

    public static String BUCKET_NAME;
    public static String USER_AVATAR_URL;

    public static String SYSTEM_TEMP;


    public static String SEMINAR_IMG;
    public static List<String> seminarImgType;
    public static String SEMINAR_VOICE;

    public static String SEMINAR_FILE;
    public static String SEMINAR_TEXT;
    public static String SEMINAR_MSG;


    public static List<String> avatarFileType;

    @PostConstruct
    public void initPropertiesSet() {

        END_POINT = endPoint;
        PORT = port;

        BUCKET_NAME = bucketName;
        USER_AVATAR_URL = userAvatarUrl;

        SYSTEM_TEMP = systemTemp;

        SEMINAR_IMG = seminarImg;
        SEMINAR_VOICE = seminarVoice;
        SEMINAR_FILE = seminarFile;
        SEMINAR_TEXT = seminarText;
        SEMINAR_MSG = seminarMsg;

        //设置图像文件属性
        setAvatarFile();
        setSeminarImgType();

    }

    /**
     * 研讨会图片文件格式
     */
    public void setSeminarImgType(){
        seminarImgType = new ArrayList<>();
        seminarImgType.add("jpg");
        seminarImgType.add("image/jpg");
        seminarImgType.add("jpeg");
        seminarImgType.add("image/jpeg");
        seminarImgType.add("png");
        seminarImgType.add("image/png");
    }

    public void setAvatarFile(){
        avatarFileType = new ArrayList<>();
        avatarFileType.add("jpg");
        avatarFileType.add("image/jpg");
        avatarFileType.add("jpeg");
        avatarFileType.add("image/jpeg");
        avatarFileType.add("png");
        avatarFileType.add("image/png");
    }
}
