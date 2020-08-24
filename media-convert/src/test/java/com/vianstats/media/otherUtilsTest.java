package com.vianstats.media;

import com.vianstats.media.convert.utils.ConvertVideoUtils;
import com.vianstats.media.convert.utils.DiskFileItemUtils;
import com.vianstats.media.convert.utils.FFmpegCommandUtils;
import com.vianstats.media.convert.utils.SpliceVideoUtils;
import com.vianstats.media.convert.utils.other.OtherMediaUtil;
import com.vianstats.media.convert.utils.other.enums.PresetVauleEnum;
import io.minio.MinioClient;
import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ws.schild.jave.FFMPEGExecutor;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author LuoHuan
 * @date 2020/7/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {otherUtilsTest.class})
public class otherUtilsTest {

    @Test
    public void isExecutableTest(){
        FFmpegCommandUtils.isExecutable();
    }

    /**
     * 视频转换
     *
     * 注意指定视频分辨率时，宽度和高度必须同时有值；
     *
     * fileInput 源视频路径
     * fileOutPut 转换后的视频输出路径
     * withAudio 是否保留音频；true-保留，false-不保留
     * crf 指定视频的质量系数（值越小，视频质量越高，体积越大；该系数取值为0-51，直接影响视频码率大小）,取值参考：CrfValueEnum.code
     * preset 指定视频的编码速率（速率越快压缩率越低），取值参考：PresetVauleEnum.presetValue
     * width 视频宽度；为空则保持源视频宽度
     * height 视频高度；为空则保持源视频高度
     */
    @Test
    public void convertVideoTest(){

        //File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\暮光之城测试片段.mp4");
        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\测试合并视频\\vianDou01.mp4");
        File fileOutPut = new File("C:\\vian\\本地测试\\测试视频文件\\暮光之城测试片段0110.ts");
        boolean withAudio = true;
        Integer crf = 31;
        String preset = PresetVauleEnum.MEDIUM_ZIP_SPEED.getPresetValue();
        Integer width = null;
        Integer height = null;
        //OtherMediaUtil.convertVideo(fileInput, fileOutPut, withAudio, crf, preset, null, null);
        ConvertVideoUtils.convertVideo(fileInput, fileOutPut, withAudio, crf, preset, null, null);
    }

    @Test
    public void convertVideoTest01(){

        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\测试合并视频\\vianDou01.mp4");
        File fileOutput = new File("D:\\FFOutput\\ceshi001.ts");

        SpliceVideoUtils.convertVideoToTs("http://192.168.0.104:9000/apptest/test/video/convert/4bfec37800de4d2e8eeae511bbc4c9e0.mp4", fileOutput);
        //SpliceVideoUtils.convertVideoToTs(fileInput, fileOutput);

    }

    @Test
    public void getVideoMetaInfoTest(){
        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\vianDou01.mp4");
        OtherMediaUtil.getVideoMetaInfo(fileInput);
    }

    @Test
    public void getMetaInfoFromFFmpegTest(){
        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\暮光之城测试片段720P.mp4");
        String metaInfoFromFFmpeg = OtherMediaUtil.getMetaInfoFromFFmpeg(fileInput);
        System.out.println("---------------------------------------");
        System.out.println(metaInfoFromFFmpeg);
        System.out.println("---------------------------------------");
    }

    /*@Resource
    private MinioClient minioClientBean;*/

    @Test
    public void Test01(){
        File mp4TempFile = DiskFileItemUtils.createMp4TempFile();
        System.out.println("---------------------------------------");
        System.out.println(mp4TempFile);
        System.out.println("---------------------------------------");
    }


}
