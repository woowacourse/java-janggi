package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.Elephant;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 상은_직선1칸_대각선_2칸으로_이동할_수_있다() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5, 5), new Position(7, 8))).isTrue();
    }

    @Test
    void 직선_1칸_대각선_2칸_이동이_아니면_거짓() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 상_직선_1칸_대각선_2칸_경로_출력() {
        Piece elephant = new Elephant(Team.HAN);
        Position src = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position mid2 = new Position(5, 4);
        Position dest = new Position(6, 5);
        List<Position> routes = List.of(mid, mid2);

        assertThat(elephant.searchRoute(src, dest)).isEqualTo(routes);
    }
}
