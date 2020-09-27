package by.itechart.libmngmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtility {
    private static final Logger LOGGER = LogManager.getLogger(FileUtility.class.getName());
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String EMPTY_STRING = "";
    private static volatile FileUtility instance;

    public static synchronized FileUtility getInstance() {
        FileUtility localInstance = instance;
        if (localInstance == null) {
            synchronized (FileUtility.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FileUtility();
                }
            }
        }
        return localInstance;
    }

    public String getUniqueName(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.equals(EMPTY_STRING)) {
            while (!checkNameForUnique(fileName)) {
                fileName = generateName(fileName);
            }
            return fileName;
        }
        return request.getParameter("currentCover");
    }

    private String generateName(String fileName) {
        char[] chars = ALPHABET.toCharArray();
        Random random = new Random();
        fileName = chars[random.nextInt(chars.length)] + fileName;
        return fileName;
    }

    private boolean checkNameForUnique(String fileName) {
        List<String> fileList = getFileList();
        for (String existingFilename : fileList) {
            if (fileName.equals(existingFilename)) {
                return false;
            }
        }
        return true;
    }

    private List<String> getFileList() {
        List<String> fileList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("uploadFolderPath")))) {
            fileList = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.debug("Getting file list error.", e);
        }
        return fileList;
    }
}
