package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampTest {

    @DisplayName("상대 진영을 알 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, HAN",
            "HAN, CHU"
    })
    void reverseTest(Camp camp, Camp expected) {
        // when
        Camp reversed = camp.reverse();

        // then
        assertThat(reversed)
                .isSameAs(expected);
    }

    @DisplayName("적인지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, HAN, true",
            "HAN, CHU, true",
            "CHU, CHU, false",
            "HAN, HAN, false"
    })
    void isEnemyTest(Camp camp, Camp enemy, boolean expected) {
        // when
        boolean result = camp.isEnemy(enemy);

        // then
        assertThat(result)
                .isSameAs(expected);
    }
}
