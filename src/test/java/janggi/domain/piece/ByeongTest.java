package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Byeong;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Sang;
import janggi.domain.space.piece.Team;
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

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Test
    void 병_대각선_움직임_예외_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(0, 3);
        Position to = new Position(1, 4);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Byeong(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateArrival(space))
                .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 병_궁성_정상_이동_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(3, 7);
        Position to = new Position(4, 8);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 병_궁성_예외_이동_테스트() {
        Piece piece = new Byeong(Team.CHO);

        Position from = new Position(3, 7);
        Position to = new Position(5, 8);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 병이 이동할 수 없습니다.");
    }
}