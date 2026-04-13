package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PalacePieceTest {

    @ParameterizedTest(name = "{0}팀 {3}이 {1}에서 {2}로 이동 성공")
    @MethodSource("model.fixture.PalaceMovePositionFixture#장_사_이동_가능한_위치")
    void 장_사_이동_성공_테스트(Team team, Position current, Position next, PieceType type) {
        // given
        Piece piece = createPiece(team, type);

        // when
        List<Position> result = piece.pathTo(current, next);

        // then
        assertThat(result).isEmpty();
    }

    @ParameterizedTest(name = "{0}팀 {3}이 {1}에서 {2}로 이동 실패")
    @MethodSource("model.fixture.PalaceMovePositionFixture#장_사_이동_불가능한_위치")
    void 장_사_이동_실패_테스트(Team team, Position current, Position next, PieceType type) {
        // given
        Piece piece = createPiece(team, type);

        // when & then
        assertThatThrownBy(() -> piece.pathTo(current, next))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Piece createPiece(Team team, PieceType type) {
        if (type == PieceType.GENERAL) {
            return new General(team);
        }
        return new Guard(team);
    }
}