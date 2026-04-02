package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.ActivePiece;
import domain.piece.Elephant;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 정상_범위_입력() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5, 5), new Position(7, 8))).isTrue();
    }

    @Test
    void 정상_범위가_아니면_거짓() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 상_정상_경로_출력() {
        ActivePiece elephant = new Elephant(Team.HAN);
        Position source = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position mid2 = new Position(5, 4);
        Position destination = new Position(6, 5);
        List<Position> routes = List.of(mid, mid2);

        assertThat(elephant.searchRoute(source, destination)).isEqualTo(routes);
    }

    @Test
    void 상_비정상_경로_출력() {
        ActivePiece elephant = new Elephant(Team.HAN);
        Position source = new Position(3, 3);
        Position mid = new Position(5, 6);
        Position mid2 = new Position(7, 7);
        Position destination = new Position(8, 9);
        List<Position> routes = List.of(mid, mid2, destination);

        assertThat(elephant.searchRoute(source, destination)).isNotEqualTo(routes);
    }
}
