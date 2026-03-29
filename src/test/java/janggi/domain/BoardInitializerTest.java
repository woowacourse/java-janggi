package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import janggi.domain.piece.Team;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

class BoardInitializerTest {
    private List<List<Piece>> board = BoardInitializer.createBoard();

    @Test
    void 보드_크기는_10개의_행과_9개의_열로_이루어진다() {
        int rowLength = board.size();
        int colLength = board.get(0).size();

        assertThat(rowLength).isEqualTo(10);
        assertThat(colLength).isEqualTo(9);
    }

    @Test
    void 장기판은_32개의_기물과_58개의_빈칸이_있다() {
        int emptyPositionCount = Math.toIntExact(board.stream()
                .flatMap(Collection::stream)
                .filter(Piece::isEmpty)
                .count());

        int pieceCount = Math.toIntExact(board.stream()
                .flatMap(Collection::stream)
                .filter((piece) -> !piece.isEmpty())
                .count());

        assertThat(emptyPositionCount).isEqualTo(58);
        assertThat(pieceCount).isEqualTo(32);
    }

    @Test
    void 한나라_기물_개수가_올바르다() {
        assertTeamPieceCount(Team.HAN, Soldier.class, 5);
        assertTeamPieceCount(Team.HAN, Cannon.class, 2);
        assertTeamPieceCount(Team.HAN, King.class, 1);
        assertTeamPieceCount(Team.HAN, Tank.class, 2);
        assertTeamPieceCount(Team.HAN, Horse.class, 2);
        assertTeamPieceCount(Team.HAN, Elephant.class, 2);
        assertTeamPieceCount(Team.HAN, Advisor.class, 2);
    }

    @Test
    void 초나라_기물_개수가_올바르다() {
        assertTeamPieceCount(Team.CHO, Soldier.class, 5);
        assertTeamPieceCount(Team.CHO, Cannon.class, 2);
        assertTeamPieceCount(Team.CHO, King.class, 1);
        assertTeamPieceCount(Team.CHO, Tank.class, 2);
        assertTeamPieceCount(Team.CHO, Horse.class, 2);
        assertTeamPieceCount(Team.CHO, Elephant.class, 2);
        assertTeamPieceCount(Team.CHO, Advisor.class, 2);
    }

    private void assertTeamPieceCount(Team team, Class<? extends Piece> pieceClass, int expected) {
        long count = board.stream()
                .flatMap(Collection::stream)
                .filter(piece -> piece.findTeam() == team)
                .filter(pieceClass::isInstance)
                .count();

        assertThat(count).isEqualTo(expected);
    }

}
