package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.data.fixture.ChoPiecePositionFixture;
import janggi.data.fixture.HanPiecePositionFixture;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
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

        List<Piece> allPiecesInCho = janggiBoard.getPieces(CampType.CHO);
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

        List<Piece> allPiecesInHan = janggiBoard.getPieces(CampType.HAN);
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
                Arguments.of(PieceType.BYUNG, HanPiecePositionFixture.BYUNG_POSITIONS)
        );
    }

    @Test
    @DisplayName("장기말을 움직일 수 있다.")
    void canMovePiece() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        Position targetPosition = ChoPiecePositionFixture.CHA_POSITIONS.getFirst();
        Position destination = targetPosition.calculateSum(new Position(0, -1));
        MovePieceCommand moveCommand = new MovePieceCommand(1, CampType.CHO, targetPosition, destination);

        janggiBoard.movePiece(moveCommand);

        List<Piece> choPieces = janggiBoard.getPieces(CampType.CHO);
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
        MovePieceCommand moveCommand = new MovePieceCommand(1, CampType.CHO, targetPosition, new Position(5, 4));

        assertThatThrownBy(() -> janggiBoard.movePiece(moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("목적지가 유효하지 않은 경우 장기말을 움직일 수 없다.")
    void canNotMoveWithInvalidDestination() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        Position targetPosition = ChoPiecePositionFixture.CHA_POSITIONS.getFirst();
        Position destination = new Position(4, 4);
        MovePieceCommand moveCommand = new MovePieceCommand(1, CampType.CHO, targetPosition, destination);

        assertThatThrownBy(() -> janggiBoard.movePiece(moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @Test
    @DisplayName("상대방의 말을 잡을 수 있다.")
    void canKillEnemy() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 6), new Position(1, 6)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 9), new Position(0, 3)));

        List<Piece> killedPiecesInCho = janggiBoard.getKilledPieces(CampType.CHO);
        List<Piece> piecesInHan = janggiBoard.getPieces(CampType.HAN);
        List<Piece> byungInHan = piecesInHan.stream().filter(piece -> piece.checkPieceType(PieceType.BYUNG)).toList();

        assertAll(
                () -> assertThat(killedPiecesInCho).containsExactly(new Piece(PieceType.BYUNG, new Position(0, 3))),
                () -> assertThat(byungInHan).hasSize(4)
        );
    }

    @Test
    @DisplayName("현재 진영의 점수를 계산할 수 있다.")
    void canCalculateScore() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 6), new Position(1, 6)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 9), new Position(0, 3)));

        assertAll(
                () -> assertThat(janggiBoard.getScore(CampType.CHO)).isEqualTo(2),
                () -> assertThat(janggiBoard.getScore(CampType.HAN)).isEqualTo(0.5)
        );
    }

    @Test
    @DisplayName("궁이 잡혀 게임이 끝났는지 확인할 수 있다.")
    void checkGameEndByGung() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 9), new Position(0, 8)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 8), new Position(3, 8)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(3, 8), new Position(3, 1)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(3, 1), new Position(4, 1)));

        assertThat(janggiBoard.canContinueGame()).isFalse();
    }

    @Test
    @DisplayName("궁이 아무도 잡히지 않아 게임이 게속 이어지는 것을 확인할 수 있다.")
    void checkGameContinueBecauseAllGungAlive() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 9), new Position(0, 8)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 8), new Position(3, 8)));

        assertThat(janggiBoard.canContinueGame()).isTrue();
    }

    @Test
    @DisplayName("궁이 잡힌 경우 승패를 계산할 수 있다.")
    void checkWinningByGung() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 9), new Position(0, 8)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(0, 8), new Position(3, 8)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(3, 8), new Position(3, 1)));
        janggiBoard.movePiece(new MovePieceCommand(1, CampType.CHO, new Position(3, 1), new Position(4, 1)));

        assertThat(janggiBoard.whoWin()).isEqualTo(CampType.CHO);
    }

    @Test
    @DisplayName("게임이 중간에 종료된 경우 점수로 승패를 계산할 수 있다.")
    void checkWinningByScore() {
        JanggiBoard janggiBoard = new JanggiBoard(PieceAssignType.LEFT_SANG, PieceAssignType.LEFT_SANG);
        assertThat(janggiBoard.whoWin()).isEqualTo(CampType.HAN);
    }
}
