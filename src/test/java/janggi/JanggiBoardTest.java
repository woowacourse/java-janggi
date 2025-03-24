package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.JanggiBoard;
import janggi.fixture.ChoPiecePositionFixture;
import janggi.fixture.HanPiecePositionFixture;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiBoardTest {

    @ParameterizedTest
    @DisplayName("초의 초기배치를 할 수 있다.")
    @MethodSource()
    void canAssignChoPiece(PieceType pieceType, List<Position> expectedPositions) {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);

        List<Piece> allPiecesInCho = janggiBoard.getChoPieces();
        List<Piece> piecesByType = allPiecesInCho.stream().filter(piece -> piece.checkPieceType(pieceType)).toList();

        assertThat(piecesByType).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    static Stream<Arguments> canAssignChoPiece() {
        return Stream.of(
                Arguments.of(PieceType.GUNG, ChoPiecePositionFixture.GUNG_POSITIONS),
                Arguments.of(PieceType.CHA, ChoPiecePositionFixture.CHA_POSITIONS),
                Arguments.of(PieceType.SA, ChoPiecePositionFixture.SA_POSITIONS),
                Arguments.of(PieceType.PO, ChoPiecePositionFixture.PO_POSITIONS),
                Arguments.of(PieceType.MA, ChoPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.SANG, ChoPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.JOL, ChoPiecePositionFixture.JOL_POSITIONS)
        );
    }

    @ParameterizedTest
    @DisplayName("한의 초기배치를 할 수 있다.")
    @MethodSource()
    void canAssignHanPiece(PieceType pieceType, List<Position> expectedPositions) {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);

        List<Piece> allPiecesInHan = janggiBoard.getHanPieces();
        List<Piece> piecesByType = allPiecesInHan.stream().filter(piece -> piece.checkPieceType(pieceType)).toList();

        assertThat(piecesByType).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    static Stream<Arguments> canAssignHanPiece() {
        return Stream.of(
                Arguments.of(PieceType.GUNG, HanPiecePositionFixture.GUNG_POSITIONS),
                Arguments.of(PieceType.CHA, HanPiecePositionFixture.CHA_POSITIONS),
                Arguments.of(PieceType.SA, HanPiecePositionFixture.SA_POSITIONS),
                Arguments.of(PieceType.PO, HanPiecePositionFixture.PO_POSITIONS),
                Arguments.of(PieceType.MA, HanPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.SANG, HanPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.JOL, HanPiecePositionFixture.JOL_POSITIONS)
        );
    }

    @Test
    @DisplayName("장기말을 움직일 수 있다.")
    void canMovePiece() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        Position targetPosition = ChoPiecePositionFixture.CHA_POSITIONS.getFirst();
        Position destination = targetPosition.calculateSum(new Position(0, -1));

        janggiBoard.movePiece(CampType.CHO, targetPosition, destination);

        List<Piece> choPieces = janggiBoard.getChoPieces();
        assertThat(choPieces)
                .filteredOn(piece -> piece.checkPieceType(PieceType.CHA))
                .map(Piece::getPosition)
                .contains(destination);
    }

    @Test
    @DisplayName("움직일 대상이 없으면 장기말을 움직일 수 없다.")
    void canNotMoveWithInvalidTargetPosition() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        Position targetPosition = new Position(4, 4);

        assertThatThrownBy(() -> janggiBoard.movePiece(CampType.CHO, targetPosition, new Position(5, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("목적지가 유효하지 않은 경우 장기말을 움직일 수 없다.")
    void canNotMoveWithInvalidDestination() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        Position targetPosition = ChoPiecePositionFixture.CHA_POSITIONS.getFirst();
        Position destination = new Position(4, 4);

        assertThatThrownBy(() -> janggiBoard.movePiece(CampType.CHO, targetPosition, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }
}
