package domain.strategy;

import domain.board.Board;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

class ForwardStepStrategyTest {

    @Test
    @DisplayName("한나라 진영에서는 하/좌/우 한칸씩 이동 가능하다.")
    void moveStrategy_HANSide_Test() {
        // given
        Position start = new Position(3, 4);
        ForwardStepStrategy strategy = new ForwardStepStrategy();

        Board board = mock(Board.class);
        Piece piece = new Pawn(Side.HAN);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).containsOnly(
                new Position(4, 4),
                new Position(3, 5),
                new Position(3, 3)
        );
    }

    @Test
    @DisplayName("한나라 진영에서는 하/좌/우 한칸씩 이동 가능하다.")
    void moveStrategy_CHUSide_Test() {
        // given
        Position start = new Position(3, 4);
        ForwardStepStrategy strategy = new ForwardStepStrategy();

        Board board = mock(Board.class);
        Piece piece = new Pawn(Side.CHU);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).containsOnly(
                new Position(2, 4),
                new Position(3, 5),
                new Position(3, 3)
        );
    }
}
