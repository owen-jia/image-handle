package com.owenjia.service;

import com.owenjia.config.ImageProperties;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class ImageHandle {

    Logger logger = LoggerFactory.getLogger(ImageHandle.class);

    @Autowired
    ImageProperties imageProperties;

    public void start() {
        long start = System.currentTimeMillis();
        init();
        long end = System.currentTimeMillis();
        System.out.println("thread use time<seconds> is " + (end-start)/1000+" .");
    }

    private void init(){
        logger.info("image<jpg> handle starting.");

        File fold = new File(imageProperties.getSourcePath());
        if(!fold.exists() || !fold.isDirectory()) {
            logger.info("error<image folder path" +fold.getAbsolutePath()+">.");
            return;
        }

        File target = new File(fold.getParent() + File.separator + "target_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
        target.mkdir();

        fileHandle(fold,target);
        logger.info("image<jpg> handle finished.");
        logger.info("new image save at " + target.getAbsolutePath());
    }

    private void fileHandle(File sourceFold,File targetFold){
        File[] files = sourceFold.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        if(files.length > 20000) {
            logger.info("error<image count > 20000>.");
            return;
        }

        int fileCount = 0;
        while (fileCount < files.length){
            File source = files[fileCount];
            String newFile = targetFold.getAbsolutePath() + File.separator + fileRename(source.getName());

            imageScale(source,new File(newFile));
            fileCount++;
        }
    }

    private String fileRename(String sourceFile){
        return "__"+sourceFile;
    }

    private void imageScale(File sourceFile,File targetFile) {
        try {
            logger.info(sourceFile.getName() + " -> " + targetFile.getName());
            Thumbnails.of(sourceFile).scale(1).toFile(targetFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
