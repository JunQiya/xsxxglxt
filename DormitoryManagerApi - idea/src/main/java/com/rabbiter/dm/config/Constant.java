package com.rabbiter.dm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rabbiter
 * @date 2021-12-08
 */
@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {
    public final static String HEADER_TOKEN = "_ut";
    public final static String DEFAULT_PASSWORD = "123456";

    private String uploadPath;

    // Getter 和 Setter
    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
        // 同时设置静态变量以保持兼容性
        UPLOAD_PATH = uploadPath;
    }

    // 为了保持向后兼容，保留静态变量
    public static String UPLOAD_PATH;
}