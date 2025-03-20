package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    @DisplayName("상대방의 포가 존재하지 않을때 포의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(7, 4);
        final Map<ChessPosition, ChessPiece> chessPositionPawnMap = Map.of(new ChessPosition(6, 4),
                new Pawn(new ChessPosition(6, 4), ChessTeam.RED),
                new ChessPosition(2, 4), new Pawn(new ChessPosition(2, 4), ChessTeam.RED));
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(chessPositionPawnMap);
        final List<ChessPosition> expected = List.of(new ChessPosition(3, 4), new ChessPosition(4, 4),
                new ChessPosition(5, 4));

        //when
        final Cannon cannon = new Cannon(chessPosition, ChessTeam.BLUE);
        final List<ChessPosition> destinations = cannon.getDestinations(piecePositions);

        //then
        assertThat(destinations).containsAll(expected);
    }

    @Test
    @DisplayName("상대방의 포가 존재할때 포의 이동 경로를 계산한다")
    void test2() {
        //given
        final ChessPosition cannonPosition = new ChessPosition(7, 4);
        final Cannon cannon = new Cannon(cannonPosition, ChessTeam.BLUE);

        final Map<ChessPosition, ChessPiece> chessPositionPawnMap = Map.of(
                new ChessPosition(7, 4), cannon,
                new ChessPosition(6, 4), new Pawn(new ChessPosition(6, 4), ChessTeam.RED),
                new ChessPosition(2, 4), new Pawn(new ChessPosition(2, 4), ChessTeam.RED),
                new ChessPosition(7,5), new Pawn(new ChessPosition(7, 5), ChessTeam.RED));
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(chessPositionPawnMap);

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7,6),
                new ChessPosition(7,7),
                new ChessPosition(7,8),
                new ChessPosition(5, 4),
                new ChessPosition(4, 4),
                new ChessPosition(3, 4),
                new ChessPosition(2, 4)
        );

        //when
        final List<ChessPosition> destinations = cannon.getDestinations(piecePositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

}
