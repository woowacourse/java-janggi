package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class PieceTest {
    @Nested
    class MaTest {
        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition() {
            Ma ma = new Ma(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), ma);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(8);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition1() {
            Ma ma = new Ma(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 2), ma);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(1, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(3);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition2() {
            Ma ma = new Ma(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 2), ma);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    ma.availableMovePositions(new Coordinate(1, 2), board);

            assertThat(availableMovePositions.contains(new Coordinate(3, 1))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 3))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class SangTest {
        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            Sang sang = new Sang(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), sang);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    sang.availableMovePositions(new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(8);
        }

        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition1() {
            Sang sang = new Sang(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 3), sang);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(1, 3), board);

            assertThat(availableMovePositions.contains(new Coordinate(4, 1))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(4, 5))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class ChaTest {
        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition() {
            Cha cha = new Cha(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), cha);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = cha.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(17);
        }

        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Cha cha = new Cha(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 1), cha);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    cha.availableMovePositions(new Coordinate(1, 1), board);

            assertThat(availableMovePositions.contains(new Coordinate(2, 1))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class ByeongTest {
        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void byeongAvailableMovePosition() {
            Byeong byeong = new Byeong(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), byeong);
            pieces.put(new Coordinate(6, 5), byeong);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = byeong.availableMovePositions(new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(2);
        }

        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Byeong byeong = new Byeong(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(4, 5), byeong);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    byeong.availableMovePositions(new Coordinate(4, 5), board);

            assertThat(availableMovePositions.contains(new Coordinate(5, 5))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(4, 4))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(4, 6))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(4, 5))).isFalse();
        }
    }

    @Nested
    class SaTest {
        @DisplayName("사의 이동 가능한 경로를 검사한다")
        @Test
        void saAvailableMovePosition() {
            Sa sa = new Sa(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), sa);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = sa.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(8);
        }

        @DisplayName("사의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Sa sa = new Sa(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 4), sa);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    sa.availableMovePositions(new Coordinate(1, 4), board);

            assertThat(availableMovePositions.contains(new Coordinate(2, 4))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(1, 5))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 1))).isFalse();
        }
    }

    @Nested
    class GungTest {
        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void gungAvailableMovePosition() {
            Gung gung = new Gung(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(1, 5), gung);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    gung.availableMovePositions(new Coordinate(1, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(5);
        }

        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Gung gung = new Gung(Country.HAN);
            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(2, 5), gung);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions =
                    gung.availableMovePositions(new Coordinate(2, 5), board);

            assertThat(availableMovePositions.contains(new Coordinate(2, 4))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(2, 6))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(3, 5))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(1, 5))).isTrue();
            assertThat(availableMovePositions.contains(new Coordinate(6, 1))).isFalse();
        }
    }

    @Nested
    class PhoTest {
        @DisplayName("포의 이동 가능한 경로를 검사한다")
        @Test
        void phoAvailableMovePosition() {
            Pho pho = new Pho(Country.HAN);
            Cha cha = new Cha(Country.CHO);

            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), pho);
            pieces.put(new Coordinate(6, 5), cha);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("포는 포를 넘어갈 수 없다")
        @Test
        void phoAvailableMovePosition2() {
            Pho pho = new Pho(Country.HAN);
            Pho pho2 = new Pho(Country.CHO);

            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), pho);
            pieces.put(new Coordinate(6, 5), pho);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(0);
        }

        @DisplayName("포는 포를 잡을 수 없다")
        @Test
        void phoAvailableMovePosition3() {
            Pho pho = new Pho(Country.HAN);
            Cha cha = new Cha(Country.CHO);
            Pho pho2 = new Pho(Country.HAN);

            Map<Coordinate, Piece> pieces = new HashMap<>();
            pieces.put(new Coordinate(5, 5), pho);
            pieces.put(new Coordinate(6, 5), cha);
            pieces.put(new Coordinate(7, 5), pho2);
            Board board = new Board(pieces);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(5, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(0);
        }
    }
}