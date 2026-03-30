package domain.board;

import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.setup.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Board 클래스 테스트")
class BoardTest {

    private Board initialBoard;
    private Turn hanTurn;
    private Turn choTurn;

    @BeforeEach
    void setUp() {
        Arrangements arrangements = new Arrangements()
                .assignArrangement(Team.HAN, Arrangement.MASANGMASANG)
                .assignArrangement(Team.CHO, Arrangement.MASANGMASANG);
        initialBoard = Board.of(arrangements);
        hanTurn = new Turn(Team.HAN);
        choTurn = new Turn(Team.CHO);
    }

    private Position pos(Col col, Row row) {
        return new Position(col, row);
    }

    private Coordinate coord(String input) {
        return Coordinate.toCoordinate(input);
    }

    @Test
    @DisplayName("pieceAt: 기물이 있는 위치에서 해당 기물을 반환한다")
    void pieceAtReturnsExistingPiece() {
        Piece piece = initialBoard.pieceAt(pos(Col.A, Row.ZERO)).orElseThrow();

        assertThat(piece).isNotNull();
        assertThat(piece.isChariot()).isTrue();
        assertThat(piece.isOwnedBy(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("isEmpty: 빈 위치에서 true를 반환한다")
    void isEmptyReturnsTrueForEmptyPosition() {
        assertThat(initialBoard.isEmpty(pos(Col.A, Row.FOUR))).isTrue();
        assertThat(initialBoard.isEmpty(pos(Col.E, Row.FIVE))).isTrue();
    }

    @Test
    @DisplayName("isEmpty: 기물 있는 위치에서 false를 반환한다")
    void isEmptyReturnsFalseForOccupiedPosition() {
        assertThat(initialBoard.isEmpty(pos(Col.A, Row.ZERO))).isFalse();
        assertThat(initialBoard.isEmpty(pos(Col.E, Row.ONE))).isFalse();
    }

    @Test
    @DisplayName("hasAnyPiece: 기물 있는 위치에서 true를 반환한다")
    void hasAnyPieceReturnsTrueForOccupiedPosition() {
        assertThat(initialBoard.hasAnyPiece(pos(Col.A, Row.ZERO))).isTrue();
    }

    @Test
    @DisplayName("hasAnyPiece: 해당 위치에 기물이 없으면 false를 반환한다")
    void hasAnyPieceReturnsFalseForEmptyPosition() {
        assertThat(initialBoard.hasAnyPiece(pos(Col.A, Row.FOUR))).isFalse();
    }

    @Test
    @DisplayName("hasFriendOf: 같은 팀 기물이 있으면 true를 반환한다")
    void hasFriendOfReturnsTrueForSameTeamPiece() {
        Piece hanChariot = new Piece(Team.HAN, PieceType.CHARIOT);
        assertThat(initialBoard.hasFriendOf(pos(Col.D, Row.ZERO), hanChariot)).isTrue();
    }

    @Test
    @DisplayName("hasFriendOf: 다른 팀 기물이 있으면 false를 반환한다")
    void hasFriendOfReturnsFalseForEnemyPiece() {
        Piece hanChariot = new Piece(Team.HAN, PieceType.CHARIOT);
        assertThat(initialBoard.hasFriendOf(pos(Col.A, Row.NINE), hanChariot)).isFalse();
    }

    @Test
    @DisplayName("hasEnemyOf: 다른 팀 기물이 있으면 true를 반환한다")
    void hasEnemyOfReturnsTrueForEnemyPiece() {
        Piece hanChariot = new Piece(Team.HAN, PieceType.CHARIOT);
        assertThat(initialBoard.hasEnemyOf(pos(Col.A, Row.NINE), hanChariot)).isTrue();
    }

    @Test
    @DisplayName("hasEnemyOf: 같은 팀 기물이 있으면 false를 반환한다")
    void hasEnemyOfReturnsFalseForSameTeamPiece() {
        Piece hanChariot = new Piece(Team.HAN, PieceType.CHARIOT);
        assertThat(initialBoard.hasEnemyOf(pos(Col.D, Row.ZERO), hanChariot)).isFalse();
    }

    @Test
    @DisplayName("move: 출발 좌표에 이동 가능한 기물이 없을 시 예외를 던진다")
    void moveFromEmptyPositionThrowsException() {
        assertThatThrownBy(() -> initialBoard.move(coord("a4 a5"), hanTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("move: 상대 팀 기물 이동 시도 시 예외를 던진다")
    void moveOpponentPieceThrowsException() {
        assertThatThrownBy(() -> initialBoard.move(coord("a9 a8"), hanTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

}
