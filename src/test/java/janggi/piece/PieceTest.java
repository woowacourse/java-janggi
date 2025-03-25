package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @DisplayName("정상: 특정 위치를 기물이 차지하고 있는지 확인")
    @Test
    void checkOccupant() {
        Piece horse = new Horse(TeamName.HAN, new Position(2, 9));

        boolean isOccupant = horse.isOccupiedByMe(new Position(2, 9));

        assertThat(isOccupant).isTrue();
    }

    @DisplayName("정상: 특정 위치로의 기물 이동 확인")
    @Test
    void checkMovement() {
        Piece horse = new Horse(TeamName.CHO, new Position(2, 0));

        horse.move(new Position(3, 2));

        assertThat(horse.isOccupiedByMe(new Position(3, 2))).isTrue();
    }

    @DisplayName("정상: 기물의 이동 규칙이 올바른지 확인")
    @Test
    void validateMovement() {
        Piece horse = new Horse(TeamName.CHO, new Position(2, 0));

        assertThatCode(() -> horse.validateMovement(new Position(2, 0), new Position(3, 2),
                PalaceArea.OUTSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 기물의 이동 규칙이 올바른지 확인 (궁 안에 있는 경우)")
    @Test
    void validateMovementInPalace() {
        Piece king = new King(TeamName.CHO, new Position(4, 1));

        assertThatCode(() -> king.validateMovement(new Position(4, 1), new Position(4, 2),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 기물이 잡힌 경우 상태 갱신 확인")
    @Test
    void checkCaptured() {
        Piece horse = new Horse(TeamName.CHO, new Position(2, 0));

        horse.updateStatusIfCaught(new Position(2, 0));

        assertThat(horse.matchStatus(PieceStatus.CAUGHT)).isTrue();
    }
}
