package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.MaMovement;
import domain.piece.movement.SangMovement;
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
                    new Ma(Team.Han));
            List<JanggiCoordinate> availableMovePositions =
                    MaMovement.availableMovePositions(new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(4);
        }

        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition1() {
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(0, 1),
                    new Ma(Team.Han));

            List<JanggiCoordinate> availableMovePositions =
                    MaMovement.availableMovePositions(new JanggiCoordinate(0, 1), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(6);
        }
    }

    @Nested
    class SangTest {
        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            JanggiBoard board = new JanggiBoard();
            board.getBoard().put(new JanggiCoordinate(5, 5),
                    new Sang(Team.Han));

            List<JanggiCoordinate> availableMovePositions =
                    SangMovement.availableMovePositions(new JanggiCoordinate(5, 5), board);

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(8);
        }
    }

//    @Nested
//    class ChaTest {
//        @DisplayName("차의 이동 가능한 경로를 검사한다")
//        @Test
//        void sangAvailableMovePosition() {
//            Cha cha = new Cha(new JanggiCoordinate(1, 1), Team.Cho);
//            JanggiBoard board = new JanggiBoard();
//
//            List<JanggiCoordinate> availableMovePositions = cha.fromCurrPosition(board);
//
//            Assertions.assertThat(availableMovePositions.size()).isEqualTo(17);
//        }
//    }

}