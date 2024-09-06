package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.MovieNotFoundException;
import com.example.bmsbookticket.exceptions.UserNotFoundException;
import com.example.bmsbookticket.models.Movie;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.MovieRepository;
import com.example.bmsbookticket.repositories.RatingRepository;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class RatingsServiceImpl implements RatingsService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        Rating ratingOfMovie = ratingRepository.findByUserIdAndMovieId(userId, movieId);

        if (ratingOfMovie == null) {
            ratingOfMovie = new Rating();
            ratingOfMovie.setMovie(movie);
            ratingOfMovie.setUser(user);
        }

        ratingOfMovie.setRating(rating);
        return ratingRepository.save(ratingOfMovie);
    }

    @Override
    public double getAverageRating(int movieId) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        List<Rating> ratings = ratingRepository.findByMovieId(movieId);

        OptionalDouble average = ratings.stream()
                .mapToDouble(Rating::getRating)
                .average();

        if (average.isPresent()) return average.getAsDouble();
        else return 0;
    }
}
