package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    @DisplayName("차의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(7, 4);
        final Map<ChessPosition, ChessPiece> chessPositionPawnMap = Map.of(
                new ChessPosition(2, 4), new Pawn(new ChessPosition(2, 4), ChessTeam.RED),
                new ChessPosition(7, 3), new Pawn(new ChessPosition(2, 4), ChessTeam.RED),
                new ChessPosition(7, 5), new Pawn(new ChessPosition(2, 4), ChessTeam.RED),
                new ChessPosition(7, 8), new Pawn(new ChessPosition(2, 4), ChessTeam.RED));
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(chessPositionPawnMap);
        final List<ChessPosition> expected = List.of(
                new ChessPosition(2, 4),
                new ChessPosition(3, 4),
                new ChessPosition(4, 4),
                new ChessPosition(5, 4),
                new ChessPosition(6,4),
                new ChessPosition(7, 3),
                new ChessPosition(7,5),
                new ChessPosition(8,4),
                new ChessPosition(9,4));

        //when
        final Chariot chariot = new Chariot(chessPosition, ChessTeam.RED);
        final List<ChessPosition> destinations = chariot.getDestinations(piecePositions);

        //then
        assertThat(destinations).containsAll(expected);
    }
}
