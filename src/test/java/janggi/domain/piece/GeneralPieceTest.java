package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralPieceTest {

    @ParameterizedTest
    @DisplayName("궁은 상하좌우 한 칸씩 이동 가능하다.")
    @CsvSource({
            "5,2,6,2",
            "5,2,4,2",
            "5,2,5,3",
            "5,2,5,1"
    })
    void testMoveGeneral(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(generalPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("궁은 상하좌우 두 칸 이상 이동하지 못한다.")
    @CsvSource({
            "5,2,7,2",
            "5,2,4,3",
            "5,2,5,4",
            "5,2,3,2"
    })
    void testNotMovableGeneral(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(generalPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "5,2,6,2",
            "5,2,4,2",
            "5,2,5,3",
            "5,2,5,1"
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        List<Position> path = generalPiece.findPath(new Position(preX, preY), new Position(nextX, nextY));
        assertThat(path).containsExactly(new Position(nextX, nextY));
    }

}
