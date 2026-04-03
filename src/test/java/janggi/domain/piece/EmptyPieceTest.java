package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmptyPieceTest {

    @Test
    void 빈_기물의_팀은_NONE이다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        Team result = emptyPiece.getTeam();

        assertThat(result).isEqualTo(Team.NONE);
    }

    @Test
    void 빈기물의_타입은_EMPTY이다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        PieceType type = emptyPiece.getType();

        assertThat(type).isEqualTo(PieceType.EMPTY);
    }

    @Test
    void 빈_기물은_빈_기물이다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        assertThat(emptyPiece.isEmptyPiece()).isTrue();
    }

    @Test
    void 빈_기물끼리는_같은_기물이다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        assertThat(emptyPiece.isSamePiece(new EmptyPiece())).isTrue();
    }

    @Test
    void 빈_기물에_이동_경로를_요청하면_예외가_발생한다() {
        EmptyPiece emptyPiece = new EmptyPiece();
        Movement movement = new Movement(Position.from("11"), Position.from("22"));

        assertThatThrownBy(() -> emptyPiece.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택된 기물이 없습니다.");
    }

    @Test
    void 빈_기물에_이동할_수_있는지_확인하면_예외가_발생한다() {
        EmptyPiece emptyPiece = new EmptyPiece();

        assertThatThrownBy(() -> emptyPiece.validateCanMove(new PieceOnPath(), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 기물은 이동할 수 없습니다.");
    }
}
