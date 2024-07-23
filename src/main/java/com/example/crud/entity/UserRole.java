package com.example.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

//用于建立User和Role之间的映射关系
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @NotEmpty
    private Long id;

    private Long userId;

    private Long roleId;
}
