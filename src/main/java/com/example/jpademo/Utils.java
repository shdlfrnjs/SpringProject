package com.example.jpademo;

public class Utils {
    public static MusicDTO toDTO(Music entity) {
        return MusicDTO.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .singer(entity.getSinger())
                .album(entity.getAlbum())
                .genre(entity.getGenre())
                .releaseDate(entity.getReleaseDate()) // Date 유지
                .ranking(entity.getRanking())
                .bookingNum(entity.getBookingNum()) // bookingNum 필드
                .image(entity.getImage())
                .build();
    }

    public static Music toEntity(MusicDTO dto) {
        return Music.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .singer(dto.getSinger())
                .album(dto.getAlbum())
                .genre(dto.getGenre())
                .releaseDate(dto.getReleaseDate()) // Date 유지
                .ranking(dto.getRanking())
                .bookingNum(dto.getBookingNum()) // bookingNum 필드
                .image(dto.getImage())
                .build();
    }
}
