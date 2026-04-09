package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private final Board board = new Board();

    @Test
    void 특정_좌표의_기물을_찾는다() {
        Position position = new Position(1, 4);

        Piece piece = board.findByPosition(position);
        Team team = piece.findTeam();

        assertThat(team).isEqualTo(Team.HAN);
        assertThat(piece).isInstanceOf(King.class);
    }

    @Test
    void 특정_좌표가_빈칸인지_확인한다() {
        Position position = new Position(1, 0);
        assertTrue(board.isEmptyPosition(position));
    }

    @Test
    void 이동시_잡힌_기물을_반환한다() {
        Board board = Board.of(Map.of(
                new Position(0, 0), new Tank(Team.HAN),
                new Position(0, 3), new Soldier(Team.CHO)
        ));

        Piece captured = board.move(
                new Position(0, 0), new Position(0, 3), Team.HAN);

        assertThat(captured).isInstanceOf(Soldier.class);
        assertThat(captured.findTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 빈칸으로_이동시_빈칸을_반환한다() {
        Board board = Board.of(Map.of(
                new Position(0, 0), new Tank(Team.HAN)
        ));

        Piece captured = board.move(
                new Position(0, 0), new Position(0, 3), Team.HAN);

        assertThat(captured.isEmpty()).isTrue();
    }

    //점수 계산
    @Test
    void 특정_진영의_점수를_계산한다() {
        // 한나라: 차(13) + 졸(2) = 15
        Board board = Board.of(Map.of(
                new Position(0, 0), new Tank(Team.HAN),
                new Position(3, 4), new Soldier(Team.HAN),
                new Position(9, 0), new Cannon(Team.CHO)
        ));

        assertThat(board.calculateScore(Team.HAN)).isEqualTo(15);
        assertThat(board.calculateScore(Team.CHO)).isEqualTo(7);
    }
    
}
