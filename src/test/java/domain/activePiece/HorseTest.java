package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.ActivePiece;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.position.Position;
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
        Position source = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position destination = new Position(5, 4);
        List<Position> routes = List.of(mid);

        assertThat(horse.calculateRoute(source, destination)).isEqualTo(routes);
    }

    @Test
    void 마_열_방향_경로를_계산한다() {
        ActivePiece horse = new Horse(Team.HAN);
        Position source = new Position(3, 3);
        Position mid = new Position(3, 4);
        Position destination = new Position(4, 5);
        List<Position> routes = List.of(mid);

        assertThat(horse.calculateRoute(source, destination)).isEqualTo(routes);
    }
}
