package by.itechart.libmngmt.util;

import java.io.File;

public class FolderPropertiesAdder {
    public static void addImageFolder(){
        String uploadPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                File.separator + "webapp" + File.separator + "images" + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        System.setProperty("uploadFolderPath", uploadPath);
    }

}


