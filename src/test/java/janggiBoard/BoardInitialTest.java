package janggiBoard;

import domain.board.JanggiBoard;
import domain.Position;
import domain.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardInitialTest {

    private JanggiBoard janggiBoard;

    @BeforeEach
    void setUp() {
        janggiBoard = new JanggiBoard(new LinkedHashMap<>());
    }

    @Test
    void 보드_초기화시_90칸이_제대로_생성되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.size()).isEqualTo(90);
    }

    @Test
    void 한나라_차가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(0, 0))).isInstanceOf(Chariot.class);
        assertThat(board.get(new Position(0, 8))).isInstanceOf(Chariot.class);

        assertThat(board.get(new Position(0, 0)).getTeam()).isEqualTo(Team.HAN);
        assertThat(board.get(new Position(0, 8)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_차가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(9, 0))).isInstanceOf(Chariot.class);
        assertThat(board.get(new Position(9, 8))).isInstanceOf(Chariot.class);

        assertThat(board.get(new Position(9, 0)).getTeam()).isEqualTo(Team.CHO);
        assertThat(board.get(new Position(9, 8)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_마가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(0, 1))).isInstanceOf(Horse.class);
        assertThat(board.get(new Position(0, 6))).isInstanceOf(Horse.class);

        assertThat(board.get(new Position(0, 1)).getTeam()).isEqualTo(Team.HAN);
        assertThat(board.get(new Position(0, 6)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_마가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(9, 1))).isInstanceOf(Horse.class);
        assertThat(board.get(new Position(9, 6))).isInstanceOf(Horse.class);

        assertThat(board.get(new Position(9, 1)).getTeam()).isEqualTo(Team.CHO);
        assertThat(board.get(new Position(9, 6)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_상이_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(0, 2))).isInstanceOf(Elephant.class);
        assertThat(board.get(new Position(0, 7))).isInstanceOf(Elephant.class);

        assertThat(board.get(new Position(0, 2)).getTeam()).isEqualTo(Team.HAN);
        assertThat(board.get(new Position(0, 7)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_상이_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(9, 2))).isInstanceOf(Elephant.class);
        assertThat(board.get(new Position(9, 7))).isInstanceOf(Elephant.class);

        assertThat(board.get(new Position(9, 2)).getTeam()).isEqualTo(Team.CHO);
        assertThat(board.get(new Position(9, 7)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_사가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(0, 3))).isInstanceOf(Guard.class);
        assertThat(board.get(new Position(0, 5))).isInstanceOf(Guard.class);

        assertThat(board.get(new Position(0, 3)).getTeam()).isEqualTo(Team.HAN);
        assertThat(board.get(new Position(0, 5)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_사가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(9, 3))).isInstanceOf(Guard.class);
        assertThat(board.get(new Position(9, 5))).isInstanceOf(Guard.class);

        assertThat(board.get(new Position(9, 3)).getTeam()).isEqualTo(Team.CHO);
        assertThat(board.get(new Position(9, 5)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_궁이_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(1, 4))).isInstanceOf(King.class);
        assertThat(board.get(new Position(1, 4)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_궁이_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(8, 4))).isInstanceOf(King.class);
        assertThat(board.get(new Position(8, 4)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_포가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(2, 1))).isInstanceOf(Cannon.class);
        assertThat(board.get(new Position(2, 7))).isInstanceOf(Cannon.class);

        assertThat(board.get(new Position(2, 1)).getTeam()).isEqualTo(Team.HAN);
        assertThat(board.get(new Position(2, 7)).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 초나라_포가_제대로된_위치에_초기화_되었는지_확인한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(board.get(new Position(7, 1))).isInstanceOf(Cannon.class);
        assertThat(board.get(new Position(7, 7))).isInstanceOf(Cannon.class);

        assertThat(board.get(new Position(7, 1)).getTeam()).isEqualTo(Team.CHO);
        assertThat(board.get(new Position(7, 7)).getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라_졸_위치들을_한번에_검증한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(List.of(new Position(3, 0),
                new Position(3, 2),
                new Position(3, 4),
                new Position(3,6),
                new Position(3,8)))
                .allSatisfy(pos -> {
                    assertThat(board.get(pos)).isInstanceOf(Pawn.class);
                    assertThat(board.get(pos).getTeam()).isEqualTo(Team.HAN);
                });
    }

    @Test
    void 초나라_졸_위치들을_한번에_검증한다() {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        assertThat(List.of(new Position(6, 0),
                new Position(6, 2),
                new Position(6, 4),
                new Position(6,6),
                new Position(6,8)))
                .allSatisfy(pos -> {
                    assertThat(board.get(pos)).isInstanceOf(Pawn.class);
                    assertThat(board.get(pos).getTeam()).isEqualTo(Team.CHO);
                });
    }
}
