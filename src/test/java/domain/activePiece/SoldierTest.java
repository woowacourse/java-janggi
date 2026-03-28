package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.ActivePiece;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Soldier;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import domain.piece.Piece;
import domain.game.Team;
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
    void 한_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Elephant(Team.HAN);
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
    void 초_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Elephant(Team.CHO);
        assertThat(soldier.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 병_정상_경로_출력_한다() {
        ActivePiece guard = new Guard(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(1), new Column(3)), new Position(new Row(2), new Column(3))));

        assertThat(guard.searchRoute(src, dest)).isEqualTo(routes);
    }

    @Test
    void 병_비정상_경로_출력_한다() {
        ActivePiece guard = new Guard(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(3), new Column(3))));

        assertThat(guard.searchRoute(src, dest)).isNotEqualTo(routes);
    }

}
