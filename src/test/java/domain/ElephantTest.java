package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    @DisplayName("다른 기물이 없는 경우, 모든 목적지를 반환할 수 있다.")
    @Test
    void notExistOtherPieces() {
        // given
        final ChessPosition elephantPosition = new ChessPosition(4, 4);
        final Elephant elephant = new Elephant(elephantPosition, ChessTeam.BLUE);

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7, 6),
                new ChessPosition(7, 2),
                new ChessPosition(1, 6),
                new ChessPosition(1, 2),
                new ChessPosition(6, 7),
                new ChessPosition(2, 7),
                new ChessPosition(6, 1),
                new ChessPosition(2, 1)
        );

        //when
        final List<ChessPosition> destinations = elephant.getDestinations(ChessPiecePositions.empty());

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
