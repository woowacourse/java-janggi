package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Position;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void 궁_움직임_정상_처리_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(5, 1);
        Position to = new Position(6, 1);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 궁_움직임_예외_처리_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(5, 1);
        Position to = new Position(8, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("해당 위치로 궁이 이동할 수 없습니다.");
    }

}