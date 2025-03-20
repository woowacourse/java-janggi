import domain.ChessPiecePositions;
import domain.ChessPosition;
import domain.ChessTeam;
import domain.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {

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
        final Pawn pawn = new Pawn(chessPosition, ChessTeam.RED);
        final List<ChessPosition> destinations = pawn.getDestinations(ChessPiecePositions.empty());

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expectDestinations);
    }
}
