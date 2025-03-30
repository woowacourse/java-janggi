package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    Board board;

    @BeforeEach
    void makeBoard() {
        Piece general = new General(new Position(1, 1), RED);
        Piece guard = new Guard(new Position(1, 2), RED);
        Piece solider1 = new Soldier(new Position(1, 3), RED);
        Piece solider2 = new Soldier(new Position(8, 9), BLUE);
        Piece cannon = new Cannon(new Position(5, 5), BLUE);

        Pieces pieces = new Pieces(List.of(general, guard, solider2, solider1, cannon));
        board = new Board(pieces, BLUE);
    }

    @DisplayName("왕이 존재하지 않을 시, 게임이 종료됨을 확인한다.")
    @Test
    void isGameEndTestWithoutGeneral() {
        assertThat(board.isGameEnd(RED)).isTrue();
    }

    @DisplayName("30턴이 진행됐을 때, 게임이 종료됨을 확인한다.")
    @Test
    void isGameEndTestWithGeneral() {
        Piece general1 = new General(new Position(1, 1), RED);
        Piece guard = new Guard(new Position(1, 2), RED);
        Piece solider1 = new Soldier(new Position(1, 3), RED);
        Piece solider2 = new Soldier(new Position(8, 9), BLUE);
        Piece cannon = new Cannon(new Position(5, 5), BLUE);
        Piece general2 = new General(new Position(8, 8), BLUE);

        Pieces pieces = new Pieces(List.of(general1, guard, solider2, solider1, cannon, general2));
        board = new Board(pieces, BLUE);
        for (int i = 0; i < 29; i++) {
            board.changeTurn();
        }
        assertThat(board.isGameEnd(RED)).isTrue();
    }

    @DisplayName("왕이 존재하지 않을 시, 게임의 승리자를 반환한다.")
    @Test
    void getWinnerTestWithoutGeneral() {
        assertThat(board.getWinner(RED)).isEqualTo(RED);
    }

    @DisplayName("30턴이 지났을 시, 게임의 승리자를 반환한다.")
    @Test
    void getWinnerTestWithGeneral() {
        Piece general1 = new General(new Position(1, 1), RED); // 0
        Piece guard = new Guard(new Position(1, 2), RED); // 3
        Piece solider1 = new Soldier(new Position(1, 3), RED); // 2
        Piece solider2 = new Soldier(new Position(8, 9), BLUE); // 2
        Piece cannon = new Cannon(new Position(5, 5), BLUE); // 7
        Piece general2 = new General(new Position(8, 8), BLUE); //0

        Pieces pieces = new Pieces(List.of(general1, guard, solider2, solider1, cannon, general2));
        board = new Board(pieces, BLUE);
        for (int i = 0; i < 29; i++) {
            board.changeTurn();
        }

        assertThat(board.getWinner(RED)).isEqualTo(BLUE);
    }
}
