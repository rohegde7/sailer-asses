package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
