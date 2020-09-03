package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderEntity {

    private int id;
    private String name;
    private String email;
    private Date dateOfRegistration;
    private Long phoneNumber;
//    private List<ReaderCard> readerCards;

}
