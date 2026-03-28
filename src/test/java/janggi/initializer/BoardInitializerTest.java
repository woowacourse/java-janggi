package janggi.initializer;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Sang;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.provider.Arguments;

class BoardInitializerTest {
    private static final Map<Position, Class<? extends Piece>> initialHanPiecePosition = Map.ofEntries(
            Map.entry(new Position(1, 1), Cha.class),
            Map.entry(new Position(1, 4), Sa.class),
            Map.entry(new Position(1, 6), Sa.class),
            Map.entry(new Position(1, 9), Cha.class),
            Map.entry(new Position(2, 5), Gung.class),
            Map.entry(new Position(3, 2), Po.class),
            Map.entry(new Position(3, 8), Po.class),
            Map.entry(new Position(4, 1), Pawn.class),
            Map.entry(new Position(4, 3), Pawn.class),
            Map.entry(new Position(4, 5), Pawn.class),
            Map.entry(new Position(4, 7), Pawn.class),
            Map.entry(new Position(4, 9), Pawn.class)
    );

    private static final Map<Position, Class<? extends Piece>> initialChoPiecePosition = Map.ofEntries(
            Map.entry(new Position(10, 1), Cha.class),
            Map.entry(new Position(10, 4), Sa.class),
            Map.entry(new Position(10, 6), Sa.class),
            Map.entry(new Position(10, 9), Cha.class),
            Map.entry(new Position(9, 5), Gung.class),
            Map.entry(new Position(8, 2), Po.class),
            Map.entry(new Position(8, 8), Po.class),
            Map.entry(new Position(7, 1), Pawn.class),
            Map.entry(new Position(7, 3), Pawn.class),
            Map.entry(new Position(7, 5), Pawn.class),
            Map.entry(new Position(7, 7), Pawn.class),
            Map.entry(new Position(7, 9), Pawn.class)
    );

    private static final List<Position> initialHanMaSangPosition = List.of(
            new Position(1, 2),
            new Position(1, 3),
            new Position(1, 7),
            new Position(1, 8)
    );

    private static final List<Position> initialChoMaSangPosition = List.of(
            new Position(10, 2),
            new Position(10, 3),
            new Position(10, 7),
            new Position(10, 8)
    );

    @Test
    void 장기판은_10x9_모든_좌표를_생성한다() {
        Map<Position, Piece> board = BoardInitializer.createBoard(Arrangement.마상마상, Arrangement.마상마상);

        assertThat(board.size()).isEqualTo(Board.BOARD_END_ROWS * Board.BOARD_END_COLS);

        for (int row = Board.BOARD_START_ROWS; row <= Board.BOARD_END_ROWS; row++) {
            for (int col = Board.BOARD_START_COLS; col <= Board.BOARD_END_COLS; col++) {
                assertThat(board.containsKey(new Position(row, col))).isTrue();
            }
        }
    }

    @Test
    void 장기판은_고정된_위치의_기물을_제외한_나머지_칸을_NONE으로_채운다() {
        Map<Position, Piece> board = BoardInitializer.createBoard(Arrangement.마상마상, Arrangement.마상마상);

        int noneCount = (int) board.values().stream()
                .filter(piece -> piece.isEqualPieceType(PieceType.NONE))
                .count();

        assertThat(noneCount).isEqualTo(58);
    }

    @Test
    void 장기판은_고정_기물을_정해진_좌표에_배치한다() {
        Map<Position, Piece> board = BoardInitializer.createBoard(Arrangement.마상마상, Arrangement.마상마상);

        initialHanPiecePosition.forEach((position, expectedClass) -> {
            assertPiece(board, position, expectedClass, Side.HAN);
        });

        initialChoPiecePosition.forEach((position, expectedClass) -> {
            assertPiece(board, position, expectedClass, Side.CHO);
        });

    }

    @ParameterizedTest
    @MethodSource("choArrangementCases")
    void 장기판은_초_진영의_마상_포진을_반영한다(Arrangement arrangement, List<Class<? extends Piece>> pieceTypeList) {
        Map<Position, Piece> board = BoardInitializer.createBoard(arrangement, Arrangement.마상마상);

        for (int i = 0; i < 4; i++) {
            assertPiece(board, initialChoMaSangPosition.get(i), pieceTypeList.get(i), Side.CHO);
        }
    }

    @ParameterizedTest
    @MethodSource("hanArrangementCases")
    void 장기판은_한_진영의_마상_포진을_반영한다(Arrangement arrangement, List<Class<? extends Piece>> pieceTypeList) {
        Map<Position, Piece> board = BoardInitializer.createBoard(Arrangement.마상마상, arrangement);

        for (int i = 0; i < 4; i++) {
            assertPiece(board, initialHanMaSangPosition.get(i), pieceTypeList.get(i), Side.HAN);
        }
    }

    private static Stream<Arguments> choArrangementCases() {
        return Stream.of(
                Arguments.of(Arrangement.마상마상, List.of(Ma.class, Sang.class, Ma.class, Sang.class)),
                Arguments.of(Arrangement.마상상마, List.of(Ma.class, Sang.class, Sang.class, Ma.class)),
                Arguments.of(Arrangement.상마마상, List.of(Sang.class, Ma.class, Ma.class, Sang.class)),
                Arguments.of(Arrangement.상마상마, List.of(Sang.class, Ma.class, Sang.class, Ma.class))
        );
    }

    private static Stream<Arguments> hanArrangementCases() {
        return Stream.of(
                Arguments.of(Arrangement.마상마상, List.of(Ma.class, Sang.class, Ma.class, Sang.class)),
                Arguments.of(Arrangement.마상상마, List.of(Ma.class, Sang.class, Sang.class, Ma.class)),
                Arguments.of(Arrangement.상마마상, List.of(Sang.class, Ma.class, Ma.class, Sang.class)),
                Arguments.of(Arrangement.상마상마, List.of(Sang.class, Ma.class, Sang.class, Ma.class))
        );
    }

    private void assertPiece(Map<Position, Piece> board, Position position, Class<? extends Piece> pieceClass, Side side) {
        assertThat(board).containsKey(position);
        assertThat(board.get(position)).isInstanceOf(pieceClass);
        assertThat(board.get(position).isEqualSide(side)).isTrue();
    }
}