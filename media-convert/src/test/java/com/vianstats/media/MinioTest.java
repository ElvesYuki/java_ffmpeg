package com.vianstats.media;

import com.vianstats.media.convert.utils.MinioUploadUtils;
import com.vianstats.media.convert.utils.other.OtherMediaUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author LuoHuan
 * @date 2020/8/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MinioTest.class})
public class MinioTest {

    /*@Resource
    private MinioClient minioClientBean;*/

    @Test
    public  void upload() throws NoSuchAlgorithmException, IOException, InvalidKeyException {


        MinioClient minioClient = null;

        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            //minioClient = new MinioClient("https://io.vianstats.net","DoPsWnYfyq2qNV", "5Bn99c8Z8eLpACuL/vmg195LiqMll/HNGBgRG3rnOw",false);
            //minioClient = new MinioClient("http://39.105.45.130",8650,"AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY",false);
            //minioClient = new MinioClient("http://192.168.0.121",8660,"AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY",false);
            minioClient = new MinioClient("http://192.168.0.104",9000,"VIANGZ2020", "VIANGZ2020",false);

            /*minioClient = MinioClient.builder()
                    .endpoint("http://39.105.45.130",8650,false)
                    .credentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY")
                    .build();*/
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket("weapp")
                    .build());
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("weapp").build());
            }

            //File file = new File("C:\\vian\\本地测试\\测试图片文件\\课程海报01.png");
            File file = new File("C:\\vian\\本地测试\\测试图片文件\\壁纸.jpg");
            FileInputStream fileInputStream = new FileInputStream(file);

            /*minioClientBean.putObject("apptest",
                    "course/cover/课程海报01.png",
                    "C:\\vian\\本地测试\\测试图片文件\\壁纸.jpg");*/
            minioClient.putObject(
                    PutObjectArgs.builder()
                    .bucket("weapp")
                    .object("course/cover/课程海壁纸.jpg")
                    .stream(fileInputStream,fileInputStream.available(),-1)
                    .contentType("image/jpg")
                    .build());

            // 使用putObject上传一个文件到存储桶中。
            /*minioClient.putObject("apptest","course/cover/课程海报01.png", "C:\vian\本地测试\测试图片文件\课程海报01.png");
            minioClient.putObject("apptest","course/cover/课程海报02.png", "C:\\vian\\本地测试\\课程海报02.png");
            minioClient.putObject("apptest","course/imgUrl/课程头像01.png", "C:\\vian\\本地测试\\课程头像01.png");
            minioClient.putObject("apptest","course/imgUrl/课程头像02.png", "C:\\vian\\本地测试\\课程头像02.png");
            minioClient.putObject("apptest","course/imgUrl/课程头像03.png", "C:\\vian\\本地测试\\课程头像03.png");
            minioClient.putObject("apptest","course/video/vianDou01.mp4", "C:\\vian\\本地测试\\vianDou01.mp4");
            minioClient.putObject("apptest","course/video/vianDou02.mp4", "C:\\vian\\本地测试\\vianDou02.mp4");
            minioClient.putObject("apptest","course/video/vianDou03.mp4", "C:\\vian\\本地测试\\vianDou03.mp4");
            minioClient.putObject("apptest","teacher/avatar/老师头像01.png", "C:\\vian\\本地测试\\老师头像01.png");
            minioClient.putObject("apptest","teacher/avatar/老师头像02.png", "C:\\vian\\本地测试\\老师头像02.png");*/

            System.out.println("成功");
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

    public MinioClient createMinioClient() {
        return MinioClient.builder()
                .endpoint("http://192.168.0.104", 9000, false)
                .credentials("VIANGZ2020", "VIANGZ2020")
                .build();
    }


    @Test
    public void testBucketExists() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        long startTime = System.currentTimeMillis();

        MinioClient minioClient = createMinioClient();

        long startTime1 = System.currentTimeMillis();

        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("apptest").build());

        //获取结束时间
        long endTime = System.currentTimeMillis();

        //输出程序运行时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        System.out.println("程序运行时间：" + (endTime - startTime1) + "ms");

        System.out.println("============================");
        System.out.println(found);
        System.out.println("============================");
    }

    @Test
    public void testListBuckets() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        MinioClient minioClient = createMinioClient();
        List<Bucket> bucketList = minioClient.listBuckets();
        System.out.println("============================");
        for (Bucket bucket : bucketList) {
            System.out.println(bucket.creationDate() + ", " + bucket.name());
        }
        System.out.println("============================");
    }

    @Test
    public void testGetObjectUrl() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        MinioClient minioClient = createMinioClient();
        String url = minioClient.getObjectUrl("apptest", "/seminar/msg/b015fcc446f04059b731bfc6d80c286b.mp4");
        System.out.println("============================");
            System.out.println(url);
        System.out.println("============================");
    }

    @Test
    public void testPutObject() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        MinioClient minioClient = createMinioClient();

        File file = new File("C:\\vian\\本地测试\\测试图片文件\\壁纸.jpg");
        FileInputStream inputStream = new FileInputStream(file);

        String contentType = "image/jpg";
        //String contentType = "video/mp4";

        ObjectWriteResponse apptest = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("apptest")
                        .object("test/test.jpg")
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build()
        );
        System.out.println("============================");
        System.out.println(apptest.toString());
        System.out.println("============================");
    }

}
