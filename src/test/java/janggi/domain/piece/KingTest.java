package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.King;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Sang;
import janggi.domain.space.piece.Team;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void 궁_움직임_정상_처리_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 궁_움직임_예외_처리_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(5, 1);
        Position to = new Position(8, 1);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 궁이 이동할 수 없습니다.");
    }


    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new King(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateArrival(space))
                .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 궁_궁성_정상_이동_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(4, 1);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 궁_궁성_예외_이동_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(5, 1);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 궁이 이동할 수 없습니다.");
    }

    @Test
    void 궁_궁성_바깥_이동_예외_테스트() {
        Piece piece = new King(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(2, 0);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 궁이 이동할 수 없습니다.");
    }
}