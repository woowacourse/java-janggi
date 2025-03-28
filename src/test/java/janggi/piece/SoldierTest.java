package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    @DisplayName("Soldier는 뒷 방향을 제외하고 한 칸을 이동할 수 있다.")
    @Test
    void soldiers() {

        // given
        final Piece soldierPiece = new Soldier(Country.HAN);
        final JanggiPosition now = new JanggiPosition(2, 2);
        final JanggiPosition ableDest = new JanggiPosition(3, 2);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 2);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = soldierPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = soldierPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Soldier은 상대 궁성에서 뒷 방향을 제외하고 대각 -> 중심, 중심 -> 대각으로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void soldier1(final JanggiPosition source, final JanggiPosition destination, final Country country) {
        // given
        final Piece soldierPiece = new Soldier(country);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual = soldierPiece.isAbleToMove(source, destination, board);

        // then
        assertThat(actual).isTrue();
    }

    static Stream<Arguments> soldier1(){
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 4), new JanggiPosition(9, 5), Country.HAN),
                Arguments.of(new JanggiPosition(9, 5), new JanggiPosition(10, 6), Country.HAN),
                Arguments.of(new JanggiPosition(3, 4), new JanggiPosition(2, 5), Country.CHO),
                Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(1, 6), Country.CHO)
        );
    }
}
