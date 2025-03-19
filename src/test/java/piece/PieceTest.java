package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {


    @Nested
    @DisplayName("피스 생성")
    class Construct {

        @DisplayName("피스를 올바로 생성한다.")
        @Test
        void construct1() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final TeamType teamType = TeamType.RED;

            // when
            final Piece piece = new Piece(pieceType, teamType);

            // then
            assertAll(
                    () -> Assertions.assertThat(piece.getPieceType()).isEqualTo(pieceType),
                    () -> Assertions.assertThat(piece.getTeamType()).isEqualTo(teamType)
            );
        }
    }

    @Nested
    @DisplayName("검증 계산")
    class calculate {

        @DisplayName("만약 타입이 같다면 true를 반환한다.")
        @Test
        void equalsPieceType() {
            // given
            final Piece piece = new Piece(PieceType.CANNON, TeamType.RED);
            final PieceType equals = PieceType.CANNON;
            final PieceType notEquals = PieceType.CHARIOT;

            // when
            final boolean actualEquals = piece.equalsPieceType(equals);
            final boolean actualNotEquals = piece.equalsPieceType(notEquals);

            // then
            Assertions.assertThat(actualEquals).isTrue();
            Assertions.assertThat(actualNotEquals).isFalse();
        }

        @DisplayName("만약 팀이 같다면 true를 반환한다.")
        @Test
        void equalsTeamType() {
            // given
            final Piece piece = new Piece(PieceType.CANNON, TeamType.RED);
            final TeamType equals = TeamType.RED;
            final TeamType notEquals = TeamType.BLUE;

            // when
            final boolean actualEquals = piece.equalsTeamType(equals);
            final boolean actualNotEquals = piece.equalsTeamType(notEquals);

            // then
            Assertions.assertThat(actualEquals).isTrue();
            Assertions.assertThat(actualNotEquals).isFalse();
        }
    }
}
