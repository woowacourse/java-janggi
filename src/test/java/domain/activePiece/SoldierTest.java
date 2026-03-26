package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    void 한_진영에서_왼쪽_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5,5), new Position(5,4))).isTrue();
    }

    @Test
    void 한_진영에서_오른쪽_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5,5), new Position(5,6))).isTrue();
    }

    @Test
    void 한_진영에서_전진_이동() {
        Piece soldier = new Soldier(Team.HAN);
        assertThat(soldier.canMove(new Position(5,5), new Position(4,5))).isTrue();
    }

    @Test
    void 한_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Elephant(Team.HAN);
        assertThat(soldier.canMove(new Position(5,5), new Position(8,8))).isFalse();
    }

    @Test
    void 초_진영에서_왼쪽_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5,5), new Position(5,4))).isTrue();
    }

    @Test
    void 초_진영에서_오른쪽_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5,5), new Position(5,6))).isTrue();
    }

    @Test
    void 초_진영에서_전진_이동() {
        Piece soldier = new Soldier(Team.CHO);
        assertThat(soldier.canMove(new Position(5,5), new Position(6,5))).isTrue();
    }

    @Test
    void 초_진영에서_정상_범위가_아니면_거짓() {
        Piece soldier = new Elephant(Team.CHO);
        assertThat(soldier.canMove(new Position(5,5), new Position(8,8))).isFalse();
    }

}
