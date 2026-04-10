package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {
    Piece choPiece;
    Piece hanPiece;

    @BeforeEach
    void setUp() {
        choPiece = new Piece(Camp.CHO, PieceType.CANNON);
        hanPiece = new Piece(Camp.HAN, PieceType.CANNON);
    }

    @Test
    @DisplayName("기물은 자신의 진영 정보를 알고 있다.")
    void determine_ChoOrHan() {
        Camp choCamp = choPiece.camp();
        Camp hanCamp = hanPiece.camp();

        assertThat(choCamp).isEqualTo(Camp.CHO);
        assertThat(hanCamp).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("기물은 자신의 타입 정보를 알고 있다.")
    void determine_Self_Type() {
        PieceType pieceType = choPiece.type();

        assertThat(pieceType).isEqualTo(PieceType.CANNON);
    }

    @Nested
    class Score {

        @Test
        @DisplayName("궁은 0점으로 처리한다.")
        void generalScore() {
            Piece general = new Piece(Camp.CHO, PieceType.GENERAL);

            int generalScore = general.score();

            assertThat(generalScore).isEqualTo(0);
        }

        @Test
        @DisplayName("사는 3점으로 처리한다.")
        void guardScore() {
            Piece guard = new Piece(Camp.CHO, PieceType.GUARD);

            int guardScore = guard.score();

            assertThat(guardScore).isEqualTo(3);
        }

        @Test
        @DisplayName("마는 5점으로 처리한다.")
        void horseScore() {
            Piece horse = new Piece(Camp.CHO, PieceType.HORSE);

            int horseScore = horse.score();

            assertThat(horseScore).isEqualTo(5);
        }

        @Test
        @DisplayName("상은 3점으로 처리한다.")
        void elephantScore() {
            Piece elephant = new Piece(Camp.CHO, PieceType.ELEPHANT);

            int elephantScore = elephant.score();

            assertThat(elephantScore).isEqualTo(3);
        }

        @Test
        @DisplayName("병과 졸은 2점으로 처리한다.")
        void soldierScore() {
            Piece choSoldier = new Piece(Camp.CHO, PieceType.SOLDIER);
            Piece hanSoldier = new Piece(Camp.HAN, PieceType.SOLDIER);

            int choSoldierScore = choSoldier.score();
            int hanSoldierScore = hanSoldier.score();

            assertThat(choSoldierScore).isEqualTo(2);
            assertThat(hanSoldierScore).isEqualTo(2);
        }

        @Test
        @DisplayName("포는 7점으로 처리한다.")
        void cannonScore() {
            Piece cannon = new Piece(Camp.CHO, PieceType.CANNON);

            int cannonScore = cannon.score();

            assertThat(cannonScore).isEqualTo(7);
        }

        @Test
        @DisplayName("차는 13점으로 처리한다.")
        void chariotScore() {
            Piece chariot = new Piece(Camp.CHO, PieceType.CHARIOT);

            int chariotScore = chariot.score();

            assertThat(chariotScore).isEqualTo(13);
        }
    }
}
