package com.vianstats.media.convert.utils;

import com.vianstats.media.convert.utils.other.OtherMediaUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author LuoHuan
 * @date 2020/7/17
 */
@Component
public class SpliceVideoUtils {

    private static final Logger log = LoggerFactory.getLogger(SpliceVideoUtils.class);

    /**
     * 可以处理的视频格式
     */
    public final static String[] VIDEO_TYPE = {"MP4", "WMV"};
    /**
     * 可以处理的图片格式
     */
    public final static String[] IMAGE_TYPE = {"JPG", "JPEG", "PNG", "GIF"};
    /**
     * 可以处理的音频格式
     */
    public final static String[] AUDIO_TYPE = {"AAC"};
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
    // /usr/bin/ffmpeg
    private static String FFMPEG_PATH = "C:\\MyProgram\\ztestProgram\\ffmpeg-4.3-win64-static\\bin\\ffmpeg.exe";

    /**
     * 视频时长正则匹配式
     * 用于解析视频及音频的时长等信息时使用；
     * <p>
     * (.*?)表示：匹配任何除\r\n之外的任何0或多个字符，非贪婪模式
     */
    private static String durationRegex = "Duration: (\\d*?):(\\d*?):(\\d*?)\\.(\\d*?), start: (.*?), bitrate: (\\d*) kb\\/s.*";
    private static Pattern durationPattern;
    /**
     * 视频流信息正则匹配式
     * 用于解析视频详细信息时使用；
     */
    private static String videoStreamRegex = "Stream #\\d:\\d[\\(]??\\S*[\\)]??: Video: (\\S*\\S$?)[^\\,]*, (.*?), (\\d*)x(\\d*)[^\\,]*, (\\d*) kb\\/s, (\\d*[\\.]??\\d*) fps";
    private static Pattern videoStreamPattern;
    /**
     * 音频流信息正则匹配式
     * 用于解析音频详细信息时使用；
     */
    private static String musicStreamRegex = "Stream #\\d:\\d[\\(]??\\S*[\\)]??: Audio: (\\S*\\S$?)(.*), (.*?) Hz, (.*?), (.*?), (\\d*) kb\\/s";
    ;
    private static Pattern musicStreamPattern;

    /**
     * 静态初始化时先加载好用于音视频解析的正则匹配式
     */
    static {
        durationPattern = Pattern.compile(durationRegex);
        videoStreamPattern = Pattern.compile(videoStreamRegex);
        musicStreamPattern = Pattern.compile(musicStreamRegex);
    }


    /**
     * 视频转换
     *      *
     *      * @param fileInput 源视频路径
     *      * @param fileOutPut 转换后的视频输出路径
     */
    public static void convertVideoToTs(String fileInput, File fileOutPut) {

        //TODO 检测minio中文件是否存在

        if ("".equals(fileOutPut) || null == fileOutPut) {
            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
        }

        if (!fileOutPut.exists()) {
            try {
                fileOutPut.createNewFile();
            } catch (IOException e) {
                log.error("视频转换时新建输出文件失败");
            }
        }

        /*String format = getFormat(fileInput);
        if (!isLegalFormat(format, VIDEO_TYPE)) {
            throw new RuntimeException("无法解析的视频格式：" + format);
        }*/

        List<String> commonds = new ArrayList<String>();
        commonds.add("-i");
        commonds.add(fileInput);
        commonds.add("-c");
        commonds.add("copy");
        commonds.add("-bsf:v");
        commonds.add("h264_mp4toannexb");
        commonds.add("-f");
        commonds.add("mpegts");
        commonds.add("-y");
        commonds.add(fileOutPut.getAbsolutePath());

        FFmpegCommandUtils.executeCommand(commonds);
    }

    /**
     * 视频转换
     *      *
     *      * @param fileInput 源视频路径
     *      * @param fileOutPut 转换后的视频输出路径
     */
    public static void convertVideoToTs(File fileInput, File fileOutPut) {

        if (null == fileInput || !fileInput.exists()) {
            throw new RuntimeException("源视频文件不存在，请检查源视频路径");
        }
        if ("".equals(fileOutPut) || null == fileOutPut) {
            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
        }

        if (!fileOutPut.exists()) {
            try {
                fileOutPut.createNewFile();
            } catch (IOException e) {
                log.error("视频转换时新建输出文件失败");
            }
        }

        /*String format = getFormat(fileInput);
        if (!isLegalFormat(format, VIDEO_TYPE)) {
            throw new RuntimeException("无法解析的视频格式：" + format);
        }*/

        List<String> commonds = new ArrayList<String>();
        commonds.add("-i");
        commonds.add(fileInput.getAbsolutePath());
        commonds.add("-c");
        commonds.add("copy");
        commonds.add("-bsf:v");
        commonds.add("h264_mp4toannexb");
        commonds.add("-f");
        commonds.add("mpegts");
        commonds.add("-y");
        commonds.add(fileOutPut.getAbsolutePath());

        OtherMediaUtil.executeCommand(commonds);
    }

