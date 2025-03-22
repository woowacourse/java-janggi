package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import domain.board.strategy.SangMaMaSang;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class PieceTest {
    @Nested
    class MaTest {
        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition() {
            Board board = new Board(new SangMaMaSang());
            Ma ma = new Ma(Country.HAN);
            board.getBoard().put(new Coordinate(5, 5), ma);
            List<Coordinate> availableMovePositions =
                    ma.availableMovePositions(new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition1() {
            Board board = new Board(new SangMaMaSang());
            Ma ma = new Ma(Country.HAN);
            List<Coordinate> availableMovePositions =
                    ma.availableMovePositions(new Coordinate(1, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(2);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition2() {
            Board board = new Board(new SangMaMaSang());
            Ma ma = new Ma(Country.HAN);
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
            Board board = new Board(new SangMaMaSang());
            Sang sang = new Sang(Country.HAN);
            board.getBoard().put(new Coordinate(5, 5), sang);

            List<Coordinate> availableMovePositions =
                    sang.availableMovePositions(new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition1() {
            Board board = new Board(HashMap::new);
            Sang sang = new Sang(Country.HAN);
            board.getBoard().put(new Coordinate(1, 3), sang);
            List<Coordinate> availableMovePositions = sang.availableMovePositions(
                    new Coordinate(1, 3), board);

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
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(5, 5),
                    cha);

            List<Coordinate> availableMovePositions = cha.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(10);
        }

        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Board board = new Board(new SangMaMaSang());
            Cha cha = new Cha(Country.HAN);
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
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(5, 5),
                    byeong);
            board.getBoard().put(new Coordinate(6, 5),
                    byeong);

            List<Coordinate> availableMovePositions = byeong.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(2);
        }

        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Board board = new Board(new SangMaMaSang());
            Byeong byeong = new Byeong(Country.HAN);
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
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(5, 5), sa);

            List<Coordinate> availableMovePositions = sa.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(7);
        }

        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Board board = new Board(new SangMaMaSang());
            Sa sa = new Sa(Country.HAN);
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
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(5, 5), gung);

            List<Coordinate> availableMovePositions = gung.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(7);
        }

        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            Board board = new Board(new SangMaMaSang());
            Gung gung = new Gung(Country.HAN);
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
        void PhoAvailableMovePosition() {
            Pho pho = new Pho(Country.HAN);
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(5, 5), pho);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(3);
        }

        @DisplayName("포는 포를 넘어갈 수 없다")
        @Test
        void PhoAvailableMovePosition2() {
            Pho pho = new Pho(Country.HAN);
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(4, 2), pho);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(4, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(1);
        }

        @DisplayName("포는 포를 잡을 수 없다")
        @Test
        void PhoAvailableMovePosition3() {
            Pho pho = new Pho(Country.HAN);
            Cha cha = new Cha(Country.HAN);
            Board board = new Board(new SangMaMaSang());
            board.getBoard().put(new Coordinate(4, 2), pho);
            board.getBoard().put(new Coordinate(5, 2), cha);

            List<Coordinate> availableMovePositions = pho.availableMovePositions(
                    new Coordinate(4, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(3);
        }
    }
}