package domain.board;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.piece.Ma;
import domain.piece.Pho;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JanggiBoardTest {

    @Nested
    class BoardCoordinateTest {

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생하지 않는다.")
        @Test
        void findPieceTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertDoesNotThrow(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(5, 5)));
        }

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생한다.")
        @Test
        void findPieceWrongCoordinateTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertThatThrownBy(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(2, 2)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("보드에 기물이 있는 경우 기물을 이동 시킬 수 있다")
        @Test
        void boardPieceMoveTest() {
            Piece piece = new Pho(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate from = new JanggiCoordinate(5, 5);
            JanggiCoordinate to = new JanggiCoordinate(3, 3);

            map.put(from, piece);
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> janggiBoard.movePiece(from, to)),
                    () -> assertThat(janggiBoard.isOccupied(from)).isFalse(),
                    () -> assertThat(janggiBoard.isOccupied(to)).isTrue()
            );
        }
    }
}