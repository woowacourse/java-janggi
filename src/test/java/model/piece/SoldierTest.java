package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import model.testdouble.FakePiece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierTest {

    @ParameterizedTest(name = "{0}나라 졸 일 때, {1}에서 {2}로 이동할 수 없다.")
    @MethodSource("model.fixture.PieceMovePositionFixture#졸_병_이동_불가능한_위치")
    void 졸_병_이동_실패_테스트(Team team, Position current, Position next) {
        // given
        Piece soldier = new Soldier(team);

        // when & then
        assertThatThrownBy(() -> soldier.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "{0}나라 졸 일 때, {1}에서 {2}로 이동 시 무조건 경로가 비어있다.")
    @MethodSource("model.fixture.PieceMovePathFixture#졸_병_이동_경로_테스트_데이터")
    void 졸_병_경로_테스트(Team team, Position current, Position next) {
        // given
        Piece soldier = new Soldier(team);

        // when
        List<Position> path = soldier.pathTo(current, next);

        // then
        assertThat(path).isEmpty();
    }

    @Test
    void 졸은_이동_경로에_기물이_있으면_예외가_발생한다() {
        // given
        Piece soldier = new Soldier(Team.HAN);
        List<Piece> obstacles = List.of(FakePiece.createFake(Team.CHO));

        // when & then
        assertThatThrownBy(() -> soldier.validatePathCondition(obstacles))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PalaceMovePositionFixture#졸_병_궁성_이동_불가능한_위치")
    void 졸은_궁성_교차점이_아닌_곳에서_대각선으로_이동할_수_없다(Team team, Position current, Position next) {
        // given
        Piece cannon = new Soldier(team);

        // when & then
        assertThatThrownBy(() -> cannon.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }
}