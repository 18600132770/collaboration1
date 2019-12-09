package com.huag.collaboration.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

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

    /**
     * 下载文件到本地
     */
    public static void downLoadFileToLocal(){
        String objectName = "localFileFolder/localFile.txt";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("E:\\test.txt"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static String getFileData(String filePath) throws Exception{
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, filePath);
        // 读取文件内容。
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            sb.append(line).append("\n");
        }
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();
        // 关闭OSSClient。
        ossClient.shutdown();
        return sb.toString();
    }




}
