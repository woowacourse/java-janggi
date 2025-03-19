package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class PieceTest {
    @Nested
    class MaTest {
        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition() {
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    new Ma(Team.HAN));
            List<JanggiCoordinate> availableMovePositions =
                    MaMovement.availableMovePositions(new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition1() {
            JanggiBoard board = new JanggiBoard();

            List<JanggiCoordinate> availableMovePositions =
                    MaMovement.availableMovePositions(new JanggiCoordinate(2, 1), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(2);
        }
    }

    @Nested
    class SangTest {
        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    new Sang(Team.HAN));

            List<JanggiCoordinate> availableMovePositions =
                    SangMovement.availableMovePositions(new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(4);
        }
    }

    @Nested
    class ChaTest {
        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void chaAvailableMovePosition() {
            Cha cha = new Cha(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    cha);

            List<JanggiCoordinate> availableMovePositions = ChaMovement.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(10);
        }
    }

    @Nested
    class ByeongTest {
        @DisplayName("병의 이동 가능한 경로를 검사한다")
        @Test
        void byeongAvailableMovePosition() {
            Byeong byeong = new Byeong(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    byeong);
            board.getBoard().put(new JanggiCoordinate(6, 5),
                    byeong);

            List<JanggiCoordinate> availableMovePositions = ByeongMovement.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(2);
        }
    }

    @Nested
    class SaTest {
        @DisplayName("사의 이동 가능한 경로를 검사한다")
        @Test
        void saAvailableMovePosition() {
            Sa sa = new Sa(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5), sa);

            List<JanggiCoordinate> availableMovePositions = SaMovement.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(7);
        }
    }

    @Nested
    class GungTest {
        @DisplayName("궁의 이동 가능한 경로를 검사한다")
        @Test
        void gungAvailableMovePosition() {
            Gung gung = new Gung(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5), gung);

            List<JanggiCoordinate> availableMovePositions = GungMovement.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(7);
        }
    }

    @Nested
    class PhoTest {
        @DisplayName("포의 이동 가능한 경로를 검사한다")
        @Test
        void PhoAvailableMovePosition() {
            Pho pho = new Pho(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5), pho);

            List<JanggiCoordinate> availableMovePositions = PhoMovement.availableMovePositions(
                    new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(3);
        }

        @DisplayName("포는 포를 넘어갈 수 없다")
        @Test
        void PhoAvailableMovePosition2() {
            Pho pho = new Pho(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(2,4), pho);

            List<JanggiCoordinate> availableMovePositions = PhoMovement.availableMovePositions(
                    new JanggiCoordinate(2, 4), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(1);
        }

        @DisplayName("포는 포를 잡을 수 없다")
        @Test
        void PhoAvailableMovePosition3() {
            Pho pho = new Pho(Team.HAN);
            Cha cha = new Cha(Team.HAN);
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(2, 4), pho);
            board.getBoard().put(new JanggiCoordinate(2,5), cha);

            List<JanggiCoordinate> availableMovePositions = PhoMovement.availableMovePositions(
                    new JanggiCoordinate(2, 4), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(3);
        }
    }
}