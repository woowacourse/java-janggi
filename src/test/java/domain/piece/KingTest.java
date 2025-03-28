package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import domain.board.BoardLocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    @DisplayName("왕이 궁성 밖으로 이동시 예외 발생")
    void validateArrival() {
        King king = new King(Team.HAN);
        BoardLocation current = new BoardLocation(4,2);
        BoardLocation destination = new BoardLocation(3,2);

        assertThatThrownBy(() -> {
            king.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
