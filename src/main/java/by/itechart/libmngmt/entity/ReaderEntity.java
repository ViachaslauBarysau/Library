package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderEntity {

    private int id;
    private String name;
    private String email;

}
