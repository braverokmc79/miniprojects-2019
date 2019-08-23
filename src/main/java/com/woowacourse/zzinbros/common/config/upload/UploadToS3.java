package com.woowacourse.zzinbros.common.config.upload;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadToS3 extends AbstractUploadTo {
    private static final Logger log = LoggerFactory.getLogger(UploadToS3.class);
    private AmazonS3 amazonS3;
    private String bucketName;
    private String bucketUrl;

    public UploadToS3(MultipartFile file, @NotNull String bucketName, @NotNull String bucketUrl) {
        super(file);
        this.bucketName = bucketName;
        this.bucketUrl = bucketUrl;
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
    }

    @Override
    public String save() {
        String keyName = hashFileName() + getExtension();
        File localFile = convertMultiPartToFile(file);
        amazonS3.putObject(bucketName, keyName, localFile);
        localFile.delete();
        return bucketUrl + keyName;
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
            fos.close();
            return convertFile;
        } catch (IOException e) {
            log.warn("FILE CONVERT FAILED : {}", e.getMessage());
            throw new IllegalAccessError();
        }
    }
}