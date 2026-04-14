package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;

import java.util.Map;

import janggi.domain.vo.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardInitializerTest {
    private Map<Position, Piece> board = BoardInitializer.createBoard();

    @ParameterizedTest(name = "{0} 나라의 {1} 개수는 {2}개여야 한다")
    @CsvSource({
            "HAN, SOLDIER, 5",
            "CHO, SOLDIER, 5",
            "HAN, CANNON, 2",
            "CHO, CANNON, 2",
            "HAN, KING, 1",
            "CHO, KING, 1",
            "HAN, TANK, 2",
            "CHO, TANK, 2",
            "HAN, HORSE, 2",
            "CHO, HORSE, 2",
            "HAN, ELEPHANT, 2",
            "CHO, ELEPHANT, 2",
            "HAN, ADVISOR, 2",
            "CHO, ADVISOR, 2"
    })
    void 생성기물_개수_테스트(Team team, PieceType pieceType, int expectedCount) {
        long actualCount = board.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .filter(piece -> piece.pieceType() == pieceType)
                .count();

        assertThat(actualCount).isEqualTo(expectedCount);
    }
}
