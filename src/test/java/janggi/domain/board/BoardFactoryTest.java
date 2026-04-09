package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static janggi.domain.Team.CHO;
import static janggi.domain.Team.HAN;
import static janggi.domain.board.PieceSetup.*;
import static janggi.domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {

    @DisplayName("한나라 기물 차림 입력이 4면 한나라는 바깥상 차림이다.")
    @Test
    void 한나라_기물_차림_입력이_4면_한나라는_바깥상_차림이다() {
        // given
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "11", CHARIOT, HAN);
        assertPiece(boardState, "12", ELEPHANT, HAN);
        assertPiece(boardState, "13", HORSE, HAN);
        assertPiece(boardState, "14", GUARD, HAN);
        assertPiece(boardState, "16", GUARD, HAN);
        assertPiece(boardState, "17", HORSE, HAN);
        assertPiece(boardState, "18", ELEPHANT, HAN);
        assertPiece(boardState, "19", CHARIOT, HAN);
        assertPiece(boardState, "25", GENERAL, HAN);
        assertPiece(boardState, "32", CANNON, HAN);
        assertPiece(boardState, "38", CANNON, HAN);
        assertPiece(boardState, "41", SOLDIER, HAN);
        assertPiece(boardState, "43", SOLDIER, HAN);
        assertPiece(boardState, "45", SOLDIER, HAN);
        assertPiece(boardState, "47", SOLDIER, HAN);
        assertPiece(boardState, "49", SOLDIER, HAN);
    }

    @DisplayName("초나라 기물 차림 입력이 4면 초나라는 바깥상 차림이다.")
    @Test
    void 초나라_기물_차림_입력이_4면_초나라는_바깥상_차림이다() {
        // given
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "01", CHARIOT, CHO);
        assertPiece(boardState, "02", ELEPHANT, CHO);
        assertPiece(boardState, "03", HORSE, CHO);
        assertPiece(boardState, "04", GUARD, CHO);
        assertPiece(boardState, "06", GUARD, CHO);
        assertPiece(boardState, "07", HORSE, CHO);
        assertPiece(boardState, "08", ELEPHANT, CHO);
        assertPiece(boardState, "09", CHARIOT, CHO);
        assertPiece(boardState, "95", GENERAL, CHO);
        assertPiece(boardState, "82", CANNON, CHO);
        assertPiece(boardState, "88", CANNON, CHO);
        assertPiece(boardState, "71", SOLDIER, CHO);
        assertPiece(boardState, "73", SOLDIER, CHO);
        assertPiece(boardState, "75", SOLDIER, CHO);
        assertPiece(boardState, "77", SOLDIER, CHO);
        assertPiece(boardState, "79", SOLDIER, CHO);
    }

    @DisplayName("차림 번호가 1번이면 왼상 차림이다.")
    @Test
    void 차림_번호가_1번이면_왼상차림이다() {
        // given
        Board board = BoardFactory.create(LEFT_ELEPHANT, LEFT_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "12", HORSE, HAN);
        assertPiece(boardState, "13", ELEPHANT, HAN);
        assertPiece(boardState, "17", HORSE, HAN);
        assertPiece(boardState, "18", ELEPHANT, HAN);
        assertPiece(boardState, "02", ELEPHANT, CHO);
        assertPiece(boardState, "03", HORSE, CHO);
        assertPiece(boardState, "07", ELEPHANT, CHO);
        assertPiece(boardState, "08", HORSE, CHO);
    }

    @DisplayName("차림 번호가 2번이면 오른상차림이다.")
    @Test
    void 차림_번호가_2번이면_오른상차림이다() {
        // given
        Board board = BoardFactory.create(RIGHT_ELEPHANT, RIGHT_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "12", ELEPHANT, HAN);
        assertPiece(boardState, "13", HORSE, HAN);
        assertPiece(boardState, "17", ELEPHANT, HAN);
        assertPiece(boardState, "18", HORSE, HAN);
        assertPiece(boardState, "02", HORSE, CHO);
        assertPiece(boardState, "03", ELEPHANT, CHO);
        assertPiece(boardState, "07", HORSE, CHO);
        assertPiece(boardState, "08", ELEPHANT, CHO);
    }

    @DisplayName("차림 번호가 3번이면 안상차림이다.")
    @Test
    void 차림_번호가_3번이면_안상차림이다() {
        // given
        Board board = BoardFactory.create(INNER_ELEPHANT, INNER_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "12", HORSE, HAN);
        assertPiece(boardState, "13", ELEPHANT, HAN);
        assertPiece(boardState, "17", ELEPHANT, HAN);
        assertPiece(boardState, "18", HORSE, HAN);
        assertPiece(boardState, "02", HORSE, CHO);
        assertPiece(boardState, "03", ELEPHANT, CHO);
        assertPiece(boardState, "07", ELEPHANT, CHO);
        assertPiece(boardState, "08", HORSE, CHO);
    }

    @DisplayName("차림 번호가 4번이면 바깥상차림이다.")
    @Test
    void 차림_번호가_4번이면_바깥상차림이다() {
        // given
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        Map<Position, Piece> boardState = board.showBoard();

        // then
        assertPiece(boardState, "12", ELEPHANT, HAN);
        assertPiece(boardState, "13", HORSE, HAN);
        assertPiece(boardState, "17", HORSE, HAN);
        assertPiece(boardState, "18", ELEPHANT, HAN);
        assertPiece(boardState, "02", ELEPHANT, CHO);
        assertPiece(boardState, "03", HORSE, CHO);
        assertPiece(boardState, "07", HORSE, CHO);
        assertPiece(boardState, "08", ELEPHANT, CHO);
    }

    private void assertPiece(Map<Position, Piece> board, String pos, PieceType expectedType, Team team) {
        Piece actual = board.get(Position.from(pos));
        boolean resultOfType = actual.isSameType(expectedType);
        boolean resultOfTeam = actual.isSameTeam(team);

        assertThat(resultOfType).isTrue();
        assertThat(resultOfTeam).isTrue();
    }
}
