package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @DisplayName("CHO 팀 Piece의 움직임 테스트")
    @Nested
    class ChoTeam {

        @Test
        @DisplayName("CHO 팀의 Piece가 왼쪽으로 3만큼 움직이면 Piece의 x 좌표 3 감소한다.")
        void test2() {
            // given
            Piece piece = new Piece(new Coordinate(5, 1), Team.CHO);

            // when
            Piece movedPiece = piece.moveLeft(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 Piece가 오른쪽으로 3만큼 움직이면 Piece의 x 좌표가 3 증가한다.")
        void test4() {
            // given
            Piece piece = new Piece(new Coordinate(5, 1), Team.CHO);

            // when
            Piece movedPiece = piece.moveRight(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(8, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 Piece가 앞쪽으로 3만큼 움직이면 Piece의 y 좌표가 3 감소한다.")
        void test6() {
            // given
            Piece piece = new Piece(new Coordinate(2, 5), Team.CHO);

            // when
            Piece movedPiece = piece.moveForward(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 2), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 Piece가 뒤쪽으로 3민큼 움직이면 Piece의 y 좌표가 3 증가한다.")
        void test8() {
            // given
            Piece piece = new Piece(new Coordinate(2, 5), Team.CHO);

            // when
            Piece movedPiece = piece.moveBackward(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 8), Team.CHO));
        }
    }

    @DisplayName("HAN 팀 Piece의 움직임 테스트 ")
    @Nested
    class HanTeam {

        @Test
        @DisplayName("HAN 팀의 Piece가 왼쪽으로 3만큼 움직이면 Piece의 x 좌표가 3 증가한다.")
        void test1() {
            // given
            Piece piece = new Piece(new Coordinate(2, 1), Team.HAN);

            // when
            Piece movedPiece = piece.moveLeft(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(5, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 Piece가 오른쪽으로 3만큼 움직이면 Piece의 x 좌표가 3 감소한다.")
        void test3() {
            // given
            Piece piece = new Piece(new Coordinate(5, 1), Team.HAN);

            // when
            Piece movedPiece = piece.moveRight(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 Piece가 앞쪽으로 3만큼 움직이면 Piece의 y 좌표가 3 증가한다.")
        void test5() {
            // given
            Piece piece = new Piece(new Coordinate(2, 2), Team.HAN);

            // when
            Piece movedPiece = piece.moveForward(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 5), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 Piece가 뒤쪽으로 3만큼 움직이면 Piece의 y 좌표가 3 감소한다.")
        void test7() {
            // given
            Piece piece = new Piece(new Coordinate(2, 5), Team.HAN);

            // when
            Piece movedPiece = piece.moveBackward(3);

            // then
            assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(2, 2), Team.HAN));
        }
    }
}
