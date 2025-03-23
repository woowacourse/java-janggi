package domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import domain.chessPiece.Cannon;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositions;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {
    private final ChessPosition chessPosition = new ChessPosition(7, 4);
    private final Cannon cannon = new Cannon(ChessTeam.BLUE);

    private class NotExistCannonPositionsGenerator implements ChessPiecePositionsGenerator {
        @Override
        public Map<ChessPosition, ChessPiece> generate() {
            return Map.of(
                    chessPosition, cannon,
                    new ChessPosition(6, 4), new Pawn(ChessTeam.RED),
                    new ChessPosition(2, 4), new Pawn(ChessTeam.RED)
            );
        }
    }

    @Test
    @DisplayName("상대방의 포가 존재하지 않을때 포의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new NotExistCannonPositionsGenerator());
        final List<ChessPosition> expected = List.of(
                new ChessPosition(2, 4),
                new ChessPosition(3, 4),
                new ChessPosition(4, 4),
                new ChessPosition(5, 4)
        );

        //when
        final List<ChessPosition> destinations = cannon.getDestinations(chessPosition, piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    private class ExistCannonPositionsGenerator implements ChessPiecePositionsGenerator {
        @Override
        public Map<ChessPosition, ChessPiece> generate() {
            return Map.of(
                    chessPosition, cannon,
                    new ChessPosition(6, 4), new Cannon(ChessTeam.RED),
                    new ChessPosition(2, 4), new Pawn(ChessTeam.RED),
                    new ChessPosition(7,5), new Pawn(ChessTeam.RED)
            );
        }
    }

    @Test
    @DisplayName("상대방의 포가 존재할때 포의 이동 경로를 계산한다")
    void test2() {
        //given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new ExistCannonPositionsGenerator());

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7,6),
                new ChessPosition(7,7),
                new ChessPosition(7,8)
        );

        //when
        final List<ChessPosition> destinations = cannon.getDestinations(chessPosition, piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

}
