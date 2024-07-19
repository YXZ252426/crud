package com.example.crud.entity;
;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String name;
}