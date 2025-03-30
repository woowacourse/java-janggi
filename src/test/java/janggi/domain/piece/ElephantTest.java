package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    @DisplayName("상의 이동 경로 중간에 기물이 있다면 움직일 수 없다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(1, 1);
        Piece startingPiece = new Elephant(Side.HAN);
        Position endPosition = Position.of(4, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(3, 2), new Soldier(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("상의 최종 목적지에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(1, 1);
        Piece startingPiece = new Elephant(Side.HAN);
        Position endPosition = Position.of(4, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("상의 최종 목적지에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(1, 1);
        Piece startingPiece = new Elephant(Side.HAN);
        Position endPosition = Position.of(4, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("상은 3점으로 계산한다.")
    @Test
    void test4() {
        // given
        Side side = Side.CHO;
        Piece piece = new Elephant(side);

        // when
        double actual = piece.getPoints();
        double expected = 3;

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
