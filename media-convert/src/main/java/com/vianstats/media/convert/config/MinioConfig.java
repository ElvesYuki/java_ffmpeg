package com.vianstats.media.convert.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author LuoHuan
 * @date 2020/8/12
 */
@Configuration
public class MinioConfig {

    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${Minio.endPoint}")
    private String endPoint;

    @Value("${Minio.port}")
    private Integer port;

    @Value("${Minio.accessKey}")
    private String accessKey;

    @Value("${Minio.secretKey}")
    private String secretKey;


    /**
     * minio客户端
     * @return
     */
    @Bean(name = "minioClientBean")
    public MinioClient minioClient() {

        //MinioClient minioClient = new MinioClient(endPoint, port,accessKey,secretKey ,false);
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endPoint, port, false)
                .credentials(accessKey, secretKey)
                .build();

        return minioClient;
    }

    /*@Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10*1024*1024L);
        return commonsMultipartResolver;
    }*/

    /**
     * 显示声明CommonsMultipartResolver为multipartResolver
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(50*1024*1024);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize(1024*1024*1024);
        return resolver;
    }
}
