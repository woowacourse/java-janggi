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

public class HorseTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#마_이동_불가능한_위치")
    void 마는_이동_규칙에_맞지_않으면_이동할_수_없다(Position current, Position next) {
        // given
        Piece horse = new Horse(Team.HAN);

        // when & then
        assertThatThrownBy(() -> horse.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePathFixture#마_이동_경로_테스트_데이터")
    void 말에_대한_다음_위치까지의_이동_경로를_반환한다(Position current, Position next, List<Position> expectedPath) {
        // given
        Piece chariot = new Horse(Team.HAN);

        // when
        List<Position> path = chariot.pathTo(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 마는_이동_경로에_기물이_있으면_예외가_발생한다() {
        // given
        Piece horse = new Horse(Team.HAN);
        List<Piece> obstacles = List.of(FakePiece.createFake(Team.CHO));

        // when & then
        assertThatThrownBy(() -> horse.validatePathCondition(obstacles))
                .isInstanceOf(IllegalArgumentException.class);
    }
}