package domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import java.util.List;
import java.util.Map;

import domain.chessPiece.Chariot;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositions;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    private final ChessPosition chariotPosition = new ChessPosition(7, 4);
    private final Chariot chariot = new Chariot(ChessTeam.BLUE);

    private class FakeChessPositionsGenerator implements ChessPiecePositionsGenerator {
        @Override
        public Map<ChessPosition, ChessPiece> generate() {
            return Map.of(
//                    new ChessPosition(2, 4), new Pawn(ChessTeam.RED),
//                    new ChessPosition(7, 3), new Pawn(ChessTeam.RED),
//                    new ChessPosition(7, 5), new Pawn(ChessTeam.RED),
//                    new ChessPosition(7, 8), new Pawn(ChessTeam.RED)
            );
        }
    }

    @Test
    @DisplayName("차의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new FakeChessPositionsGenerator());
        final List<ChessPosition> expected = List.of(
                new ChessPosition(2, 4),
                new ChessPosition(3, 4),
                new ChessPosition(4, 4),
                new ChessPosition(5, 4),
                new ChessPosition(6,4),
                new ChessPosition(7, 3),
                new ChessPosition(7,5),
                new ChessPosition(8,4),
                new ChessPosition(9,4)
        );

        //when

        final List<ChessPosition> destinations = chariot.getDestinations(chariotPosition, piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("궁안에 차의 이동 경로를 계산하여 반환한다")
    void test2() {
        //given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new FakeChessPositionsGenerator());
        final ChessPosition chessPosition = new ChessPosition(2, 3);

        //when
        final Chariot chariot1 = new Chariot(ChessTeam.BLUE);
        final List<Path> coordinatePaths = chariot1.getCoordinatePaths(chessPosition);
        final HurdlePolicy hurdlePolicy = chariot1.getHurdlePolicy();
        final List<ChessPosition> chessPositions = hurdlePolicy.pickDestinations(ChessTeam.BLUE, coordinatePaths,
                piecePositions);

        System.out.println(chessPositions);

    }
}
