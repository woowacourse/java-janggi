package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Nested
    @DisplayName("같은 팀인 지 판별하는 테스트")
    class IsSameTeamTest {

        @Test
        @DisplayName("다른 기물과 같은 팀이면 true를 반환한다.")
        void test1() {
            // given
            Piece piece1 = new Piece(Team.HAN, new Coordinate(1, 1), PieceType.CHA);
            Piece piece2 = new Piece(Team.HAN, new Coordinate(1, 2), PieceType.MA);

            // when
            boolean isSameTeam = piece1.isSameTeam(piece2);

            // then
            assertThat(isSameTeam).isTrue();
        }

        @Test
        @DisplayName("다른 기물과 다른 팀이면 false를 반환한다.")
        void test2() {
            // given
            Piece piece1 = new Piece(Team.HAN, new Coordinate(1, 1), PieceType.CHA);
            Piece piece2 = new Piece(Team.CHO, new Coordinate(1, 2), PieceType.MA);

            // when
            boolean isSameTeam = piece1.isSameTeam(piece2);

            // then
            assertThat(isSameTeam).isFalse();
        }
    }

    @Nested
    @DisplayName("기물이 포인 지 판별하는 테스트")
    class IsPoTest {

        @Test
        @DisplayName("기물이 포이면 true를 반환한다.")
        void test1() {
            // given
            Piece piece = new Piece(Team.HAN, new Coordinate(1, 1), PieceType.PO);

            // when
            boolean isPo = piece.isPo();

            // then
            assertThat(isPo).isTrue();
        }

        @Test
        @DisplayName("기물이 포가 아니면 false를 반환한다.")
        void test2() {
            // given
            Piece piece = new Piece(Team.HAN, new Coordinate(1, 1), PieceType.CHA);

            // when
            boolean isPo = piece.isPo();

            // then
            assertThat(isPo).isFalse();
        }
    }
}
