package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.ActivePiece;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    void 한_진영에서_왼쪽_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5, 5), new Position(5, 4))).isTrue();
    }

    @Test
    void 한_진영에서_오른쪽_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 한_진영에서_전진_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5, 5), new Position(4, 5))).isTrue();
    }

    @Test
    void 한_진영에서_후진_불가() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5, 5), new Position(6, 5))).isFalse();
    }

    @Test
    void 한_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 초_진영에서_왼쪽_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(5, 4))).isTrue();
    }

    @Test
    void 초_진영에서_오른쪽_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 초_진영에서_전진_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(6, 5))).isTrue();
    }

    @Test
    void 초_진영에서_후진_불가() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(4, 5))).isFalse();
    }

    @Test
    void 초_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 병은_빈_경로_출력_한다() {
        ActivePiece soldier = new Soldier(Team.HAN);

        Position source = new Position(new Row(5), new Column(5));
        Position destination = new Position(new Row(4), new Column(5));
        List<Position> routes = new ArrayList<>();

        assertThat(soldier.calculateRoute(source, destination)).isEqualTo(routes);
    }

    @Test
    void 병_비정상_경로_출력_한다() {
        ActivePiece soldier = new Soldier(Team.HAN);

        Position source = new Position(new Row(5), new Column(5));
        Position destination = new Position(new Row(4), new Column(5));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(3), new Column(5))));

        assertThat(soldier.calculateRoute(source, destination)).isNotEqualTo(routes);
    }

}
