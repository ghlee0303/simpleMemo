package smm.simpleMemo.controller;

import smm.simpleMemo.dto.ImageDto;
import smm.simpleMemo.response.ResponseMemo;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class ImageController {

    // 이미지 저장 후 url 반환
    @ResponseBody
    @PostMapping("/image/upload")
    public ResponseMemo<ImageDto> postImageUpload(@RequestParam(name = "image") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }
        String homePath = "http://localhost:8080/image/data/";

        ImageDto imageDto = new ImageDto(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File(imageDto.getFullPath()));

        return new ResponseMemo<>(imageDto.responseImageDto(homePath+imageDto.getRandomFileName()));
    }

    // 이미지 요청
    @ResponseBody
    @GetMapping("/image/{type}/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable String type, @PathVariable String name) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        InputStream in = new BufferedInputStream(
                new FileInputStream("C:\\Users\\pjh13\\Desktop\\Fuck\\java-spring\\image\\"+type+"\\"+name));
        byte[] media = IOUtils.toByteArray(in);
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }
}
