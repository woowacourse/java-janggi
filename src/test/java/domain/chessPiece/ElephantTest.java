package domain.chessPiece;

import domain.chessPiece.ChessPiece;
import domain.chessPiece.Elephant;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositions;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.position.EmptyChessPiecePositionsGenerator;
import domain.type.ChessTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private final ChessPosition elephantPosition = new ChessPosition(4, 4);
    private final Elephant elephant = new Elephant(ChessTeam.BLUE);

    @DisplayName("다른 기물이 없는 경우, 모든 목적지를 반환할 수 있다.")
    @Test
    void notExistOtherPieces() {
        // given
        ChessPiecePositions emptyPositions = new ChessPiecePositions(new EmptyChessPiecePositionsGenerator());

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
        final List<ChessPosition> destinations = elephant.getDestinations(elephantPosition, emptyPositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static class FakeChessPositionsGenerator implements ChessPiecePositionsGenerator {
        @Override
        public Map<ChessPosition, ChessPiece> generate() {
            return Map.of(
                    new ChessPosition(2, 5), new Pawn(ChessTeam.RED),
                    new ChessPosition(1, 2), new Pawn(ChessTeam.BLUE),
                    new ChessPosition(7, 2), new Pawn(ChessTeam.RED)
            );
        }
    }

    @DisplayName("다른 기물이 있는 경우, 모든 목적지를 반환할 수 있다.")
    @Test
    void existOtherPieces() {
        // given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new FakeChessPositionsGenerator());

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7, 6),
                new ChessPosition(7, 2),
                new ChessPosition(6, 7),
                new ChessPosition(2, 7),
                new ChessPosition(6, 1),
                new ChessPosition(2, 1)
        );

        //when
        final List<ChessPosition> destinations = elephant.getDestinations(elephantPosition, piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
