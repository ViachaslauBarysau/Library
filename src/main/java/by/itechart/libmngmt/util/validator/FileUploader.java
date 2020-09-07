package by.itechart.libmngmt.util.validator;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUploader {

    public static void uploadFile(Part filePart) {
        try (InputStream fileContent = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(System.getProperty("uploadFolderPath") + filePart.getSubmittedFileName()));) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
