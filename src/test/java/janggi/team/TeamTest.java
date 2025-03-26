package janggi.team;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TeamTest {
    /*@ParameterizedTest
    @MethodSource("setTeamOption")
    @DisplayName("옵션에 맞는 Team을 반환하는지 확인")
    void TeamOptionTest(String inputOption, Team expected) {
        //given
        //when
        //then
        Assertions.assertThat(Team.from(inputOption)).isEqualTo(expected);
    }

    static Stream<Arguments> setTeamOption() {
        return Stream.of(
                Arguments.arguments(
                        "초", Team.CHO
                ),
                Arguments.arguments(
                        "한", Team.HAN
                )
        );
    }*/
}
