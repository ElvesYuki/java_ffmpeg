package com.vianstats.media.convert.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LuoHuan
 * @date 2020/7/23
 */
public class ImgMetadataDTO implements Serializable {

    private static final long serialVersionUID = -8348472894864274623L;

    @ApiModelProperty(value = "图片链接地址")
    private String imgUrl;

    @ApiModelProperty(value = "图片宽度")
    private Integer imgWidth;

    @ApiModelProperty(value = "图片高度")
    private Integer imgHeight;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    @Override
    public String toString() {
        return "ImgMetadataDTO{" +
                "imgUrl='" + imgUrl + '\'' +
                ", imgWidth=" + imgWidth +
                ", imgHeight=" + imgHeight +
                '}';
    }
}
