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

    @Column(nullable = false)
    private String singer;

    private String album;

    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private int ranking;

    @Column(name = "booking_num")
    private String bookingNum;

    private String image;

    @ManyToOne
    @JoinColumn(name = "singer", referencedColumnName = "singer", insertable = false, updatable = false)
    private Artist artist;

    //INSERT INTO artist (singer, debut_date, agency)
    //VALUES ('Zico', '2011-01-01', 'KOZ Entertainment');
    //INSERT INTO artist (singer, debut_date, agency)
    //VALUES ('The Weeknd', '2010-12-01', 'XO Records');
}