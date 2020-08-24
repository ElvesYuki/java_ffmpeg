package com.vianstats.media.convert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author LuoHuan
 * @date 2020/7/16
 */
@EnableSwagger2
@MapperScan(value = {"com.vianstats.media.**.mapper"})
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class MediaConvertApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaConvertApplication.class);
    }
}
