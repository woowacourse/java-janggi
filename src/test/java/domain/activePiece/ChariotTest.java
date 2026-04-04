package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차는_직선_운동한다() {
        Piece cha = new Chariot(Team.HAN);
        assertThat(cha.canMove(new Position(1, 4), new Position(1, 8))).isTrue();

    }

    @Test
    void 차는_직선이_아닌_방향으로_가지_못한다() {
        Piece cha = new Chariot(Team.HAN);
        assertThat(cha.canMove(new Position(5, 5), new Position(3, 3))).isFalse();

    }

    @Test
    void 같은_열의_행_방향_경로를_계산한다() {
        Piece chariot = new Chariot(Team.HAN);
        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(4), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(2), new Column(3)),
                        new Position(new Row(3), new Column(3))));

        assertThat(chariot.calculateRoute(source, destination)).isEqualTo(routes);
    }

    @Test
    void 같은_행의_열_방향_경로를_계산한다() {
        Piece chariot = new Chariot(Team.HAN);
        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(1), new Column(7));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(1), new Column(4)),
                        new Position(new Row(1), new Column(5)),
                        new Position(new Row(1), new Column(6))));

        assertThat(chariot.calculateRoute(source, destination)).isEqualTo(routes);
    }
}
