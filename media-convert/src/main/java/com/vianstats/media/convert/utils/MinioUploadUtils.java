package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.advice.VianException;
import com.vianstats.media.convert.dto.ImgMetadataDTO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
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
 * @date 2020/8/12
 */
@Component
public class MinioUploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(MinioUploadUtils.class);

    public static MinioClient minioClient;

    @Resource
    private MinioClient minioClientBean;

    /**
     * 获取Minio客户端方法
     *
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    @PostConstruct
    public void getMinioClient() {

        MinioUploadUtils.minioClient = minioClientBean;
    }


    /**
     * 上传图片文件方法
     *
     * @param multipartFile 文件
     * @param bucketName    根目录名称 apptest
     * @param fileUrl       存入文件的路径
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadImageFile(MultipartFile multipartFile, String bucketName, String fileUrl) {

        //根据文件获取输入流
        InputStream inputStream = null;

        try {

            //根据文件获取输入流
            inputStream = multipartFile.getInputStream();

            //根据文件获取文件类型
            String contentType = multipartFile.getContentType();

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }

    /**
     * 上传视频文件方法
     *
     * @param multipartFile 文件
     * @param bucketName    根目录名称 apptest
     * @param fileUrl       存入文件的路径
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadVideoFile(MultipartFile multipartFile, String bucketName, String fileUrl) {


        //根据文件获取输入流
        InputStream inputStream = null;
        try {

            //根据文件获取输入流
            inputStream = multipartFile.getInputStream();

            //根据文件获取文件类型
            String contentType = multipartFile.getContentType();

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }

    /**
     * 上传音频文件方法
     *
     * @param multipartFile 文件
     * @param bucketName    根目录名称 apptest
     * @param fileUrl       存入文件的路径
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadVoiceFile(MultipartFile multipartFile, String bucketName, String fileUrl) {

        //根据文件获取输入流
        InputStream inputStream = null;
        try {

            //根据文件获取输入流
            inputStream = multipartFile.getInputStream();

            //根据文件获取文件类型
            String contentType = multipartFile.getContentType();

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }



    /**
     * 上传内容文件方法（update使用这个方法并把cover设置为true）
     *
     * @param content    文件内容
     * @param bucketName 根目录名称 apptest
     * @param fileUrl    存入文件的路径
     * 是否覆盖，true表示不生成新文件名(fileUrl应该校验正确)，false表示重新生成命名上传文件
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadHtmlFile(String content, String bucketName, String fileUrl) {


        InputStream inputStream = null;

        try {

            inputStream = MinioUtils.strToInputStream(content);

            //设置文件类型
            String contentType = "text/html";

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);


        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }

    /**
     * 上传PDF文件方法
     *
     * @param multipartFile 文件
     * @param bucketName    根目录名称 apptest
     * @param fileUrl       存入文件的路径
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadPDFFile(MultipartFile multipartFile, String bucketName, String fileUrl) {


        //根据文件获取输入流
        InputStream inputStream = null;
        try {

            //根据文件获取输入流
            inputStream = multipartFile.getInputStream();

            //根据文件获取文件类型
            String contentType = multipartFile.getContentType();

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }


    /**
     * 上传OSS文件通用方法
     *
     * @param multipartFile 文件
     * @param bucketName    根目录名称 apptest
     * @param fileUrl       存入文件的路径
     * @return 返回的是储存路径，需要拼接 ip+端口 访问
     */
    public static void uploadOSSFile(MultipartFile multipartFile, String bucketName, String fileUrl) {


        //根据文件获取输入流
        InputStream inputStream = null;
        try {

            //根据文件获取输入流
            inputStream = multipartFile.getInputStream();

            //根据文件获取文件类型
            String contentType = multipartFile.getContentType();

            //MinioUploadUtils.minioClient.putObject(bucketName, fileUrl, inputStream, inputStream.available(), contentType);
            MinioUploadUtils.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUrl)
                            .stream(inputStream ,inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("储存路径----" + "/" + bucketName +"/"+ fileUrl);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                logger.error("Minio方法InputStream资源释放错误" + e.getMessage());
            }
        }
    }

}
