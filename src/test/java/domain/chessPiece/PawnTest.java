package domain.chessPiece;

import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.position.EmptyChessPiecePositionsGenerator;
import domain.type.ChessTeam;
import domain.chessPiece.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {
    private final ChessPiecePositions emptyPositions = new ChessPiecePositions(new EmptyChessPiecePositionsGenerator());

    @Test
    @DisplayName("폰이 이동 가능한 경로를 반환한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(0, 0);
        final List<ChessPosition> expectDestinations = List.of(
                new ChessPosition(1,0),
                new ChessPosition(0,1)
        );

        //when
        final Pawn pawn = new Pawn(ChessTeam.RED);
        final List<ChessPosition> destinations = pawn.getDestinations(chessPosition, emptyPositions);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expectDestinations);
    }
}
