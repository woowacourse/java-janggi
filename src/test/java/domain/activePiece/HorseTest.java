package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    void 정상_범위_입력() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5, 5), new Position(6, 7))).isTrue();
    }

    @Test
    void 정상_범위가_아니면_거짓() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 마_정상_경로_출력() {
        ActivePiece horse = new Horse(Team.HAN);
        Position src = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position dest = new Position(5, 4);
        List<Position> routes = List.of(src, mid, dest);

        assertThat(horse.searchRoute(src, dest)).isEqualTo(routes);
    }

    @Test
    void 마_비정상_경로_출력() {
        ActivePiece horse = new Horse(Team.HAN);
        Position src = new Position(3, 3);
        Position mid = new Position(6, 3);
        Position dest = new Position(7, 3);
        List<Position> routes = new ArrayList<>(List.of(mid, dest));

        assertThat(horse.searchRoute(src, dest)).isNotEqualTo(routes);
    }
}
