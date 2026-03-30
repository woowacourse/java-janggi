package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private final Board board = new Board();

    @Test
    void 특정_좌표의_기물을_찾는다() {
        Position position = new Position(1, 4);

        Piece piece = board.findByPosition(position);
        Team team = piece.findTeam();
        PieceType pieceType = piece.pieceType();

        assertThat(team).isEqualTo(Team.HAN);
        assertThat(pieceType).isEqualTo(PieceType.KING);
    }

    @Test
    void 특정_좌표가_빈칸인지_확인한다() {
        Position position = new Position(1, 0);
        assertTrue(board.isEmptyPosition(position));
    }

}
