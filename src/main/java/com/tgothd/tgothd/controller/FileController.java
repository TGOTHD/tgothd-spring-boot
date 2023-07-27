package com.tgothd.tgothd.controller;

import com.tgothd.tgothd.service.FileService;
import com.tgothd.tgothd.utils.ResponseEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ShrJanLan
 * @date: 2023/6/26 10:23
 * @description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static Logger log = LoggerFactory.getLogger(FileController.class);

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam(value = "bucket",required = false) String bucket,MultipartFile file){
        try{
            return fileService.upload(bucket, file);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntityUtil.error(e);
        }
    }

    /**
     * 通用下载请求
     */
    @GetMapping("/download")
    public ResponseEntity download(@RequestParam(value = "bucket",required = false) String bucket
            , @RequestParam(value = "fileName") String fileName
            , @RequestParam(value = "actualFileName",required = false) String actualFileName) {
        try {
            return fileService.download(bucket, fileName, actualFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.error(e);
        }
    }

}
