package com.example.jpademo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "artist")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String singer;

    @Column(name = "debut_date", nullable = false)
    private LocalDate debutDate;

    @Column(nullable = false)

    private String agency;

    private String image;

}