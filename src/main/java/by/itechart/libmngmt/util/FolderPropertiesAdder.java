package by.itechart.libmngmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Provides method for creating image upload folder.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderPropertiesAdder {
    private static volatile FolderPropertiesAdder instance;

    public static synchronized FolderPropertiesAdder getInstance() {
        FolderPropertiesAdder localInstance = instance;
        if (localInstance == null) {
            synchronized (FolderPropertiesAdder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FolderPropertiesAdder();
                }
            }
        }
        return localInstance;
    }

    /**
     * Creates independent of system file upload path, creates folders
     * and assigns this folder path to the system property.
     */
    public void addImageFolder() {
        String uploadPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "webapp" + File.separator + "images" + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        System.setProperty("uploadFolderPath", uploadPath);
    }
}


