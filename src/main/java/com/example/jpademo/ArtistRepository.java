package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findBySingerIgnoreCase(String singer);
}
