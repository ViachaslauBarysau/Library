package by.itechart.libmngmt.util.validation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ValidationResult {
    List<String> errorList;
}
