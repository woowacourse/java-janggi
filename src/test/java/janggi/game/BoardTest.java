package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.piece.Byeong;
import janggi.piece.Gung;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("장기 판 초기화 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("장기 말들을 가진 장기판을 생성할 수 있다.")
        void createBoard() {
            Board board = Board.init();

            assertThat(board.getRunningPieces()).hasSize(32);
        }
    }

    @Nested
    @DisplayName("기물 검색 테스트")
    class SearchTest {

        @Test
        @DisplayName("입력 좌표에 기물이 위치하는지 확인할 수 있다.")
        void searchByPoint() {
            Point point = new Point(8, 4);
            List<Movable> pieces = List.of(new Gung(Team.CHO, point));
            Board board = new Board(pieces);

            assertThat(board.findByPoint(point)).isEqualTo(pieces.getFirst());
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("기물의 위치를 특정 위치로 바꿀 수 있다.")
        void updatePoint() {
            Point beforePoint = new Point(6, 4);
            Byeong byeong = new Byeong(Team.CHO, beforePoint);
            List<Movable> pieces = new ArrayList<>(List.of(byeong));
            Board board = new Board(pieces);

            Point afterPoint = new Point(5, 4);
            board.move(beforePoint, afterPoint);

            assertAll(() -> {
                assertThat(board.findByPoint(afterPoint).getPoint()).isEqualTo(afterPoint);
                assertThatThrownBy(() -> board.findByPoint(beforePoint))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("해당 좌표에 기물이 존재하지 않습니다.");
            });
        }

        @Test
        @DisplayName("이동 하는 위치에 기물이 있으면 공격한다.")
        void attackPiece() {
            Point beforePoint = new Point(6, 4);
            Byeong byeong1 = new Byeong(Team.CHO, beforePoint);

            Point afterPoint = new Point(5, 4);
            Byeong byeong2 = new Byeong(Team.HAN, afterPoint);

            List<Movable> pieces = new ArrayList<>(List.of(byeong1, byeong2));
            Board board = new Board(pieces);

            board.move(beforePoint, afterPoint);

            assertThat(board.getRunningPieces()).hasSize(1);
        }
    }
}
