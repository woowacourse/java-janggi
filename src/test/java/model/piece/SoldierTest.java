package model.piece;

import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#한나라_병_이동_가능한_위치")
    void 한나라_병은_전진과_좌우로_이동할_수_있다(Position current, Position next) {
        // given
        Piece soldier = new Soldier(Team.HAN);
        // when
        boolean canMove = soldier.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#한나라_병_이동_불가능한_위치")
    void 한나라_병은_후퇴와_두칸_이동을_할_수_없다(Position current, Position next) {
        // given
        Piece soldier = new Soldier(Team.HAN);
        // when
        boolean canMove = soldier.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#초나라_졸_이동_가능한_위치")
    void 초나라_졸은_전진과_좌우로_이동할_수_있다(Position current, Position next) {
        // given
        Piece soldier = new Soldier(Team.CHO);
        // when
        boolean canMove = soldier.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#초나라_졸_이동_불가능한_위치")
    void 초나라_졸은_후퇴와_두칸_이동을_할_수_없다(Position current, Position next) {
        // given
        Piece soldier = new Soldier(Team.CHO);
        // when
        boolean canMove = soldier.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }
}