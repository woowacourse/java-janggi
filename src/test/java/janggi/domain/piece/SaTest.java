package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

class SaTest {

    @Test
    void 사_움직임_경로_정상_판정_테스트() {
        Piece piece = new Sa(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(4, 0);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 사_대각선_움직임_경로_정상_판정_테스트() {
        Piece piece = new Sa(Team.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 2);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 사_움직임_예외_처리_테스트() {
        Piece piece = new Sa(Team.CHO);

        Position from = new Position(3, 2);
        Position to = new Position(5, 2);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("[ERROR] 해당 위치로 사가 이동할 수 없습니다.");
    }

    @Test
    void 궁_벗어난_경우_예외_처리_테스트() {
        Piece piece = new Sa(Team.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(8, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("[ERROR] 사는 궁성 내부에서만 이동할 수 있습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Sa(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateArrival(space))
            .withMessage("[ERROR] 이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }
}