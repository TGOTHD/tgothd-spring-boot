package com.tgothd.tgothd.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ShrJanLan
 * @date: 2023/6/26 10:08
 * @description:
 */
public interface FileService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    ResponseEntity upload(MultipartFile file) throws Exception;

    /**
     * 文件上传
     * @param bucketName
     * @param file
     * @return
     */
    ResponseEntity upload(String bucketName,MultipartFile file) throws Exception;

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    ResponseEntity download(String fileName) throws Exception;

    /**
     * 文件下载
     * @param fileName
     * @param actualFileName
     * @return
     */
    ResponseEntity download(String fileName,String actualFileName) throws Exception;

    /**
     * 文件下载
     * @param bucketName
     * @param fileName
     * @return
     */
    ResponseEntity download(String bucketName,String fileName,String actualFileName) throws Exception;

}
