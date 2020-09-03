package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderCardEntity {

    private int id;
    private int bookId;
    private int readerId;
    private String readerName;
    private String readerEmail;
    private Date borrowDate;
    private int timePeriod;
    private Date dueDate;
    private Timestamp returnDate;

}
