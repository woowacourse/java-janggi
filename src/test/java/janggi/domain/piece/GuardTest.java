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

class GuardTest {

    @DisplayName("사가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(1, 5);
        Guard guard = new Guard();
        Piece startingPiece = new Piece(Side.HAN, guard);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = guard.generateAvailableMovePositions(board, Side.HAN, startingPosition);
        Set<Position> expected = Set.of(
                Position.of(1, 4),
                Position.of(1, 6),
                Position.of(2, 5),
                Position.of(2, 6),
                Position.of(2, 4)
        );

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }
}
