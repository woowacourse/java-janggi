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

class SoldierTest {

    @DisplayName("병이 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(3, 3);
        Soldier soldier = new Soldier();
        Piece startingPiece = new Piece(Side.HAN, soldier);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(new HashMap<>(startingPieces));

        // when
        Set<Position> actual = soldier.generateAvailableMovePositions(board, Side.HAN, startingPosition);
        Set<Position> expected = Set.of(
                Position.of(3, 2),
                Position.of(4, 3),
                Position.of(3, 4)
        );

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }
}
