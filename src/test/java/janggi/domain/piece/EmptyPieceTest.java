package janggi.domain.piece;

import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.team.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmptyPieceTest {

    @Test
    void 빈_기물의_팀은_NONE이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.getTeam()).isEqualTo(Team.NONE);
    }

    @Test
    void 빈기물의_타입은_EMPTY이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.getType()).isEqualTo(PieceType.EMPTY);
    }

    @Test
    void 빈_기물은_빈_기물이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.isEmptyPiece()).isTrue();
    }


    @ParameterizedTest
    @EnumSource(value = Team.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
    void 빈_기물은_어떤_팀과도_같은_팀이_아니다(Team expected) {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.isSameTeam(expected)).isFalse();
    }

    @Test
    void 빈_기물에_이동_경로를_요청하면_예외가_발생한다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();
        Movement movement = new Movement(Position.from("11"), Position.from("22"));

        // when & then
        assertThatThrownBy(() -> emptyPiece.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택된 기물이 없습니다.");
    }

    @Test
    void 빈_기물에_이동할_수_있는지_확인하면_예외가_발생한다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThatThrownBy(() -> emptyPiece.validateCanMove(new PieceOnPath(), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 기물은 이동할 수 없습니다.");
    }
}
