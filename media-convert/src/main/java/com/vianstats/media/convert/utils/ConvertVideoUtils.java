package com.vianstats.media.convert.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LuoHuan
 * @date 2020/7/17
 */
@Component
public class ConvertVideoUtils {

    private static final Logger log = LoggerFactory.getLogger(ConvertVideoUtils.class);

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

        /*if (!fileOutPut.exists()) {
            try {
                fileOutPut.createNewFile();
            } catch (IOException e) {
                log.error("视频转换时新建输出文件失败");
            }
        }*/

        String format = FFmpegCommandUtils.getFormat(fileInput);
        if (!FFmpegCommandUtils.isLegalFormat(format, FFmpegCommandUtils.VIDEO_TYPE)) {
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
        //commonds.add("-y"); // 当已存在输出文件时，不提示是否覆盖
        commonds.add(fileOutPut.getAbsolutePath());
        //executeCommand(commonds);
        FFmpegCommandUtils.executeCommand(commonds);
    }


}
