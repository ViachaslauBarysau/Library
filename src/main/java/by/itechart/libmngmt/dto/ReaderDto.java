package by.itechart.libmngmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderDto {

    private int id;
    private String name;
    private String email;
    private Date dateOfRegistration;
    private Long phoneNumber;

}
