package smm.simpleMemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImageDto {
    @JsonIgnore
    String originFileName;
    @JsonIgnore
    String basePath;
    @JsonIgnore
    String randomFileName;
    String ext;
    String fullPath;

    public ImageDto() {
    }

    public ImageDto(String originFileName) {
        this.originFileName = originFileName;
        this.basePath = "C:\\Users\\pjh13\\Desktop\\Fuck\\java-spring\\image\\data\\";
        this.ext = originFileName.substring(originFileName.lastIndexOf(".") + 1);

        String formatedNow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        this.randomFileName = formatedNow + "-" + generatedString + "." + this.ext;
        this.fullPath = this.basePath + this.randomFileName;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRandomFileName() {
        return randomFileName;
    }

    public void setRandomFileName(String randomFileName) {
        this.randomFileName = randomFileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public ImageDto responseImageDto(String path) {
        ImageDto imageDto = new ImageDto();
        imageDto.setFullPath(path);
        imageDto.setExt(this.ext);

        return imageDto;
    }
}
