package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드 초기화 테스트")
    public void boardInitTest() {
        // given & when
        Board board = Board.init(ElephantSetup.INNER_ELEPHANT_SETUP, ElephantSetup.INNER_ELEPHANT_SETUP);

        // then
        Map<Position, PieceType> pieces = board.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().getPieceType()
                ));

        assertThat(pieces).containsAllEntriesOf(Map.ofEntries(
                // ===== CHO =====
                Map.entry(Position.of(10, 1), PieceType.CHARIOT),
                Map.entry(Position.of(10, 9), PieceType.CHARIOT),
                Map.entry(Position.of(10, 2), PieceType.HORSE),
                Map.entry(Position.of(10, 3), PieceType.ELEPHANT),
                Map.entry(Position.of(10, 4), PieceType.GUARD),
                Map.entry(Position.of(9, 5), PieceType.GENERAL),
                Map.entry(Position.of(10, 6), PieceType.GUARD),
                Map.entry(Position.of(10, 7), PieceType.ELEPHANT),
                Map.entry(Position.of(10, 8), PieceType.HORSE),
                Map.entry(Position.of(8, 2), PieceType.CANNON),
                Map.entry(Position.of(8, 8), PieceType.CANNON),
                Map.entry(Position.of(7, 1), PieceType.SOLDIER),
                Map.entry(Position.of(7, 3), PieceType.SOLDIER),
                Map.entry(Position.of(7, 5), PieceType.SOLDIER),
                Map.entry(Position.of(7, 7), PieceType.SOLDIER),
                Map.entry(Position.of(7, 9), PieceType.SOLDIER),

                // ===== HAN =====
                Map.entry(Position.of(1, 1), PieceType.CHARIOT),
                Map.entry(Position.of(1, 2), PieceType.HORSE),
                Map.entry(Position.of(1, 3), PieceType.ELEPHANT),
                Map.entry(Position.of(1, 4), PieceType.GUARD),
                Map.entry(Position.of(2, 5), PieceType.GENERAL),
                Map.entry(Position.of(1, 6), PieceType.GUARD),
                Map.entry(Position.of(1, 7), PieceType.ELEPHANT),
                Map.entry(Position.of(1, 8), PieceType.HORSE),
                Map.entry(Position.of(1, 9), PieceType.CHARIOT),
                Map.entry(Position.of(3, 2), PieceType.CANNON),
                Map.entry(Position.of(3, 8), PieceType.CANNON),
                Map.entry(Position.of(4, 1), PieceType.SOLDIER),
                Map.entry(Position.of(4, 3), PieceType.SOLDIER),
                Map.entry(Position.of(4, 5), PieceType.SOLDIER),
                Map.entry(Position.of(4, 7), PieceType.SOLDIER),
                Map.entry(Position.of(4, 9), PieceType.SOLDIER)
        ));
    }

    @Test
    @DisplayName("기물 이동 위치 계산 테스트")
    public void getMovablePositionsTest() {
        Board board = Board.init(ElephantSetup.INNER_ELEPHANT_SETUP, ElephantSetup.INNER_ELEPHANT_SETUP);
        Position from = Position.of(7, 1);
        List<Position> movablePositions = board.getMovablePositions(from);

        assertThat(movablePositions).isNotEmpty();
    }

    @Test
    @DisplayName("기물 이동 테스트")
    public void moveTest() {
        Board board = Board.init(ElephantSetup.INNER_ELEPHANT_SETUP, ElephantSetup.INNER_ELEPHANT_SETUP);
        Position from = Position.of(7, 1);
        Position to = Position.of(8, 1);

        board.move(from, to, Team.CHO);

        Map<Position, Piece> pieces = board.getPieces();
        assertThat(pieces.containsKey(from)).isFalse();
        assertThat(pieces.containsKey(to)).isTrue();
    }
}
