package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
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
    void 차는_궁성_안에서_대각선_이동한다() {
        Piece cha = new Chariot(Team.CHO);
        assertThat(cha.canMove(new Position(1, 4), new Position(2, 5))).isTrue();
        assertThat(cha.canMove(new Position(2, 5), new Position(3, 6))).isTrue();
        assertThat(cha.canMove(new Position(1, 4), new Position(3, 6))).isTrue();
    }

    @Test
    void 차는_궁성_밖에서_대각선_이동하지_못한다() {
        Piece cha = new Chariot(Team.CHO);
        assertThat(cha.canMove(new Position(5, 5), new Position(6, 6))).isFalse();
    }

    @Test
    void 차는_궁성_대각선_1칸_이동_경로는_비어있다() {
        Chariot cha = new Chariot(Team.CHO);
        assertThat(cha.searchRoute(new Position(1, 4), new Position(2, 5))).isEmpty();
    }

    @Test
    void 차는_궁성_대각선_2칸_이동_경로는_중앙이다() {
        Chariot cha = new Chariot(Team.CHO);
        List<Position> route = cha.searchRoute(new Position(1, 4), new Position(3, 6));
        assertThat(route).containsExactly(new Position(2, 5));
    }

    @Test
    void 직선_방향_경로를_출력_한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(4), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(2), new Column(3)),
                        new Position(new Row(3), new Column(3))));

        assertThat(chariot.searchRoute(src, dest)).isEqualTo(routes);
    }
}
