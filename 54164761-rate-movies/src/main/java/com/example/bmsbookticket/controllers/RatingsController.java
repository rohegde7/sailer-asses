package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.services.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RatingsController {
    @Autowired
    RatingsService ratingsService;

    public RateMovieResponseDto rateMovie(RateMovieRequestDto requestDto) {
        RateMovieResponseDto responseDto = new RateMovieResponseDto();

        try {
            Rating rating = ratingsService.rateMovie(
                    requestDto.getUserId(),
                    requestDto.getMovieId(),
                    requestDto.getRating()
            );

            responseDto.setRating(rating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public GetAverageMovieResponseDto getAverageMovieRating(GetAverageMovieRequestDto requestDto) {
        GetAverageMovieResponseDto responseDto = new GetAverageMovieResponseDto();

        try {
            double rating = ratingsService.getAverageRating(
                    requestDto.getMovieId()
            );

            responseDto.setAverageRating(rating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
