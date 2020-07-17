package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.utils.other.OtherMediaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Time;

/**
 * @author LuoHuan
 * @date 2020/7/17
 */
@Component
public class ConvertVideoUtils {

    private static final Logger log = LoggerFactory.getLogger(ConvertVideoUtils.class);

    /**
     * 可以处理的视频格式
     */
    public final static String[] VIDEO_TYPE = { "MP4", "WMV" };
    /**
     * 可以处理的图片格式
     */
    public final static String[] IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF" };
    /**
     * 可以处理的音频格式
     */
    public final static String[] AUDIO_TYPE = { "AAC" };
    /**
     * 视频帧抽取时的默认时间点，第10s（秒）
     * （Time类构造参数的单位:ms）
     */
    private static final Time DEFAULT_TIME = new Time(0, 0, 10);
    /**
     * 视频帧抽取的默认宽度值，单位：px
     */
    private static int DEFAULT_WIDTH = 320;
    /**
     * 视频帧抽取的默认时长，单位：s（秒）
     */
    private static int DEFAULT_TIME_LENGTH = 10;
    /**
     * 抽取多张视频帧以合成gif动图时，gif的播放速度
     */
    private static int DEFAULT_GIF_PLAYTIME = 110;
    /**
     * FFmpeg程序执行路径
     * 当前系统安装好ffmpeg程序并配置好相应的环境变量后，值为ffmpeg可执行程序文件在实际系统中的绝对路径
     */
    private static String FFMPEG_PATH = "C:\\MyProgram\\ztestProgram\\ffmpeg-4.3-win64-static\\bin\\ffmpeg.exe" +
            "" +
            ""; // /usr/bin/ffmpeg


}
