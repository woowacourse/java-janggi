package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {

    @DisplayName("포가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(8, 3), new Soldier(Side.HAN),
                Position.of(3, 7), new Soldier(Side.HAN)
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = startingPiece.generateAvailableMovePositions(board, startingPosition);
        Set<Position> expected = Set.of(
                Position.of(9, 3),
                Position.of(10, 3),
                Position.of(3, 8),
                Position.of(3, 9)
        );

        // then
        assertThat(actual).hasSameElementsAs(expected);
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
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = startingPiece.generateAvailableMovePositions(board, startingPosition);

        // then
        assertThat(actual).contains(endPosition);
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
                Position.of(8, 3), new Soldier(Side.CHO)
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = startingPiece.generateAvailableMovePositions(board, startingPosition);

        // then
        assertThat(actual).contains(endPosition);
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
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = startingPiece.generateAvailableMovePositions(board, startingPosition);

        // then
        assertThat(actual).doesNotContain(endPosition);
    }

    @DisplayName("포는 넘을 기물이 없으면 이동할 수 없다.")
    @Test
    void test5() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Cannon(Side.HAN);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = startingPiece.generateAvailableMovePositions(board, startingPosition);

        // then
        assertThat(actual).isEmpty();
    }
}
