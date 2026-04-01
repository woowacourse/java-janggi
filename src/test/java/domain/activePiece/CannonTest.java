package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    void 포는_직선_운동한다() {
        Piece cannon = new Cannon(Team.HAN);
        assertThat(cannon.canMove(new Position(1, 4), new Position(1, 8))).isTrue();

    }

    @Test
    void 포는_직선이_아닌_방향으로_가지_못한다() {
        Piece cannon = new Cannon(Team.HAN);
        assertThat(cannon.canMove(new Position(5, 5), new Position(3, 3))).isFalse();

    }

    @Test
    void 직선_방향_경로를_출력_한다() {
        Cannon cannon = new Cannon(Team.HAN);
        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(4), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(2), new Column(3)),
                        new Position(new Row(3), new Column(3))));

        assertThat(cannon.searchRoute(src, dest)).isEqualTo(routes);
    }
}
