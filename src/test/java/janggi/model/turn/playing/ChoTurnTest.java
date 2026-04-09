package janggi.model.turn.playing;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChoTurnTest {

    Board board;

    @BeforeEach
    void beforeEach() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(
                new Position(Row.SEVEN, Column.FIVE),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.SIX, Column.SEVEN),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.SIX, Column.FIVE),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.EIGHT, Column.SEVEN),
                new Ma(Team.HAN)
        );
        board.put(
                new Position(Row.ONE, Column.ONE),
                new Ma(Team.HAN)
        );

        this.board = PlayingBoard.of(board);
    }

    @DisplayName("한나라 턴을 반환한다.")
    @Test
    void play() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.SEVEN);

        //when & then
        assertThat(new ChoTurn(board).play(from, to))
                .isInstanceOf(HanTurn.class);
    }

    @DisplayName("게임이 아직 끝나지 않았다.")
    @Test
    void isGameOver() {
        assertThat(new ChoTurn(PlayingBoard.of(new HashMap<>())).isGameOver())
                .isFalse();
    }
}
