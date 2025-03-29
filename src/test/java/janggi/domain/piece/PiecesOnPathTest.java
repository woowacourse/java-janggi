package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PiecesOnPathTest {

    @DisplayName("목적지가 같은 나라인지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHU, false"
    })
    void isDestinationOfDynasty(Dynasty dynasty, boolean expected) {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new Chariot(Dynasty.HAN));

        //when
        boolean actual = piecesOnPath.isDestinationOfDynasty(dynasty);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("목적지를 제외하고 모두 빈 피스인지 확인할 수 있다.")
    @Test
    void isAllEmptyWithoutDestination() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new EmptyPiece(), new EmptyPiece(), new HanSoldier());

        //when
        boolean actual = piecesOnPath.isAllEmptyWithoutDestination();

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("같은 피스가 존재하는지 알 수 있다.")
    @Test
    void isExistSamePiece() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new HanSoldier(), new EmptyPiece(), new HanSoldier());

        //when
        boolean result = piecesOnPath.isExistSamePiece(new HanSoldier());

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("목적지를 제외하고 같지 않은 피스의 개수를 알 수 있다")
    @Test
    void countNotSamePieceWithoutDestination() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new HanSoldier(), new EmptyPiece(), new HanSoldier());

        //when
        int count = piecesOnPath.countNotSamePieceWithoutDestination(new HanSoldier());

        //then
        assertThat(count).isEqualTo(0);
    }
}