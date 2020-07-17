package com.vianstats.media;

import com.vianstats.media.convert.utils.other.OtherMediaUtil;
import com.vianstats.media.convert.utils.other.enums.CrfValueEnum;
import com.vianstats.media.convert.utils.other.enums.PresetVauleEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        OtherMediaUtil.isExecutable();
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

        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\向左走向右走.mkv");
        File fileOutPut = new File("C:\\vian\\本地测试\\测试视频文件\\vianDou0001.mp4");
        boolean withAudio = true;
        Integer crf = 31;
        String preset = PresetVauleEnum.MEDIUM_ZIP_SPEED.getPresetValue();
        Integer width = null;
        Integer height = null;
        OtherMediaUtil.convertVideo(fileInput, fileOutPut, withAudio, crf, preset, null, null);
    }

    @Test
    public void getVideoMetaInfoTest(){
        File fileInput = new File("C:\\vian\\本地测试\\测试视频文件\\vianDou01.mp4");
        OtherMediaUtil.getVideoMetaInfo(fileInput);
    }
}
