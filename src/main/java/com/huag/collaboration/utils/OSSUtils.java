package com.huag.collaboration.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * OSS文件工具
 * @author huag
 * @date 2019/10/28 15:00
 */
public class OSSUtils{


    static String endpoint = null;
    static String accessKeyId = null;
    static String accessKeySecret = null;
    static String bucketName = null;


    static{
        try {
            endpoint = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("oss.endpoint");
            accessKeyId = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("oss.accessKeyId");
            accessKeySecret = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("oss.accessKeySecret");
            bucketName = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("oss.bucketName");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 上传本地文件到OSS
     * @param localFilePath
     * @param uploadFilePath
     */
    public static void uploadFile(String localFilePath, String uploadFilePath){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, uploadFilePath, new File(localFilePath));
        ossClient.shutdown();
    }

    /**
     * 下载oss文件到本地
     * @param ossFileUrl
     * @param localFilePath
     */
    public static void downloadFileToLocal(String ossFileUrl, String localFilePath){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.getObject(new GetObjectRequest(bucketName, ossFileUrl), new File(localFilePath));
        ossClient.shutdown();
    }

    /**
     * 在OSS服务器创建一个内容是content的oosFileUrl文件
     * @param ossFileUrl
     * @param content
     */
    public static void uploadString(String ossFileUrl, String content){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, ossFileUrl, new ByteArrayInputStream(content.getBytes()));
        ossClient.shutdown();
    }




}
