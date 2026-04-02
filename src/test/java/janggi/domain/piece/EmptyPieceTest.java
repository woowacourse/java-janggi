package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EmptyPieceTest {

    @Test
    void 빈_기물의_팀을_확인하면_거짓을_반환한다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        boolean hanResult = emptyPiece.getTeam() == Team.HAN;
        boolean choResult = emptyPiece.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isFalse(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 빈_기물은_두_나라에_소속되지_않는다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        boolean hanResult = emptyPiece.getTeam() == Team.HAN;
        boolean choResult = emptyPiece.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isFalse(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 빈기물의_타입은_EMPTY다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        PieceType type = emptyPiece.getType();
        assertThat(type).isEqualTo(PieceType.EMPTY);
    }

    @Test
    void 빈_기물에_이동_경로를_요청하면_예외가_발생한다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        assertThatThrownBy(() -> emptyPiece.getPath(Position.from("11"), Position.from("22")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택된 기물이 없습니다.");
    }

    @Test
    void 빈_기물에_이동할_수_있는지_확인하면_거짓을_반환한다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        boolean result = emptyPiece.canMove(new PieceOnPath(), new EmptyPiece());

        assertThat(result).isFalse();
    }
}
