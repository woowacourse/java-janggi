package janggi.rule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampTypeTest {

    @DisplayName("현재 진영의 적 진영을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void getEnemyCampType(CampType currentCamp, CampType expectedEnemyCamp) {
        CampType actualCampType = currentCamp.getEnemyCampType();
        assertThat(actualCampType).isEqualTo(expectedEnemyCamp);
    }

    static Stream<Arguments> getEnemyCampType() {
        return Stream.of(
                Arguments.of(CampType.CHO, CampType.HAN),
                Arguments.of(CampType.HAN, CampType.CHO)
        );
    }

    @DisplayName("현재 진영에 부여되는 기본 점수를 추가할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void addCampDefaultScore(CampType campType, double originScore, double expectedResult) {
        double actualResult = campType.addCampDefaultScore(originScore);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> addCampDefaultScore() {
        return Stream.of(
                Arguments.of(CampType.CHO, 10.0, 10.0),
                Arguments.of(CampType.HAN, 10.0, 10.5)
        );
    }

}