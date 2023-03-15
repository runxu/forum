package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@RestController
public class CommonController {
    @Value("${upload.path}")
    private String baseFolderPath;

    @PostMapping({"/upload"})
    public Result uploadPic(MultipartFile image, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());
        File baseFolder = new File(this.baseFolderPath + filePath);
        if (!baseFolder.exists())
            baseFolder.mkdirs();
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(this.baseFolderPath)
                .append(filePath);
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + image.getOriginalFilename().replaceAll(" ", "");
        File dest = new File(baseFolder, imgName);
        try {
            FileCopyUtils.copy(image.getBytes(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        url.append("/").append(imgName);
        return Result.succ(url);
    }
}
