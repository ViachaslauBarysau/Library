package by.itechart.libmngmt.util.validator.fileValidator;

import by.itechart.libmngmt.util.validator.fileValidator.impl.FileNameValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUploader {
    private final static Logger logger = LogManager.getLogger(FileUploader.class.getName());

    public static void uploadFile(Part filePart, String fileName) {
        try (InputStream fileContent = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(System.getProperty("uploadFolderPath")
                     + fileName));) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            logger.debug("File upload error!", e);
        }
    }
}
