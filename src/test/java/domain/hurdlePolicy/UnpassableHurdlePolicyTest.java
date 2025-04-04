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

class UnpassableHurdlePolicyTest {

    @Test
    @DisplayName("경로에 장애물이 없고, 목적지에 적팀있는 경우를 테스트 한다")
    void test1() {
        //given
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(
                List.of(new Pawn(ChessTeam.BLUE, new ChessPosition(4, 5)),
                        new Pawn(ChessTeam.BLUE, new ChessPosition(4, 6))));
        final List<Path> paths = List.of(
                new Path(List.of(new ChessPosition(4,5)))
        );

        //when
        final UnpassableHurdlePolicy unpassableHurdlePolicy = new UnpassableHurdlePolicy();
        final List<ChessPosition> chessPositions = unpassableHurdlePolicy.pickDestinations(ChessTeam.RED, paths,
                chessPiecePositions);

        //then
        assertThat(chessPositions).containsExactlyInAnyOrderElementsOf(List.of(new ChessPosition(4,5)));

    }

    @Test
    @DisplayName("경로에 장애물이 있어 이동하지 못하는 경우를 테스트 한다")
    void test2() {
        //given
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(
                List.of(new Pawn(ChessTeam.BLUE, new ChessPosition(5, 5))));
        final List<Path> paths = List.of(
                new Path(List.of(new ChessPosition(5,5), new ChessPosition(3,6)))
        );

        //when
        final UnpassableHurdlePolicy unpassableHurdlePolicy = new UnpassableHurdlePolicy();
        final List<ChessPosition> chessPositions = unpassableHurdlePolicy.pickDestinations(ChessTeam.RED, paths,
                chessPiecePositions);

        //then
        assertThat(chessPositions).isEmpty();

    }

}
