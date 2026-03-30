package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BoardFactoryTest {

    @Test
    void 한나라_기물_차림_입력이_4면_한나라는_바깥상_차림이다() {
        Board board = BoardFactory.create("4", "4");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "11", new Chariot(Team.HAN),  Team.HAN);
        assertPiece(boardState, "12", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "13", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "14", new Guard(Team.HAN),    Team.HAN);
        assertPiece(boardState, "16", new Guard(Team.HAN),    Team.HAN);
        assertPiece(boardState, "17", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "18", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "19", new Chariot(Team.HAN),  Team.HAN);
        assertPiece(boardState, "25", new General(Team.HAN),  Team.HAN);
        assertPiece(boardState, "32", new Cannon(Team.HAN),   Team.HAN);
        assertPiece(boardState, "38", new Cannon(Team.HAN),   Team.HAN);
        assertPiece(boardState, "41", new Soldier(Team.HAN),  Team.HAN);
        assertPiece(boardState, "43", new Soldier(Team.HAN),  Team.HAN);
        assertPiece(boardState, "45", new Soldier(Team.HAN),  Team.HAN);
        assertPiece(boardState, "47", new Soldier(Team.HAN),  Team.HAN);
        assertPiece(boardState, "49", new Soldier(Team.HAN),  Team.HAN);
    }

    @Test
    void 초나라_기물_차림_입력이_4면_초나라는_바깥상_차림이다() {
        Board board = BoardFactory.create("4", "4");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "01", new Chariot(Team.CHO),  Team.CHO);
        assertPiece(boardState, "02", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "03", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "04", new Guard(Team.CHO),    Team.CHO);
        assertPiece(boardState, "06", new Guard(Team.CHO),    Team.CHO);
        assertPiece(boardState, "07", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "08", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "09", new Chariot(Team.CHO),  Team.CHO);
        assertPiece(boardState, "95", new General(Team.CHO),  Team.CHO);
        assertPiece(boardState, "82", new Cannon(Team.CHO),   Team.CHO);
        assertPiece(boardState, "88", new Cannon(Team.CHO),   Team.CHO);
        assertPiece(boardState, "71", new Soldier(Team.CHO),  Team.CHO);
        assertPiece(boardState, "73", new Soldier(Team.CHO),  Team.CHO);
        assertPiece(boardState, "75", new Soldier(Team.CHO),  Team.CHO);
        assertPiece(boardState, "77", new Soldier(Team.CHO),  Team.CHO);
        assertPiece(boardState, "79", new Soldier(Team.CHO),  Team.CHO);
    }

    @Test
    void 차림_번호가_1번이면_왼상차림이다() {
        Board board = BoardFactory.create("1", "1");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "12", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "13", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "17", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "18", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "02", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "03", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "07", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "08", new Horse(Team.CHO),    Team.CHO);
    }

    @Test
    void 차림_번호가_2번이면_오른상차림이다() {
        Board board = BoardFactory.create("2", "2");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "12", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "13", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "17", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "18", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "02", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "03", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "07", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "08", new Elephant(Team.CHO), Team.CHO);
    }

    @Test
    void 차림_번호가_3번이면_안상차림이다() {
        Board board = BoardFactory.create("3", "3");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "12", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "13", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "17", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "18", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "02", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "03", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "07", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "08", new Horse(Team.CHO),    Team.CHO);
    }

    @Test
    void 차림_번호가_4번이면_바깥상차림이다() {
        Board board = BoardFactory.create("4", "4");
        Map<Position, Piece> boardState = board.showBoard();

        assertPiece(boardState, "12", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "13", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "17", new Horse(Team.HAN),    Team.HAN);
        assertPiece(boardState, "18", new Elephant(Team.HAN), Team.HAN);
        assertPiece(boardState, "02", new Elephant(Team.CHO), Team.CHO);
        assertPiece(boardState, "03", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "07", new Horse(Team.CHO),    Team.CHO);
        assertPiece(boardState, "08", new Elephant(Team.CHO), Team.CHO);
    }

    private void assertPiece(Map<Position, Piece> board, String pos, Piece expected, Team team) {
        Piece actual = board.get(Position.from(pos));
        assertThat(actual.isSamePiece(expected)).isTrue();
        assertThat(actual.isSame(team)).isTrue();
    }
}
