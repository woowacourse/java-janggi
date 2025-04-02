package janggi.data.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.point.Point;
import janggi.data.dao.test.FakeBoardDao;
import janggi.data.dao.test.FakePiece;
import janggi.piece.Camp;
import janggi.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private final BoardDao boardDao = new FakeBoardDao();

    @DisplayName("보드판을 생성하고 기본 상태를 확인할 수 있다.")
    @Test
    void createBoardTest() {
        // when
        boardDao.create();

        // then
        assertAll(
                () -> assertThat(boardDao.existsActiveGame())
                        .isTrue(),
                () -> assertThat(boardDao.findCurrentTurnCamp())
                        .isSameAs(Camp.CHU),
                () -> assertThat(boardDao.findCurrentBoardPieces())
                        .isEmpty()
        );
    }

    @DisplayName("게임을 종료하면 활성 게임이 종료 상태가 된다.")
    @Test
    void endGameTest() {
        // given
        boardDao.create();

        // when
        boardDao.endGame();

        // then
        assertThat(boardDao.existsActiveGame()).isFalse();
    }

    @DisplayName("활성화된 게임이 없으면 ID를 조회할 수 없다.")
    @Test
    void findCurrentBoardIdWithoutActiveGameTest() {
        // when & then
        assertThatCode(boardDao::findCurrentBoardId)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최신 보드판의 ID를 조회하는 도중 오류가 발생했습니다.");
    }

    @DisplayName("현재 턴의 진영을 확인하고 턴을 변경할 수 있다.")
    @Test
    void turnChangeTest() {
        // given
        boardDao.create();

        // when
        boardDao.turnChange();

        // then
        assertThat(boardDao.findCurrentTurnCamp())
                .isSameAs(Camp.HAN);
    }

    @DisplayName("보드판 생성 시간을 확인할 수 있다.")
    @Test
    void findCurrentBoardCreatedAtTest() {
        // given
        boardDao.create();

        // then
        assertThat(boardDao.findCurrentBoardCreatedAt())
                .isNotNull();
    }

    @DisplayName("현재 보드에서 기물 정보를 조회할 수 있다.")
    @Test
    void findCurrentBoardPiecesTest() {
        // given
        boardDao.create();
        Point point = new Point(1, 1);
        Piece piece = new FakePiece(Camp.CHU);
        placeFakePiece(point, piece);

        // when
        Map<Point, Piece> pieces = boardDao.findCurrentBoardPieces();

        // then
        assertThat(pieces)
                .containsEntry(point, piece);
    }

    private void placeFakePiece(Point point, Piece piece) {
        boardDao.findCurrentBoardPieces()
                .put(point, piece);
    }
}
