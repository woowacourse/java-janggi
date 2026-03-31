package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.board.Position;
import janggi.domain.board.Space;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Test
    void 병_움직임_정상_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(0, 3);
        Position to = new Position(0, 4);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 병_후진_움직임_예외_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(0, 3);
        Position to = new Position(0, 2);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Test
    void 병_대각선_움직임_예외_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(0, 3);
        Position to = new Position(1, 4);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateArrival(space))
                .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }
}