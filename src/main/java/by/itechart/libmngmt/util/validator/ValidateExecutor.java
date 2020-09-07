package by.itechart.libmngmt.util.validator;

import javax.servlet.http.Part;

public class ValidateExecutor {

    public static String validateAndUploadFile(Part filePart) {
        String fileName = "";
        final int SIZE_VALIDATOR = 1;
        final int TYPE_VALIDATOR = 2;
        final int NAME_VALIDATOR = 3;

        Validator sizeValidator = ValidatorFactory.validatorCreate(SIZE_VALIDATOR);
        Validator typeValidator = ValidatorFactory.validatorCreate(TYPE_VALIDATOR);
        Validator nameValidator = ValidatorFactory.validatorCreate(NAME_VALIDATOR);

        if (sizeValidator.validate(filePart.getSize()) == false ||
            typeValidator.validate(filePart.getContentType()) == false ||
            nameValidator.validate(filePart.getSubmittedFileName()) == false) {
            fileName = "";
        } else {
            FileUploader.uploadFile(filePart);
            fileName = filePart.getSubmittedFileName();
        }
        return fileName;
    }
}
