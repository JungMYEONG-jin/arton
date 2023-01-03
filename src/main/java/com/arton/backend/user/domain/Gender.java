package com.arton.backend.user.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

@Getter
public enum Gender {
    MALE("남자"), FEMALE("여자"), ETC("기타"),
    ;
    private String value;

    Gender(String value) {
        this.value = value;
    }

    private static final Map<String, Gender> GENDER_MAP;

    static{
        Map<String, Gender> map = new ConcurrentHashMap<>();
        for (Gender gender : Gender.values()) {
            map.put(gender.name(), gender);
        }
        GENDER_MAP = Collections.unmodifiableMap(map);
    }

    public static Gender get(String name){
        return hasText(name)?GENDER_MAP.get(name):null;
    }
}
