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
        int age = 0;
        assertThat(AgeRange.get(age).equals(AgeRange.Age0_9)).isTrue();
        age=1;
        assertThat(AgeRange.get(age).equals(AgeRange.Age10_19)).isTrue();
        age=1;
        assertThat(AgeRange.get(age).equals(AgeRange.Age10_19)).isTrue();
        age=2;
        assertThat(AgeRange.get(age).equals(AgeRange.Age20_29)).isTrue();
        age=2;
        assertThat(AgeRange.get(age).equals(AgeRange.Age20_29)).isTrue();
        age=3;
        assertThat(AgeRange.get(age).equals(AgeRange.Age30_39)).isTrue();
        age=3;
        assertThat(AgeRange.get(age).equals(AgeRange.Age30_39)).isTrue();
        age=4;
        assertThat(AgeRange.get(age).equals(AgeRange.Age40_49)).isTrue();
        age=4;
        assertThat(AgeRange.get(age).equals(AgeRange.Age40_49)).isTrue();
        age=5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
        age=6;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isTrue();
    }

    @Test
    void failTest() {
        int age = 5;
        assertThat(AgeRange.get(age).equals(AgeRange.Age50_Above)).isFalse();
    }
}