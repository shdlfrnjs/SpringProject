package com.example.jpademo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {
    private long idx;
    private String title;
    private String singer;
    private String album;
    private String genre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private int ranking;
    private String bookingNum;
    private String image;
}
