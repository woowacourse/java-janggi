package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.domain.strategy.InitializeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HanTurnTest {

    InitializeStrategy strategy;
    Board board;

    @BeforeEach
    public void setUp() {
        strategy = new BasicPlacementStrategy();
        board = new Board(strategy);
    }

    @Test
    void 한나라_턴_종료_정상_테스트() {
        Position from = new Position(2, 6);
        Position to = new Position(2, 5);

        GameState state = new HanTurn(board);
        GameState actual = state.move(from, to);

        assertInstanceOf(ChoTurn.class, actual);
    }

    @Test
    void 다른_진영의_기물을_움직이는_예외_테스트() {
        Position from = new Position(2, 3);
        Position to = new Position(2, 4);

        GameState state = new HanTurn(board);

        assertThatThrownBy(() -> state.move(from, to))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 한_기물_점수_테스트() {
        GameState state = new HanTurn(board);

        double actual = state.calculatePieceScore();

        double expected = 73.5;

        assertThat(actual).isEqualTo(expected);
    }
}
