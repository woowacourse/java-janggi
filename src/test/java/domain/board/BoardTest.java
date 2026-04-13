package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Destinations;
import domain.game.Position;
import domain.game.Side;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 선택한_포메이션에_맞게_32개의_기물이_초기_위치에_정확히_배치된다() {
        // LEFT(상마상마), RIGHT(마상마상) 포메이션으로 초기화
        Board board = BoardFactory.create(Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        Map<Position, Piece> actual = board.getBoard();

        assertThat(actual).hasSize(32);

        // 궁성 중앙 (4,1), (4,8)에 궁(General) 배치 확인
        assertThat(actual.get(Position.of(4, 1))).isInstanceOf(General.class);
        assertThat(actual.get(Position.of(4, 8))).isInstanceOf(General.class);

        // 네 귀퉁이에 차(Chariot) 배치 확인
        assertThat(actual.get(Position.of(0, 0))).isInstanceOf(Chariot.class);
        assertThat(actual.get(Position.of(8, 0))).isInstanceOf(Chariot.class);
        assertThat(actual.get(Position.of(0, 9))).isInstanceOf(Chariot.class);
        assertThat(actual.get(Position.of(8, 9))).isInstanceOf(Chariot.class);

        // 초나라(y=0) LEFT_ELEPHANT: 상(1), 마(2), 상(6), 마(7)
        assertThat(actual.get(Position.of(1, 0))).isInstanceOf(Elephant.class);
        assertThat(actual.get(Position.of(2, 0))).isInstanceOf(Horse.class);
        assertThat(actual.get(Position.of(6, 0))).isInstanceOf(Elephant.class);
        assertThat(actual.get(Position.of(7, 0))).isInstanceOf(Horse.class);

        // 한나라(y=9) RIGHT_ELEPHANT: 마(2), 상(1), 마(7), 상(6)
        assertThat(actual.get(Position.of(2, 9))).isInstanceOf(Horse.class);
        assertThat(actual.get(Position.of(1, 9))).isInstanceOf(Elephant.class);
        assertThat(actual.get(Position.of(7, 9))).isInstanceOf(Horse.class);
        assertThat(actual.get(Position.of(6, 9))).isInstanceOf(Elephant.class);
    }

    @Test
    void 양쪽_장군이_모두_있으면_게임은_종료되지_않는다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(4, 1), PieceFactory.createGeneral(Side.CHO));
        pieces.put(Position.of(4, 8), PieceFactory.createGeneral(Side.HAN));
        Board board = new Board(pieces);

        assertThat(board.isGameOver()).isFalse();
    }

    @Test
    void 장군이_하나만_남으면_게임이_종료된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(4, 1), PieceFactory.createGeneral(Side.CHO));
        Board board = new Board(pieces);

        assertThat(board.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("목적지에 적군 기물이 있으면 보드에서 해당 기물을 제거하고 이동한다")
    void captureEnemy() {
        // Given: (0, 3)에 초나라 졸, (0, 4)에 한나라 졸 배치
        Position source = Position.of(0, 3);
        Position target = Position.of(0, 4);
        Piece choSoldier = PieceFactory.createSoldier(Side.CHO);
        Piece hanSoldier = PieceFactory.createSoldier(Side.HAN);

        Board board = new Board(Map.of(
                source, choSoldier,
                target, hanSoldier
        ));

        // When: (0, 3)의 초나라 졸이 (0, 4)의 한나라 졸을 잡음
        Board movedBoard = board.movePiece(source, target);

        // Then: 출발지는 비어있고, 도착지에는 초나라 졸이 위치함
        assertThat(movedBoard.isEmpty(source)).isTrue();
        assertThat(movedBoard.getPiece(target)).isSameAs(choSoldier);
    }

    @Test
    @DisplayName("기물이 존재하지 않는 빈 좌표를 출발지로 입력하면 예외가 발생한다")
    void moveEmptySource() {
        // Given: 비어있는 보드
        Board board = new Board(Map.of());
        Position emptyPos = Position.of(0, 0);
        Position target = Position.of(0, 1);

        // When & Then: 빈 좌표를 선택하여 이동을 시도하거나 이동 가능한 위치를 찾을 때 예외 발생
        assertThatThrownBy(() -> board.findDestinations(emptyPos))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.movePiece(emptyPos, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("목적지에 아군 기물이 있는지는 이동 가능 경로 탐색 단계에서 필터링된다")
    void validateAllyAtDestination() {
        // Given: (0, 0)에 초나라 차, (0, 1)에 초나라 졸 배치
        Position chariotPos = Position.of(0, 0);
        Position allyPos = Position.of(0, 1);
        Board board = new Board(Map.of(
                chariotPos, PieceFactory.createChariot(Side.CHO),
                allyPos, PieceFactory.createSoldier(Side.CHO)
        ));

        // When: 차(Chariot)의 이동 가능 위치 탐색
        Destinations destinations = board.findDestinations(chariotPos);

        // Then: 아군이 있는 (0, 1)은 목적지 목록에 포함되지 않음
        assertThat(destinations.getPositions()).doesNotContain(allyPos);
    }
}
