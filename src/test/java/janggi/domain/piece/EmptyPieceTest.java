package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmptyPieceTest {

    @DisplayName("빈 기물의 타입은 EMPTY이다.")
    @Test
    void 빈기물의_타입은_EMPTY이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when
        boolean result = emptyPiece.isSameType(PieceType.EMPTY);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("빈 기물은 빈 기물이다.")
    @Test
    void 빈_기물은_빈_기물이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.isEmptyPiece()).isTrue();
    }

    @DisplayName("빈 기물의 팀은 NONE이다.")
    @Test
    void 빈_기물의_팀은_NONE이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.getTeam()).isEqualTo(Team.NONE);
    }

    @DisplayName("빈 기물이 입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, false",
            "CHO, false",
            "NONE, true",})
    void 빈_기물_같은_팀_확인_테스트(Team team, boolean expected) {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when
        boolean result = emptyPiece.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("빈 기물의 점수는 0점이다.")
    @Test
    void 빈_기물의_점수는_0점이다() {
        // given
        EmptyPiece emptyPiece = new EmptyPiece();

        // when & then
        assertThat(emptyPiece.getScore()).isEqualTo(0.0);
    }

    @DisplayName("빈 기물에 이동 경로를 요청하면 예외가 발생한다.")
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

    @DisplayName("빈 기물에 이동할 수 있는지 확인하면 예외가 발생한다.")
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