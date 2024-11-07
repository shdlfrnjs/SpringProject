package com.example.jpademo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "music")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String title;

    private String singer;

    private String album;

    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private int ranking;

    @Column(name = "booking_num")
    private String bookingNum;

    private String image;

}
