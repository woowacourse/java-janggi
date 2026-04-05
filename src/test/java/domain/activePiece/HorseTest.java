package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    void 마는_직선1칸_대각선_1칸으로_이동할_수_있다() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5, 5), new Position(6, 7))).isTrue();
    }

    @Test
    void 마의_이동_범위가_직선1칸_대각선_1칸이_아니면_거짓이다() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 마_직선1칸_대각선_1칸_경로_출력() {
        Piece horse = new Horse(Team.HAN);
        Position src = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position dest = new Position(5, 4);
        List<Position> routes = List.of(mid);

        assertThat(horse.searchRoute(src, dest)).isEqualTo(routes);
    }
}
