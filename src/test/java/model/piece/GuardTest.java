package model.piece;

import model.coordinate.Position;
import model.game.Team;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class GuardTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#궁성_중심에서_상하좌우_이동")
    void 사는_궁성_내에서_상하좌우로_한칸_이동할_수_있다(Position current, Position next) {
        // given
        Piece guard = new Guard(Team.HAN);
        // when
        boolean canMove = guard.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#궁성_대각선_위치에서_대각선_이동")
    void 사는_궁성_대각선_위치에서_대각선으로_한칸_이동할_수_있다(Position current, Position next) {
        // given
        Piece guard = new Guard(Team.HAN);
        // when
        boolean canMove = guard.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#궁성_비대각선_위치에서_대각선_이동_불가")
    void 사는_궁성_대각선_위치가_아니면_대각선으로_이동할_수_없다(Position current, Position next) {
        // given
        Piece guard = new Guard(Team.HAN);
        // when
        boolean canMove = guard.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#궁성_밖으로_이동_불가")
    void 사는_궁성_밖으로_이동할_수_없다(Position current, Position next) {
        // given
        Piece guard = new Guard(Team.HAN);
        // when
        boolean canMove = guard.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }
}
