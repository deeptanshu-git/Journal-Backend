package com.DT.journal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NonNull
    @Schema(description = "The user's username")
    private String userName;
    private String email;
    private String city;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
}
