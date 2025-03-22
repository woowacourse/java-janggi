package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    @DisplayName("상이 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(1, 1);
        Elephant elephant = new Elephant();
        Piece startingPiece = new Piece(Side.HAN, elephant);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = elephant.generateAvailableMovePositions(board, Side.HAN, startingPosition);
        Set<Position> expected = Set.of(
                Position.of(4, 3),
                Position.of(3, 4)
        );

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }

    @DisplayName("상의 최종 목적지에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(1, 1);
        Elephant elephant = new Elephant();
        Piece startingPiece = new Piece(Side.HAN, elephant);
        Position endPosition = Position.of(4, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Piece(Side.HAN, new Soldier())
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = elephant.generateAvailableMovePositions(board, Side.HAN, startingPosition);

        // then
        assertThat(actual).doesNotContain(endPosition);
    }

    @DisplayName("상의 최종 목적지에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(1, 1);
        Elephant elephant = new Elephant();
        Piece startingPiece = new Piece(Side.HAN, elephant);
        Position endPosition = Position.of(4, 3);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Piece(Side.CHO, new Soldier())
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = elephant.generateAvailableMovePositions(board, Side.HAN, startingPosition);

        // then
        assertThat(actual).contains(endPosition);
    }
}
