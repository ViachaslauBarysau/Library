package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Represents a ReaderCardEntity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderCardEntity {
    private int id;
    private int bookId;
    public String bookTitle;
    private int readerId;
    private String readerName;
    private String readerEmail;
    private Date borrowDate;
    private int timePeriod;
    private Date dueDate;
    private String status;
    private Timestamp returnDate;
    private String comment;
}
