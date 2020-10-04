package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a ReaderEntity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReaderEntity {
    private int id;
    private String name;
    private String email;
}
