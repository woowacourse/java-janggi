package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
}
