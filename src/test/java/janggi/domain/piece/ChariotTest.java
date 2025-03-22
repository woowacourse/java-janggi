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

class ChariotTest {

    @DisplayName("차가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Piece startingPiece = new Piece(Side.HAN, chariot);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(6, 1), new Piece(Side.CHO, new Soldier()),
                Position.of(10, 2), new Piece(Side.CHO, new Soldier())
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, startingPosition);
        Set<Position> expected = Set.of(
                Position.of(9, 1),
                Position.of(8, 1),
                Position.of(7, 1),
                Position.of(6, 1),
                Position.of(10, 2)
        );

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }

    @DisplayName("차 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Piece startingPiece = new Piece(Side.HAN, chariot);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(9, 1), new Piece(Side.HAN, new Soldier()),
                Position.of(10, 2), new Piece(Side.HAN, new Soldier())
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, startingPosition);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("차 앞에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Piece startingPiece = new Piece(Side.HAN, chariot);
        Position endPosition = Position.of(7, 1);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Piece(Side.CHO, new Soldier())
        );
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, startingPosition);

        // then
        assertThat(actual).contains(endPosition);
    }
}
