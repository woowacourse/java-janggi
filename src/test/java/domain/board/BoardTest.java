package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.setup.Coordinate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Board 클래스 테스트")
class BoardTest {

    private Board initialBoard;

    @BeforeEach
    void setUp() {
        Arrangements arrangements = new Arrangements()
                .assignArrangement(Team.HAN, Arrangement.MASANGMASANG)
                .assignArrangement(Team.CHO, Arrangement.MASANGMASANG);
        initialBoard = Board.of(arrangements);
    }

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Coordinate coord(String input) {
        return Coordinate.toCoordinate(input);
    }

    @Test
    @DisplayName("pieceAt: 기물이 있는 위치에서 해당 기물을 반환한다")
    void pieceAtReturnsExistingPiece() {
        Piece piece = initialBoard.pieceAt(pos(Column.A, Row.ZERO));

        assertThat(piece).isNotNull();
        assertThat(piece.isChariot()).isTrue();
        assertThat(piece.isOwnedBy(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("isEmpty: 빈 위치에서 true를 반환한다")
    void isEmptyReturnsTrueForEmptyPosition() {
        assertThat(initialBoard.isEmpty(pos(Column.A, Row.FOUR))).isTrue();
        assertThat(initialBoard.isEmpty(pos(Column.E, Row.FIVE))).isTrue();
    }

    @Test
    @DisplayName("isEmpty: 기물 있는 위치에서 false를 반환한다")
    void isEmptyReturnsFalseForOccupiedPosition() {
        assertThat(initialBoard.isEmpty(pos(Column.A, Row.ZERO))).isFalse();
        assertThat(initialBoard.isEmpty(pos(Column.E, Row.ONE))).isFalse();
    }

    @Test
    @DisplayName("move: 출발 좌표에 이동 가능한 기물이 없을 시 예외를 던진다")
    void moveFromEmptyPositionThrowsException() {
        assertThatThrownBy(() -> initialBoard.move(coord("a4 a5"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("move: 상대 팀 기물 이동 시도 시 예외를 던진다")
    void moveOpponentPieceThrowsException() {
        assertThatThrownBy(() -> initialBoard.move(coord("a9 a8"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("move: 현재 팀의 기물을 이동 가능한 빈 칸으로 이동시킨다")
    void moveToAvailableEmptyTarget() {
        Board moved = initialBoard.move(coord("a0 a1"), Team.HAN);

        assertThat(moved.isEmpty(pos(Column.A, Row.ZERO))).isTrue();
        assertThat(moved.findPieceByPosition(pos(Column.A, Row.ONE))).isPresent();
    }

    @Test
    @DisplayName("availableTargetsOf: 아군 기물이 있는 도착지는 제외한다")
    void availableTargetsExcludeFriendlyOccupiedPositions() {
        List<Position> availableTargets = initialBoard.findAvailableTargetPositions(pos(Column.A, Row.ZERO));

        assertThat(availableTargets).contains(pos(Column.A, Row.ONE));
        assertThat(availableTargets).doesNotContain(pos(Column.B, Row.ZERO));
    }

}
