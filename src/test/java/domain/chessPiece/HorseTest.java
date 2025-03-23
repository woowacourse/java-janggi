package domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import domain.chessPiece.ChessPiece;
import domain.chessPiece.Horse;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositions;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.position.EmptyChessPiecePositionsGenerator;
import domain.type.ChessTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {
    private final ChessPosition horsePosition = new ChessPosition(4, 4);
    private final Horse horse = new Horse(ChessTeam.BLUE);

    @Test
    @DisplayName("마의 이동 경로를 반환한다")
    void test1() {
        //given
        ChessPiecePositions emptyPositions = new ChessPiecePositions(new EmptyChessPiecePositionsGenerator());
        final List<ChessPosition> expected = List.of(
                new ChessPosition(6, 5),
                new ChessPosition(6, 3),
                new ChessPosition(2, 3),
                new ChessPosition(2, 5),
                new ChessPosition(5, 6),
                new ChessPosition(3, 6),
                new ChessPosition(3, 2),
                new ChessPosition(5, 2));

        //when
        final List<ChessPosition> destinations = horse.getDestinations(horsePosition, emptyPositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static class ExistHurdlePositionsGenerator implements ChessPiecePositionsGenerator {
        @Override
        public Map<ChessPosition, ChessPiece> generate() {
            return Map.of(
                    new ChessPosition(3, 4), new Pawn(ChessTeam.RED),
                    new ChessPosition(5, 2), new Pawn(ChessTeam.BLUE)
            );
        }
    }

    @Test
    @DisplayName("장애물이 있을때 마의 이동 경로를 계산한다")
    void test2() {
        //given
        final ChessPiecePositions piecePositions = new ChessPiecePositions(new ExistHurdlePositionsGenerator());
        final List<ChessPosition> expected = List.of(new ChessPosition(3, 2), new ChessPosition(3, 6), new ChessPosition(6, 3), new ChessPosition(6, 5), new ChessPosition(5, 6));

        //when
        final List<ChessPosition> destinations = horse.getDestinations(horsePosition, piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
