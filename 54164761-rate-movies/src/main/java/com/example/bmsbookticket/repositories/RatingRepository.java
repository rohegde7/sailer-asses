package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating findByUserIdAndMovieId(int userId, int movieId);
    List<Rating> findByMovieId(int movieId);
}
