package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    void 차_움직임_정상_처리_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(0, 9);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 차_움직임_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("해당 위치로 차가 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateArrival(space))
            .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 이동_경로_사이에_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);
        List<Piece> pieces = List.of(new Sang(Team.CHO));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateRoutes(pieces))
            .withMessage("이동 경로 사이에 다른 말이 있으면 안됩니다.");
    }
}