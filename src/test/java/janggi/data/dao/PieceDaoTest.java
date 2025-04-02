package janggi.data.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.point.Point;
import janggi.data.dao.test.FakePiece;
import janggi.data.dao.test.FakePieceDao;
import janggi.piece.Camp;
import janggi.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final PieceDao pieceDao = new FakePieceDao();

    @DisplayName("기물을 저장하면 해당 위치에 기물이 존재한다.")
    @Test
    void saveTest() {
        // given
        Point point = new Point(1, 1);
        Piece piece = new FakePiece(Camp.CHU);

        // when
        pieceDao.save(point, piece);

        // then
        assertThat(((FakePieceDao) pieceDao).findByPoint(point))
                .isEqualTo(piece);
    }

    @DisplayName("기물을 이동하면 기존 위치는 비어있고, 새 위치에 기물이 존재한다.")
    @Test
    void moveTest() {
        // given
        Point start = new Point(2, 2);
        Point end = new Point(3, 3);
        Piece piece = new FakePiece(Camp.HAN);
        pieceDao.save(start, piece);

        // when
        pieceDao.move(start, end);

        // then
        assertAll(
                () -> assertThat(((FakePieceDao) pieceDao).findByPoint(end))
                        .isEqualTo(piece),
                () -> assertThatCode(() -> ((FakePieceDao) pieceDao).findByPoint(start))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 위치에 기물이 존재하지 않습니다.")
        );
    }

    @DisplayName("기물을 삭제하고 그 위치로 기물을 찾는 경우 예외가 발생한다.")
    @Test
    void deleteTest() {
        // given
        Point point = new Point(4, 4);
        Piece piece = new FakePiece(Camp.CHU);

        pieceDao.save(point, piece);

        // when
        pieceDao.delete(point);

        // then
        assertThatCode(() -> ((FakePieceDao) pieceDao).findByPoint(point))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }
}
