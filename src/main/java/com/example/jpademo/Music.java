package com.example.jpademo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "music")
@ToString
@Getter
@Setter
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

    @Column(name = "hits")
    private int hits;

    private String image;

    @ManyToOne
    @JoinColumn(name = "singer", referencedColumnName = "singer", insertable = false, updatable = false)
    private Artist artist;


}