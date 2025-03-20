package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    @DisplayName("마의 이동 경로를 반환한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(4, 4);
        final List<ChessPosition> expected = List.of(new ChessPosition(6, 5),
                new ChessPosition(6, 3),
                new ChessPosition(2, 3),
                new ChessPosition(2, 5),
                new ChessPosition(5, 6),
                new ChessPosition(3, 6),
                new ChessPosition(3, 2),
                new ChessPosition(5, 2));

        //when
        final Horse horse = new Horse(chessPosition, ChessTeam.BLUE);
        final List<ChessPosition> destinations = horse.getDestinations(ChessPiecePositions.empty());

        //then
        assertThat(destinations).containsAll(expected);
    }

    @Test
    @DisplayName("장애물이 있을때 마의 이동 경로를 계산한다")
    void test2() {
        //given
        final ChessPosition chessPosition = new ChessPosition(4, 4);
        final Map<ChessPosition, ChessPiece> chessPositionPawnMap = Map.of(new ChessPosition(3, 4),
                new Pawn(new ChessPosition(3, 4), ChessTeam.RED),
                new ChessPosition(5, 2), new Pawn(new ChessPosition(5, 2), ChessTeam.BLUE));
        final List<ChessPosition> expected = List.of(new ChessPosition(3, 2), new ChessPosition(3, 6),
                new ChessPosition(5, 2), new ChessPosition(6, 3), new ChessPosition(6, 5), new ChessPosition(5, 6));
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(chessPositionPawnMap);
        //when
        final Horse horse = new Horse(chessPosition, ChessTeam.BLUE);
        final List<ChessPosition> destinations = horse.getDestinations(piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);

    }

}
