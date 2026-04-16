package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTypeTest {

    @ParameterizedTest
    @CsvSource({
            "CHA, 13.0",
            "PO, 7.0",
            "MA, 5.0",
            "SANG, 3.0",
            "SA, 3.0",
            "ZOL, 2.0",
            "KING, 0.0"
    })
    @DisplayName("차(13점), 포(7점), 마(5점), 상(3점), 사(3점), 졸(2점)을 반환한다.")
    void 기물당_점수_반환(PieceType pieceType, double score) {
        //given & when
        double pieceTypeScore = pieceType.getScore();

        //then
        assertThat(pieceTypeScore).isEqualTo(score);
    }

}
