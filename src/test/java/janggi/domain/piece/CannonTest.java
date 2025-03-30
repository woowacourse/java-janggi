package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {

    @DisplayName("포는 상대 포를 잡을 수 없다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(3, 8);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Cannon(Side.CHO),
                Position.of(3, 5), new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("포는 기물을 넘어야 같은 팀 기물의 전까지 이동할 수 있다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(7, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(4, 3), new Soldier(Side.HAN),
                Position.of(8, 3), new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("포는 기물을 넘어야 상대 기물이 있는 위치까지 이동할 수 있다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(8, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(4, 3), new Soldier(Side.HAN),
                endPosition, new Soldier(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("포는 다른 포를 넘을 수 없다.")
    @Test
    void test4() {
        // given
        Position startingPosition = Position.of(7, 5);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(4, 5);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(5, 5), new Cannon(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("포는 넘을 기물이 없으면 이동할 수 없다.")
    @Test
    void test5() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(3, 8);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("포는 궁성 영역 안에서 대각선으로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"3,4,1,6", "1,4,3,6", "8,4,10,6", "8,6,10,4"})
    void test6(int startingRow, int startingColumn, int endRow, int endColumn) {
        // given
        Position startingPosition = Position.of(startingRow, startingColumn);
        Piece startingPiece = new Cannon(Side.HAN);
        Position endPosition = Position.of(endRow, endColumn);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(2, 5), new Soldier(Side.HAN),
                Position.of(9, 5), new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("포는 7점으로 계산한다.")
    @Test
    void test7() {
        // given
        Side side = Side.CHO;
        Piece piece = new Cannon(side);

        // when
        double actual = piece.getPoints();
        double expected = 7;

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
