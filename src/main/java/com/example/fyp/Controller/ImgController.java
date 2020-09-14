package com.example.fyp.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.coyote.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
public class ImgController {

    @GetMapping(value = "Users")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(name = "imgPath") String img) throws IOException {
        ClassPathResource imageFile = new ClassPathResource("images\\users\\"+img);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(imageFile.getInputStream()));
    }

    public String saveUserImage(String imageString, String fileName) throws Exception {
        OutputStream outputStream = null;
        byte[] byteFormat = Base64.decodeBase64(imageString);
        String oldPath = "C:\\Users\\peter\\Desktop\\fyp\\src\\main\\resources\\images\\users\\";
        String Path =  fileName + ".png";
        String newPath = oldPath+Path;
        try {
            outputStream = new FileOutputStream(newPath);
            outputStream.write(byteFormat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Path;
    }

    public String saveProductImage(String imageString, String fileName) throws Exception {
        OutputStream outputStream = null;
        byte[] byteFormat = Base64.decodeBase64(imageString);
        String oldPath = "C:\\Users\\peter\\Desktop\\fyp\\src\\main\\resources\\images\\products\\";
        String Path =  fileName + ".png";
        String newPath = oldPath+Path;
        try {
            outputStream = new FileOutputStream(newPath);
            outputStream.write(byteFormat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Path;
    }
}