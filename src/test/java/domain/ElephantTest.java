package domain;

import domain.chessPiece.ChessPiece;
import domain.chessPiece.Elephant;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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

    @DisplayName("다른 기물이 있는 경우, 모든 목적지를 반환할 수 있다.")
    @Test
    void existOtherPieces() {
        // given
        final ChessPosition elephantPosition = new ChessPosition(4, 4);
        final Elephant elephant = new Elephant(elephantPosition, ChessTeam.BLUE);

        final Map<ChessPosition, ChessPiece> chessPositionPawnMap = Map.of(
                new ChessPosition(2, 5), new Pawn(new ChessPosition(2, 5), ChessTeam.RED),
                new ChessPosition(1, 2), new Pawn(new ChessPosition(1, 2), ChessTeam.BLUE),
                new ChessPosition(7, 2), new Pawn(new ChessPosition(7, 2), ChessTeam.RED)
        );
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(chessPositionPawnMap);

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7, 6),
                new ChessPosition(7, 2),
                new ChessPosition(6, 7),
                new ChessPosition(2, 7),
                new ChessPosition(6, 1),
                new ChessPosition(2, 1)
        );

        //when
        final List<ChessPosition> destinations = elephant.getDestinations(piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
