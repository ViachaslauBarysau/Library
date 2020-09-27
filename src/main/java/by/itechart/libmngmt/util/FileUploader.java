package by.itechart.libmngmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploader {
    private final static Logger LOGGER = LogManager.getLogger(FileUploader.class.getName());
    private static volatile FileUploader instance;

    public static synchronized FileUploader getInstance() {
        FileUploader localInstance = instance;
        if (localInstance == null) {
            synchronized (FileUploader.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FileUploader();
                }
            }
        }
        return localInstance;
    }

    public void uploadFile(Part filePart, String fileName) {
        try (InputStream fileContent = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(System.getProperty("uploadFolderPath")
                     + fileName));) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            LOGGER.debug("File upload error.", e);
        }
    }
}
