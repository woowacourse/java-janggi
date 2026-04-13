package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.camp.CampType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampTypeTest {

    @ParameterizedTest
    @CsvSource({
            "CHO, HAN",
            "HAN, CHO"
    })
    void next를_호출하면_상대_진영을_반환한다(CampType current, CampType expectedResult) {
        // when
        CampType next = current.next();
        // then
        assertThat(next).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 1",
            "HAN, -1"
    })
    void 전진_방향이_일치하는지_확인한다(CampType campType, int rowDirection) {
        assertThat(campType.matchesForwardDirection(rowDirection)).isTrue();
    }
}
