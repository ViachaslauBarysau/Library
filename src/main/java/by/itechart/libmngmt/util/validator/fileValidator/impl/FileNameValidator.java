package by.itechart.libmngmt.util.validator.fileValidator.impl;

import by.itechart.libmngmt.util.validator.fileValidator.Validator;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class FileNameValidator implements Validator {

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

        }
        return null;
    }




}
