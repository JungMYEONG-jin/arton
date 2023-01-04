package com.arton.backend.user.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

@Getter
public enum AgeRange {
    Age0_9("아동"),
    Age10_19("10대"),
    Age20_29("20대"),
    Age30_39("30대"),
    Age40_49("40대"),
    Age50_Above("50대이상");

    private String value;

    AgeRange(String value) {
        this.value = value;
    }

    private static final Map<String, AgeRange> AGE_RANGE_MAP;

    static{
        Map<String, AgeRange> map = new ConcurrentHashMap<>();
        for (AgeRange ageRange : AgeRange.values()) {
            map.put(ageRange.name(), ageRange);
        }
        AGE_RANGE_MAP = Collections.unmodifiableMap(map);
    }

    public static AgeRange get(String name){
        return hasText(name)?AGE_RANGE_MAP.get(name):null;
    }

    public static AgeRange get(int age){
        switch (age){
            case 1:
                return Age10_19;
            case 2:
                return Age20_29;
            case 3:
                return Age30_39;
            case 4:
                return Age40_49;
            default:
                if (age>=5)
                    return Age50_Above;
                else
                    return Age0_9;
        }
    }

}
