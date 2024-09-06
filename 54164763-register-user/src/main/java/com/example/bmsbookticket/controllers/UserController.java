package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public SignupUserResponseDTO signupUser(SignupUserRequestDTO requestDTO){
        SignupUserResponseDTO responseDto = new SignupUserResponseDTO();

        try {
            User user = userService.signupUser(
                    requestDTO.getName(),
                    requestDTO.getEmail(),
                    requestDTO.getPassword()
            );

            responseDto.setUserId(user.getId());
            responseDto.setEmail(user.getEmail());
            responseDto.setName(user.getName());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public LoginResponseDto login(LoginRequestDto requestDto){
        LoginResponseDto responseDto = new LoginResponseDto();

        try {
            boolean loggedIn = userService.login(
                    requestDto.getEmail(),
                    requestDto.getPassword()
            );

            responseDto.setLoggedIn(loggedIn);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

}
