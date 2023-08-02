package com.app.raghu.dto.request;

import javax.validation.constraints.Email;
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
    @Email
    @Size(max = 40)
    private String email;

    @NonNull
    @Size(max = 120)
    private String password;
}