    /**
     * 视频转换
     *      *
     *      * @param fileInput 源视频路径
     *      * @param fileOutPut 转换后的视频输出路径
     */
    public static void spliceTsVideoToMp4(String fileInput, File fileOutPut) {

        //TODO 检测minio中文件是否存在

        if ("".equals(fileOutPut) || null == fileOutPut) {
            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
        }

        if (!fileOutPut.exists()) {
            try {
                fileOutPut.createNewFile();
            } catch (IOException e) {
                log.error("视频转换时新建输出文件失败");
            }
        }

        /*String format = getFormat(fileInput);
        if (!isLegalFormat(format, VIDEO_TYPE)) {
            throw new RuntimeException("无法解析的视频格式：" + format);
        }*/

        List<String> commonds = new ArrayList<String>();
        commonds.add("-i");
        commonds.add(fileInput);
        commonds.add("-c");
        commonds.add("copy");
        //commonds.add("-bsf:a");
        //commonds.add("aac_adtstoasc");
        commonds.add("-movflags");
        commonds.add("+faststart");
        commonds.add("-y");
        commonds.add(fileOutPut.getAbsolutePath());

        FFmpegCommandUtils.executeCommand(commonds);
    }


    /**
     * 视频转换
     *      *
     *      * 注意指定视频分辨率时，宽度和高度必须同时有值；
     *      *
     *      * @param fileInput 源视频路径
     *      * @param fileOutPut 转换后的视频输出路径
     *      * @param withAudio 是否保留音频；true-保留，false-不保留
     *      * @param crf 指定视频的质量系数（值越小，视频质量越高，体积越大；该系数取值为0-51，直接影响视频码率大小）,取值参考：CrfValueEnum.code
     *      * @param preset 指定视频的编码速率（速率越快压缩率越低），取值参考：PresetVauleEnum.presetValue
     *      * @param width 视频宽度；为空则保持源视频宽度
     *      * @param height 视频高度；为空则保持源视频高度
     */
    public static void convertVideo(File fileInput, File fileOutPut, boolean withAudio, Integer crf, String preset, Integer width, Integer height) {
        if (null == fileInput || !fileInput.exists()) {
            throw new RuntimeException("源视频文件不存在，请检查源视频路径");
        }
        if (null == fileOutPut) {
            throw new RuntimeException("转换后的视频路径为空，请检查转换后的视频存放路径是否正确");
        }

        if (!fileOutPut.exists()) {
            try {
                fileOutPut.createNewFile();
            } catch (IOException e) {
                log.error("视频转换时新建输出文件失败");
            }
        }

        String format = getFormat(fileInput);
        if (!isLegalFormat(format, VIDEO_TYPE)) {
            throw new RuntimeException("无法解析的视频格式：" + format);
        }

        List<String> commonds = new ArrayList<String>();
        commonds.add("-i");
        commonds.add(fileInput.getAbsolutePath());
        if (!withAudio) { // 设置是否保留音频
            commonds.add("-an");  // 去掉音频
        }
        if (null != width && width > 0 && null != height && height > 0) { // 设置分辨率
            commonds.add("-s");
            String resolution = width.toString() + "x" + height.toString();
            commonds.add(resolution);
        }

        commonds.add("-vcodec"); // 指定输出视频文件时使用的编码器
        commonds.add("libx264"); // 指定使用x264编码器
        commonds.add("-preset"); // 当使用x264时需要带上该参数
        commonds.add(preset); // 指定preset参数
        commonds.add("-crf"); // 指定输出视频质量
        commonds.add(crf.toString()); // 视频质量参数，值越小视频质量越高
        commonds.add("-y"); // 当已存在输出文件时，不提示是否覆盖
        commonds.add(fileOutPut.getAbsolutePath());

        FFmpegCommandUtils.executeCommand(commonds);
    }

    /**
     * 获取指定文件的后缀名
     * @param file
     * @return
     */
    private static String getFormat(File file) {
        String fileName = file.getName();
        String format = fileName.substring(fileName.indexOf(".") + 1);
        return format;
    }

    /**
     * 检测视频格式是否合法
     * @param format
     * @param formats
     * @return
     */
    private static boolean isLegalFormat(String format, String formats[]) {
        for (String item : formats) {
            if (item.equals(StringUtils.upperCase(format))) {
                return true;
            }
        }
        return false;
    }


}
