package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {

    @DisplayName("Cannon은 목적지가 같은 라인이 아니라면 false를 반환한다.")
    @Test
    void cannon1() {
        // given
        final Piece cannonPiece = new Cannon(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition ableDest = new JanggiPosition(1, 3);
        final JanggiPosition notAbleDest = new JanggiPosition(2, 2);
        final Country country = Country.HAN;
        final Map<JanggiPosition, Piece> map = Map.of(new JanggiPosition(1, 2),
                new General(country));
        final Board board = new Board(map);

        // when
        final boolean actual1 = cannonPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Cannon은 목적지까지 가는 중 아무런 기물이 없다면 false를 반환한다.")
    @Test
    void cannon2() {
        // given
        final Piece cannonPiece = new Cannon(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition ableDest = new JanggiPosition(1, 3);
        final JanggiPosition notAbleDest = new JanggiPosition(2, 1);
        final Country country = Country.HAN;
        final Map<JanggiPosition, Piece> map = Map.of(new JanggiPosition(1, 2),
                new Chariot(country));
        final Board board = new Board(map);

        // when
        final boolean actual1 = cannonPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Cannon은 목적지까지 가던 중 포를 만나면 false를 반환한다.")
    @Test
    void cannon3() {
        // given
        final Piece cannonPiece = new Cannon(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition destJanggiPosition = new JanggiPosition(1, 3);
        final Country country = Country.HAN;
        final Map<JanggiPosition, Piece> map = Map.of(new JanggiPosition(1, 2),
                new Cannon(country));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, destJanggiPosition, board);

        // then
        assertThat(actual2).isFalse();
    }

    @DisplayName("Cannon은 목적지까지 가던 중 2개 이상의 기물을 만나면 false를 반환한다.")
    @Test
    void cannon4() {
        // given
        final Piece cannonPiece = new Cannon(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 4);
        final Country country = Country.HAN;
        final Map<JanggiPosition, Piece> map = Map.of(new JanggiPosition(1, 2),
                new Chariot(country), new JanggiPosition(1, 3),
                new Chariot(country));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        assertThat(actual2).isFalse();
    }

    @DisplayName("포는 포를 죽일 수 없다.")
    @Test
    void cannon5() {
        // given
        final Piece cannonPiece = new Cannon(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 4);
        final Country country = Country.HAN;
        final Map<JanggiPosition, Piece> map = Map.of(new JanggiPosition(1, 4),
                new Cannon(Country.CHO), new JanggiPosition(1, 3),
                new Chariot(country));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        assertThat(actual2).isFalse();
    }

    @DisplayName("출발지와 목적지가 일직선상에 존재하지 않으면서, 궁성 모서리에서 모서리로 이동하는 경우 궁성 중심에 포가 아닌 기물이 존재해야 한다.")
    @ParameterizedTest
    @MethodSource
    void cannon6(final JanggiPosition now, final JanggiPosition dest, final Piece centerPiece, final boolean expected) {
        // given
        final Piece piece = new Cannon(Country.CHO);
        final Board board = new Board(Map.of(new JanggiPosition(2, 5), centerPiece));

        // when
        final boolean actual = piece.isAbleToMove(now, dest, board);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> cannon6() {
        return Stream.of(
                Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 6), new Chariot(Country.CHO), true),
                Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 7), new Cannon(Country.CHO), false)
        );
    }
}
