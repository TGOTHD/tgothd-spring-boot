package com.tgothd.tgothd.service.impl;

import com.tgothd.tgothd.config.MinioConfig;
import com.tgothd.tgothd.service.FileService;
import com.tgothd.tgothd.utils.ResponseEntityUtil;
import io.minio.*;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: ShrJanLan
 * @date: 2023/6/26 10:08
 * @description:
 */
@Service
public class FileServiceImpl implements FileService {

    private static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private MinioConfig minioConfig;

    @Autowired
    public void setMinioConfig(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
    }

    @Override
    public ResponseEntity upload(MultipartFile file) throws Exception {
        return upload(null, file);
    }

    /**
     * 文件上传至MinIo
     * 使用try catch finally进行上传
     * finally里进行资源的回收
     */
    @Override
    public ResponseEntity upload(String bucketName,MultipartFile file) throws Exception {
        InputStream inputStream = null;
        //创建Minio的连接对象
        MinioClient minioClient = getClient();
        //获取存储桶
        boolean defBucket = bucketName == null;
        if (defBucket) {
            bucketName = minioConfig.getBucketName();
        }
        try {
            inputStream = file.getInputStream();

            if (defBucket) {
                // Make bucketName bucket if not exist.
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!found) {
                    // Make a new bucket called bucketName.
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                }
            }

            //Uploads given stream as object in bucket.
            String oldFileName = file.getOriginalFilename();
            String oldFileSuffixName = oldFileName == null ? "" : oldFileName.substring(oldFileName.lastIndexOf("."));
            Calendar calendar = Calendar.getInstance();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(calendar.getTime()) + UUID.randomUUID() + oldFileSuffixName;
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType()).build());
            /**
             * 封装需要的数据进行返回
             */
            Map vo = ResponseEntityUtil.getResultMap(HttpStatus.OK.value(), "success");
            vo.put("bucket", bucketName);
            vo.put("fileName", "/"+objectName);
            vo.put("actualFileName", oldFileName);
            vo.put("url", minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntityUtil.error(e);
        }finally {
            //防止内存泄漏
            if (inputStream != null) {
                try {
                    inputStream.close(); // 关闭流
                } catch (IOException e) {
                    log.debug("inputStream close IOException:" + e.getMessage());
                }
            }
        }
    }

    @Override
    public ResponseEntity download(String fileName) throws Exception {
        return download(null, fileName, null);
    }

    @Override
    public ResponseEntity download(String fileName,String actualFileName) throws Exception {
        return download(null, fileName, actualFileName);
    }

    @Override
    public ResponseEntity download(String bucketName,String fileName,String actualFileName) throws Exception {
        //创建Minio的连接对象
        MinioClient minioClient = getClient();
        ResponseEntity responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            bucketName = bucketName == null ? minioConfig.getBucketName() : bucketName;
            actualFileName = actualFileName == null ? fileName : actualFileName;
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(actualFileName, StandardCharsets.UTF_8));
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Collections.singletonList("*"));
            responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseEntity;
    }

    /**
     * 提供一个获取Minio连接的方法
     * 获取Minio连接
     * @return
     */
    private MinioClient getClient(){
        return MinioClient.builder()
                        .endpoint(minioConfig.getEndpoint())
                        .credentials(minioConfig.getAccessKey(),minioConfig.getSecretKey())
                        .build();
    }

}
