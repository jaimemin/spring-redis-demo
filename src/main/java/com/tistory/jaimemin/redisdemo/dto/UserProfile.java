package com.tistory.jaimemin.redisdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @JsonProperty
    private String name;

    @JsonProperty
    private int age;
}
