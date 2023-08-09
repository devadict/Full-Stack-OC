package com.app.raghu.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {


    @NonNull
    private String name;

    @NonNull
    private String username;
    
    @NonNull
    @Email
    @Size(max = 40)
    private String email;

    @NonNull
    @Size(min = 8)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}")
    private String password;
}
