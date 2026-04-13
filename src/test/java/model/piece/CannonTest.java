package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#사간방_대각선_이동_방향_케이스")
    void 포는_대각선이나_제자리로_이동할_수_없다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);

        // when & then
        assertThatThrownBy(() -> cannon.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePathFixture#사방위_이동_경로_테스트_데이터")
    void 포에_대한_다음_위치까지의_이동_경로를_반환한다(Position current, Position next, List<Position> expectedPath) {
        // given
        Piece chariot = new Cannon(Team.HAN);

        // when
        List<Position> path = chariot.pathTo(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 포가_넘어가는_다리에_포가_있으면_예외가_발생한다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        Piece hurdleCannon = new Cannon(Team.CHO); // 다리가 포인 경우
        List<Piece> piecesOnPath = List.of(hurdleCannon);

        // when & then
        assertThatThrownBy(() -> cannon.validatePathCondition(piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_넘어가는_다리가_없으면_예외가_발생한다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        List<Piece> emptyPath = List.of(); // 다리가 없는 경우

        // when & then
        assertThatThrownBy(() -> cannon.validatePathCondition(emptyPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_넘어가는_다리가_두_개_이상이면_예외가_발생한다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        List<Piece> manyHurdles = List.of(
                new Cannon(Team.CHO),
                new Cannon(Team.CHO)
        );

        // when & then
        assertThatThrownBy(() -> cannon.validatePathCondition(manyHurdles))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_정상적으로_하나의_기물을_넘어가는_경우_예외가_발생하지_않는다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        Piece hurdle = new Soldier(Team.CHO);
        List<Piece> piecesOnPath = List.of(hurdle);

        // when & then
        assertThatCode(() -> cannon.validatePathCondition(piecesOnPath))
                .doesNotThrowAnyException();
    }

    @Test
    void 포가_상대_포를_잡으려_하면_예외가_발생한다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        Piece targetCannon = new Cannon(Team.CHO);

        // when & then
        assertThatThrownBy(() -> cannon.validateTarget(targetCannon))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_상대_포가_아닌_기물을_잡으려_하면_예외가_발생하지_않는다() {
        // given
        Piece cannon = new Cannon(Team.HAN);
        Piece target = new Soldier(Team.CHO);

        // when & then
        assertThatCode(() -> cannon.validateTarget(target))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PalaceMovePositionFixture#차_포_궁성_대각선_이동_경로")
    void 포는_궁성_교차점에서_대각선으로_이동_경로를_구할_수_있다(Team team, Position current, Position next, List<Position> expectedPath) {
        // given
        Piece cannon = new Cannon(team);

        // when & then
        List<Position> path = cannon.pathTo(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PalaceMovePositionFixture#차_포_궁성_대각선_이동_불가능한_위치")
    void 포는_궁성_교차점이_아닌_곳에서_대각선으로_이동할_수_없다(Team team, Position current, Position next) {
        // given
        Piece cannon = new Cannon(team);

        // when & then
        assertThatThrownBy(() -> cannon.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }
}