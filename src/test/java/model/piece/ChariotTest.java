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

public class ChariotTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#사간방_대각선_이동_방향_케이스")
    void 차는_대각선_또는_제자리로_이동할_수_없다(Position current, Position next) {
        // given
        Piece chariot = new Chariot(Team.HAN);

        // when & then
        assertThatThrownBy(() -> chariot.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePathFixture#사방위_이동_경로_테스트_데이터")
    void 차에_대한_다음_위치까지의_이동_경로를_반환한다(Position current, Position next, List<Position> expectedPath) {
        // given
        Piece chariot = new Chariot(Team.HAN);

        // when
        List<Position> path = chariot.pathTo(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 차는_이동_경로에_기물이_있으면_예외가_발생한다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        List<Piece> obstacles = List.of(FakePiece.createFake(Team.CHO));

        // when & then
        assertThatThrownBy(() -> chariot.validatePathCondition(obstacles))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PalaceMovePositionFixture#차_포_궁성_대각선_이동_경로")
    void 차는_궁성_교차점에서_대각선으로_이동_경로를_구할_수_있다(Team team, Position current, Position next, List<Position> expectedPath) {
        // given
        Piece cannon = new Chariot(team);

        // when & then
        List<Position> path = cannon.pathTo(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PalaceMovePositionFixture#차_포_궁성_대각선_이동_불가능한_위치")
    void 차는_궁성_교차점이_아닌_곳에서_대각선으로_이동할_수_없다(Team team, Position current, Position next) {
        // given
        Piece cannon = new Chariot(team);

        // when & then
        assertThatThrownBy(() -> cannon.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }
}