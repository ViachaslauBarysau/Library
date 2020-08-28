package by.itechart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reader {
    private String name;
    private String email;
    private Date dateOfRegistration;
    private Long phoneNumber;
    private String gender;
}
