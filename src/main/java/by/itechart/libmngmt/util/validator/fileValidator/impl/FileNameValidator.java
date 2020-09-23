package by.itechart.libmngmt.util.validator.fileValidator.impl;

import by.itechart.libmngmt.util.emailScheduler.EmailSender;
import by.itechart.libmngmt.util.validator.fileValidator.Validator;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class FileNameValidator implements Validator {
    private final static Logger logger = LogManager.getLogger(FileNameValidator.class.getName());
    @Override
    public boolean validate(Object fileName) {
        List<String> fileList = getFileList();
        for (String existingFilename : fileList) {
            if (fileName.equals(existingFilename)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getFileList() {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("uploadFolderPath")))) {
            return (walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.debug("Getting file list error!", e);
        }
        return null;
    }
}
