package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Soldier;
import domain.state.Finished;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameTest {
    Map<Position, Piece> board = new HashMap<>();
    JanggiGame janggiGame;

    @BeforeEach
    void setUp() {
        board.put(new Position(Row.ONE, Column.ONE), new General(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.TWO), new Chariot(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.THREE), new Cannon(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.FOUR), new Horse(PieceColor.BLUE));
        board.put(new Position(Row.ONE, Column.FIVE), new Elephant(PieceColor.BLUE));
        board.put(new Position(Row.ONE, Column.SIX), new Guard(PieceColor.BLUE));
        board.put(new Position(Row.ONE, Column.SEVEN), new Soldier(PieceColor.BLUE));
        Board gameBoard = new Board(board);
        janggiGame = new JanggiGame(new Finished(gameBoard));
    }

    @Test
    void 한나라의_점수를_계산() {
        double redTeamScore = janggiGame.getRedTeamScore();

        assertThat(redTeamScore).isEqualTo(21.5);
    }

    @Test
    void 초나라의_점수를_계산() {
        double blueTeamScore = janggiGame.getBlueTeamScore();

        assertThat(blueTeamScore).isEqualTo(13);
    }

    @Test
    void 궁이_잡은팀이_승리() {
        PieceColor winner = janggiGame.getWinner();

        assertThat(winner).isEqualTo(PieceColor.RED);
    }
}
