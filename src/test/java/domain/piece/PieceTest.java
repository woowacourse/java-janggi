package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.board.JanggiBoardInitPosition;
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
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Ma ma = new Ma(Country.HAN);
            board.getBoard().put(new JanggiCoordinate(5, 5), ma);
            List<JanggiCoordinate> availableMovePositions =
                    ma.availableMovePositions(new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition1() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Ma ma = new Ma(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    ma.availableMovePositions(new JanggiCoordinate(1, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(2);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition2() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Ma ma = new Ma(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    ma.availableMovePositions(new JanggiCoordinate(1, 2), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 1))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 3))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class SangTest {
        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Sang sang = new Sang(Country.HAN);
            board.getBoard().put(new JanggiCoordinate(5, 5), sang);

            List<JanggiCoordinate> availableMovePositions =
                    sang.availableMovePositions(new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition1() {
            JanggiBoard board = new JanggiBoard(new HashMap<>());
            Sang sang = new Sang(Country.HAN);
            board.getBoard().put(new JanggiCoordinate(1, 3), sang);
            List<JanggiCoordinate> availableMovePositions =
                    sang.availableMovePositions(new JanggiCoordinate(1, 3), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(4, 1))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(4, 5))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class ChaTest {
        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition() {
            Cha cha = new Cha(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    cha);

            List<JanggiCoordinate> availableMovePositions = cha.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(10);
        }

        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Cha cha = new Cha(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    cha.availableMovePositions(new JanggiCoordinate(1, 1), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(2, 1))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 4))).isFalse();
        }
    }

    @Nested
    class ByeongTest {
        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void byeongAvailableMovePosition() {
            Byeong byeong = new Byeong(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    byeong);
            board.getBoard().put(new JanggiCoordinate(6, 5),
                    byeong);

            List<JanggiCoordinate> availableMovePositions = byeong.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(2);
        }

        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Byeong byeong = new Byeong(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    byeong.availableMovePositions(new JanggiCoordinate(4, 5), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(5, 5))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(4, 4))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(4, 6))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(4, 5))).isFalse();
        }
    }

    @Nested
    class SaTest {
        @DisplayName("사의 이동 가능한 경로를 검사한다")
        @Test
        void saAvailableMovePosition() {
            Sa sa = new Sa(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(5, 5), sa);

            List<JanggiCoordinate> availableMovePositions = sa.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(7);
        }

        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Sa sa = new Sa(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    sa.availableMovePositions(new JanggiCoordinate(1, 4), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(2, 4))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(1, 5))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 1))).isFalse();
        }
    }

    @Nested
    class GungTest {
        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void gungAvailableMovePosition() {
            Gung gung = new Gung(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(5, 5), gung);

            List<JanggiCoordinate> availableMovePositions = gung.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(7);
        }

        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition2() {
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            Gung gung = new Gung(Country.HAN);
            List<JanggiCoordinate> availableMovePositions =
                    gung.availableMovePositions(new JanggiCoordinate(2, 5), board);

            assertThat(availableMovePositions.contains(new JanggiCoordinate(2, 4))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(2, 6))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(3, 5))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(1, 5))).isTrue();
            assertThat(availableMovePositions.contains(new JanggiCoordinate(6, 1))).isFalse();
        }
    }

    @Nested
    class PhoTest {
        @DisplayName("포의 이동 가능한 경로를 검사한다")
        @Test
        void PhoAvailableMovePosition() {
            Pho pho = new Pho(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(5, 5), pho);

            List<JanggiCoordinate> availableMovePositions = pho.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            assertThat(availableMovePositions.size()).isEqualTo(3);
        }

        @DisplayName("포는 포를 넘어갈 수 없다")
        @Test
        void PhoAvailableMovePosition2() {
            Pho pho = new Pho(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(4, 2), pho);

            List<JanggiCoordinate> availableMovePositions = pho.availableMovePositions(
                    new JanggiCoordinate(4, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(1);
        }

        @DisplayName("포는 포를 잡을 수 없다")
        @Test
        void PhoAvailableMovePosition3() {
            Pho pho = new Pho(Country.HAN);
            Cha cha = new Cha(Country.HAN);
            JanggiBoard board = new JanggiBoard(JanggiBoardInitPosition.create());
            board.getBoard().put(new JanggiCoordinate(4, 2), pho);
            board.getBoard().put(new JanggiCoordinate(5, 2), cha);

            List<JanggiCoordinate> availableMovePositions = pho.availableMovePositions(
                    new JanggiCoordinate(4, 2), board);

            assertThat(availableMovePositions.size()).isEqualTo(3);
        }
    }
}