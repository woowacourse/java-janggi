package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.board.Path;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Ma;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Sang;
import janggi.domain.space.piece.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaTest {

    @Test
    void 마_움직임_정상_처리_테스트() {
        Piece piece = new Ma(Team.CHO);

        Position from = new Position(1, 0);
        Position to = new Position(3, 1);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 마_움직임_예외_처리_테스트() {
        Piece piece = new Ma(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        assertThatIllegalStateException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("해당 위치로 마가 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Ma(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalStateException()
            .isThrownBy(() -> piece.validateArrival(space))
            .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 이동_경로_사이에_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Ma(Team.CHO);
        List<Piece> pieces = List.of(new Sang(Team.CHO));

        assertThatIllegalStateException()
            .isThrownBy(() -> piece.validateRoutes(pieces))
            .withMessage("이동 경로 사이에 다른 말이 있으면 안됩니다.");
    }

    @Test
    void 마_경로_반환_테스트() {
        Piece piece = new Ma(Team.CHO);

        Position from = new Position(1, 0);
        Position to = new Position(3, 1);
        Path actual = piece.getPath(from, to);

        Path expect = new Path(List.of(new Position(2, 0)));
        assertThat(actual).isEqualTo(expect);
    }
}