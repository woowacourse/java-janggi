package janggi.domain.board;

import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @DisplayName("출발 좌표와 도착 좌표를 입력하면 도착 좌표의 기물은 출발 좌표의 기물이 된다.")
    @Test
    void 출발_좌표와_도착_좌표를_입력하면_도착_좌표의_기물은_출발_좌표의_기물이_된다() {
        // given
        board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("25"), Position.from("35"));
        Piece fromPiece = getPiece(movement.getFrom());

        // when
        board.move(movement, Team.HAN);

        // then
        assertThat(getPiece(movement.getTo())).isEqualTo(fromPiece);
    }

    @DisplayName("출발 좌표와 도착 좌표를 입력하면 출발 좌표의 기물은 빈 기물이 된다.")
    @Test
    void 출발_좌표와_도착_좌표를_입력하면_출발_좌표의_기물은_빈_기물이_된다() {
        // given
        board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("25"), Position.from("35"));

        // when
        board.move(movement, Team.HAN);

        // then
        assertThat(getPiece(movement.getFrom()).isEmptyPiece()).isTrue();
    }

    @DisplayName("자신의 기물이 아닌 기물을 이동시키면 예외가 발생한다.")
    @Test
    void 자신의_기물이_아닌_기물을_이동시키면_예외가_발생한다() {
        // given
        board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("43"), Position.from("53"));

        // when & then
        assertThatThrownBy(() -> board.move(movement, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물만 이동시킬 수 있습니다.");
    }

    @DisplayName("빈 기물을 이동시키면 예외가 발생한다.")
    @Test
    void 빈_기물을_이동시키면_예외가_발생한다() {
        // given
        board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("55"), Position.from("65"));

        // when & then
        assertThatThrownBy(() -> board.move(movement, Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택된 기물이 없습니다.");
    }

    @DisplayName("장이 잡히지 않으면 거짓을 반환한다.")
    @Test
    void 장이_잡히지_않으면_거짓을_반환한다() {
        // given
        board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when & then
        assertThat(board.isGeneralCaptured(Team.HAN)).isFalse();
    }

    @DisplayName("장이 잡히면 참을 반환한다.")
    @Test
    void 장이_잡히면_참을_반환한다() {
        // given
        Map<Position, Piece> base = new LinkedHashMap<>();
        base.put(Position.of(2, 5), new General(Team.HAN));
        base.put(Position.of(3, 5), new Soldier(Team.CHO));
        board = new Board(base);
        Movement movement = new Movement(Position.of(3, 5), Position.of(2, 5));

        // when
        board.move(movement, Team.CHO);

        // then
        assertThat(board.isGeneralCaptured(Team.HAN)).isTrue();
    }

    private Piece getPiece(Position position) {
        return board.showBoard().get(position);
    }
}
