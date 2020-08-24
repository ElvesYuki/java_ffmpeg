package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.advice.VianException;
import com.vianstats.media.convert.dto.ImgMetadataDTO;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author LuoHuan
 * @date 2020/5/25
 */
@Component
public class MinioUtils {

    private static final Logger logger = LoggerFactory.getLogger(MinioUtils.class);

    public static MinioClient minioClient;

    @Resource
    private MinioClient minioClientBean;

    /**
     * 获取Minio客户端方法
     *
     * @return
     */
    @PostConstruct
    public void getMinioClient() {
        MinioUtils.minioClient = minioClientBean;
    }

    /**
     * 获取唯一文件名
     *
     * @return 生成的文件名（不包含后缀）
     */
    public static String generateUUID() {

        char[] toUuid = new char[32];
        char[] uuid = UUID.randomUUID().toString().toCharArray();
        System.arraycopy(uuid, 0, toUuid, 0, 8);
        System.arraycopy(uuid, 9, toUuid, 8, 4);
        System.arraycopy(uuid, 14, toUuid, 12, 4);
        System.arraycopy(uuid, 19, toUuid, 16, 4);
        System.arraycopy(uuid, 24, toUuid, 20, 12);
        return new String(toUuid);
    }

    /**
     * 获取唯一文件名
     *
     * @return 生成的文件名（不包含后缀）
     */
    @Deprecated
    public static String getImageFileName() {

        return MinioUtils.generateUUID();
    }

    /**
     * 获取文件后缀
     *
     * @return 后缀
     */
    public static String getFileSuffix(String originalFilename) {

        if ("".equals(originalFilename)) {
            throw new VianException("文件名不能为空");
        }

        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

    }

    /**
     * 生成文件存储路径(不包含bucketName)
     * 主要做：fileUrl+UUID+后缀
     * file = /00/00/
     * uuid = uuid
     * 后缀 = .mp4
     * <p>
     * 返回  /00/00/uuid.mp4
     *
     * @return 生成的文件存储路径(不包含bucketName)
     */
    public static String generateFileUrl(MultipartFile multipartFile, String fileUrl) {

        StringBuilder stringBuilder = new StringBuilder();

        //根据文件获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
        if (null == originalFilename) {
            throw new VianException("获取文件名出错");
        }
        //拼接文件路径
        stringBuilder.append(fileUrl);
        //拼接文件名
        stringBuilder.append(MinioUtils.generateUUID());
        //拼接文件后缀
        stringBuilder.append(".");
        stringBuilder.append(MinioUtils.getFileSuffix(originalFilename));

        return stringBuilder.toString();
    }

    /**
     * 生成文件存储路径(不包含bucketName)
     * 主要做：fileUrl+UUID+后缀
     * file = 00/00/
     * uuid = uuid
     * 后缀 = .mp4
     * <p>
     * 返回  /00/00/uuid.mp4
     *
     * @return 生成的文件存储路径(不包含bucketName)
     */
    public static String generateFileUrl(String fileSuffix, String fileUrl) {

        StringBuilder stringBuilder = new StringBuilder();
        //拼接文件路径
        stringBuilder.append(fileUrl);
        //拼接文件名
        stringBuilder.append(MinioUtils.generateUUID());
        //拼接文件后缀
        stringBuilder.append(fileSuffix);

        return stringBuilder.toString();
    }

    /**
     * 根据文件的真实路径分割路径参数
     *
     * @param fileUrl
     * @return
     *
     * HashMap<String, String>
     * bucketName
     * fileUrl
     * fileName
     */
    public static Map<String, String> getFileUrlSuffixBucketName(String fileUrl) {

        String[] fileUrlSplit = fileUrl.split(Pattern.quote("/"));

        HashMap<String, String> stringStringHashMap = new HashMap<>(4);

        //bucketName: bucketName
        stringStringHashMap.put("bucketName", fileUrlSplit[1]);

        //拼接中间的路径名：/路径/路径/
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("/");
        for (int i = 2; i < fileUrlSplit.length - 1; i++) {
            stringBuilder
                    .append(fileUrlSplit[i])
                    .append("/");
        }
        stringStringHashMap.put("fileUrl", stringBuilder.toString());

        //fileName：名字.后缀
        stringStringHashMap.put("fileName", fileUrlSplit[fileUrlSplit.length - 1]);

        return stringStringHashMap;

    }

    /**
     * 生成文件存储路径(包含bucketName)
     * bucketName 111
     * fileUrl 00/00/uuid.mp4
     *
     * @return 生成的文件存储路径(不包含bucketName)  /111/00/00/uuid.mp4
     */
    public static String getBucketNameFileUrl(String bucketName, String fileUrl) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("/")
                .append(bucketName)
                .append("/")
                .append(fileUrl);

        return stringBuilder.toString();
    }

    /**
     * 生成文件存储路径(包含bucketName)
     * bucketName 111
     * fileUrl 00/00/uuid.mp4
     *
     * @return 生成的文件存储路径(不包含bucketName)  /111/00/00/uuid.mp4
     */
    public static String getFileUrlWithHttp(String fileUrl) {

        Map<String, String> fileUrlSuffixBucketName = MinioUtils.getFileUrlSuffixBucketName(fileUrl);
        String bucketName = fileUrlSuffixBucketName.get("bucketName");
        String fileUrlSplice = fileUrlSuffixBucketName.get("fileUrl");
        String fileName = fileUrlSuffixBucketName.get("fileName");

        String realFileUrl = fileUrlSplice + fileName;

        String objectUrl = null;
        try {
            objectUrl = MinioUtils.minioClient.getObjectUrl(bucketName, realFileUrl);
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return objectUrl;
    }

    /**
     * 获取内容字符串转输入流
     *
     * @param content 内容字符串
     * @return
     */
    public static InputStream strToInputStream(String content) {

        if (null == content || "".equals(content.trim())) {
            throw new VianException("不合法的输入内容");
        }

        String standardContent = SensitiveFilterUtils.sensitiveFilter(content);

        ByteArrayInputStream contentInputStream = new ByteArrayInputStream(standardContent.getBytes());

        return contentInputStream;

    }

    /**
     * 删除对象
     * @param fileUrl
     * @return
     */
    public static Boolean deleteOSSFile(String fileUrl) {

        Map<String, String> fileUrlSuffixBucketName = MinioUtils.getFileUrlSuffixBucketName(fileUrl);
        String bucketName = fileUrlSuffixBucketName.get("bucketName");
        String fileUrlSplice = fileUrlSuffixBucketName.get("fileUrl");
        String fileName = fileUrlSuffixBucketName.get("fileName");

        String realFileUrl = fileUrlSplice + fileName;

        try {
            MinioUtils.minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(realFileUrl)
                    .build());
            logger.info("删除文件:"+fileUrl);
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return true;
    }




}
