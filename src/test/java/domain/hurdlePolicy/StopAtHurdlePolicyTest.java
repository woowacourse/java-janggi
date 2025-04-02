package domain.hurdlePolicy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.chessPiece.Pawn;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StopAtHurdlePolicyTest {
    
    @Test
    @DisplayName("적팀 장애물을 만났을때 멈추는 정책을 테스트 한다")
    void test1() {
        //given
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(
                List.of(new Pawn(new ChessPosition(4, 5), ChessTeam.BLUE),
                        new Pawn(new ChessPosition(4, 6), ChessTeam.BLUE)));
        final List<Path> paths = List.of(
                new Path(List.of(new ChessPosition(4,4), new ChessPosition(4,5), new ChessPosition(4,6)))
        );

        //when
        final StopAtHurdlePolicy stopAtHurdlePolicy = new StopAtHurdlePolicy();
        final List<ChessPosition> chessPositions = stopAtHurdlePolicy.pickDestinations(ChessTeam.RED, paths,
                chessPiecePositions);

        //then
        assertThat(chessPositions).containsExactlyInAnyOrderElementsOf(List.of(new ChessPosition(4,4), new ChessPosition(4,5)));
        
    }

    @Test
    @DisplayName("아군 장애물을 만났을때 멈추는 정책을 테스트 한다")
    void test2() {
        //given
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(
                List.of(new Pawn(new ChessPosition(4, 5), ChessTeam.BLUE),
                        new Pawn(new ChessPosition(4, 6), ChessTeam.BLUE)));
        final List<Path> paths = List.of(
                new Path(List.of(new ChessPosition(4,4), new ChessPosition(4,5), new ChessPosition(4,6)))
        );

        //when
        final StopAtHurdlePolicy stopAtHurdlePolicy = new StopAtHurdlePolicy();
        final List<ChessPosition> chessPositions = stopAtHurdlePolicy.pickDestinations(ChessTeam.BLUE, paths,
                chessPiecePositions);

        //then
        assertThat(chessPositions).containsExactlyInAnyOrderElementsOf(List.of(new ChessPosition(4,4)));

    }

}
