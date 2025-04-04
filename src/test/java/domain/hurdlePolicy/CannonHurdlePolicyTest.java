package domain.hurdlePolicy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.chesspiece.Pawn;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonHurdlePolicyTest {

    @Test
    @DisplayName("포의 이동정책을 테스트 한다")
    void test1() {
        //given
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(
                List.of(new Pawn(ChessTeam.BLUE, new ChessPosition(4, 5)),
                        new Pawn(ChessTeam.RED, new ChessPosition(4, 6))));
        final List<Path> paths = List.of(
                new Path(List.of(new ChessPosition(4,5), new ChessPosition(4,6), new ChessPosition(4,7)))
        );

        //when
        final CannonHurdlePolicy cannonHurdlePolicy = new CannonHurdlePolicy();
        final List<ChessPosition> chessPositions = cannonHurdlePolicy.pickDestinations(ChessTeam.BLUE, paths,
                chessPiecePositions);

        //then
        assertThat(chessPositions).containsExactlyInAnyOrderElementsOf(List.of(new ChessPosition(4,6)));
    }

}
