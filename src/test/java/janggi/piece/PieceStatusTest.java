package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import janggi.team.TeamName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceStatusTest {
    @DisplayName("정상: 말 상태 확인")
    @Test
    void checkHorseStatus() {
        Piece horse = new Horse(TeamName.CHO, new Position(2, 0));
        assertThat(horse.getStatus()).isEqualTo(PieceStatus.ALIVE);
    }
}
