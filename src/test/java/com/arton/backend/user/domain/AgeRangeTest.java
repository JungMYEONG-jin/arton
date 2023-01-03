package com.arton.backend.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgeRangeTest {

    @Test
    void getRangeTest() {
        int age = 5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age0_9)).isTrue();
        age=10;
        assertThat(AgeRange.get(age).equals(AgeRange.Age10_19)).isTrue();
        age=19;
        assertThat(AgeRange.get(age).equals(AgeRange.Age10_19)).isTrue();
        age=20;
        assertThat(AgeRange.get(age).equals(AgeRange.Age20_29)).isTrue();
        age=29;
        assertThat(AgeRange.get(age).equals(AgeRange.Age20_29)).isTrue();
        age=30;
        assertThat(AgeRange.get(age).equals(AgeRange.Age30_39)).isTrue();
        age=39;
        assertThat(AgeRange.get(age).equals(AgeRange.Age30_39)).isTrue();
        age=40;
        assertThat(AgeRange.get(age).equals(AgeRange.Age40_49)).isTrue();
        age=49;
        assertThat(AgeRange.get(age).equals(AgeRange.Age40_49)).isTrue();
        age=50;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=51;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=59;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=66;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
    }

    @Test
    void failTest() {
        int age = 5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age20_29)).isFalse();
    }
}