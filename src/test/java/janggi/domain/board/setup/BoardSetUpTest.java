package janggi.domain.board.setup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardSetUpTest {

    private static Map<Point, PieceType> toTypeMap(Map<Point, Piece> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getType()
                ));
    }

    @Nested
    @DisplayName("기물 차림 테스트")
    class SetUpTest {

        static Stream<Arguments> generate() {
            return Stream.of(
                    Arguments.of(new InSetUp(), Side.CHO, Map.of(
                            Point.of(0, 1), new Horse(Side.CHO), Point.of(0, 2), new Elephant(Side.CHO),
                            Point.of(0, 6), new Elephant(Side.CHO), Point.of(0, 7), new Horse(Side.CHO))),

                    Arguments.of(new InSetUp(), Side.HAN, Map.of(
                            Point.of(9, 7), new Horse(Side.HAN), Point.of(9, 6), new Elephant(Side.HAN),
                            Point.of(9, 2), new Elephant(Side.HAN), Point.of(9, 1), new Horse(Side.HAN)
                    )),

                    Arguments.of(new OutSetUp(), Side.CHO, Map.of(
                            Point.of(0, 1), new Elephant(Side.CHO), Point.of(0, 2), new Horse(Side.CHO),
                            Point.of(0, 6), new Horse(Side.CHO), Point.of(0, 7), new Elephant(Side.CHO)
                    )),

                    Arguments.of(new OutSetUp(), Side.HAN, Map.of(
                            Point.of(9, 7), new Elephant(Side.HAN), Point.of(9, 6), new Horse(Side.HAN),
                            Point.of(9, 2), new Horse(Side.HAN), Point.of(9, 1), new Elephant(Side.HAN)
                    )),

                    Arguments.of(new LeftSetUp(), Side.CHO, Map.of(
                            Point.of(0, 1), new Elephant(Side.CHO), Point.of(0, 2), new Horse(Side.CHO),
                            Point.of(0, 6), new Elephant(Side.CHO), Point.of(0, 7), new Horse(Side.CHO)
                    )),

                    Arguments.of(new LeftSetUp(), Side.HAN, Map.of(
                            Point.of(9, 7), new Elephant(Side.HAN), Point.of(9, 6), new Horse(Side.HAN),
                            Point.of(9, 2), new Elephant(Side.HAN), Point.of(9, 1), new Horse(Side.HAN)
                    )),

                    Arguments.of(new RightSetUp(), Side.CHO, Map.of(
                            Point.of(0, 1), new Horse(Side.CHO), Point.of(0, 2), new Elephant(Side.CHO),
                            Point.of(0, 6), new Horse(Side.CHO), Point.of(0, 7), new Elephant(Side.CHO)
                    )),

                    Arguments.of(new RightSetUp(), Side.HAN, Map.of(
                            Point.of(9, 7), new Horse(Side.HAN), Point.of(9, 6), new Elephant(Side.HAN),
                            Point.of(9, 2), new Horse(Side.HAN), Point.of(9, 1), new Elephant(Side.HAN)
                    ))
            );
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("generate(): 4가지 기물 차림 테스트")
        void generate(BoardSetUp boardSetUp, Side side, Map<Point, Piece> expected) {
            assertThat(toTypeMap(boardSetUp.generate(side)))
                    .containsAllEntriesOf(toTypeMap(expected));
        }
    }

    @Nested
    @DisplayName("공통 기물 배치 테스트")
    class CommonSetUpTest {

        private static Map<Point, Piece> createHanCommonBoard() {
            Map<Point, Piece> board = new HashMap<>();
            board.put(Point.of(6, 8), new Soldier(Side.HAN));
            board.put(Point.of(6, 6), new Soldier(Side.HAN));
            board.put(Point.of(6, 4), new Soldier(Side.HAN));
            board.put(Point.of(6, 2), new Soldier(Side.HAN));
            board.put(Point.of(6, 0), new Soldier(Side.HAN));

            board.put(Point.of(7, 7), new Cannon(Side.HAN));
            board.put(Point.of(7, 1), new Cannon(Side.HAN));

            board.put(Point.of(8, 4), new General(Side.HAN));

            board.put(Point.of(9, 8), new Chariot(Side.HAN));
            board.put(Point.of(9, 5), new Advisor(Side.HAN));
            board.put(Point.of(9, 3), new Advisor(Side.HAN));
            board.put(Point.of(9, 0), new Chariot(Side.HAN));

            return board;
        }

        private static Map<Point, Piece> createChoCommonBoard() {
            Map<Point, Piece> board = new HashMap<>();
            board.put(Point.of(0, 0), new Chariot(Side.CHO));
            board.put(Point.of(0, 3), new Advisor(Side.CHO));
            board.put(Point.of(0, 5), new Advisor(Side.CHO));
            board.put(Point.of(0, 8), new Chariot(Side.CHO));

            board.put(Point.of(1, 4), new General(Side.CHO));

            board.put(Point.of(2, 1), new Cannon(Side.CHO));
            board.put(Point.of(2, 7), new Cannon(Side.CHO));

            board.put(Point.of(3, 0), new Soldier(Side.CHO));
            board.put(Point.of(3, 2), new Soldier(Side.CHO));
            board.put(Point.of(3, 4), new Soldier(Side.CHO));
            board.put(Point.of(3, 6), new Soldier(Side.CHO));
            board.put(Point.of(3, 8), new Soldier(Side.CHO));

            return board;
        }

        static Stream<Arguments> generate() {
            return Stream.of(
                    Arguments.of(new LeftSetUp(), Side.CHO, createChoCommonBoard()),
                    Arguments.of(new LeftSetUp(), Side.HAN, createHanCommonBoard()),
                    Arguments.of(new RightSetUp(), Side.CHO, createChoCommonBoard()),
                    Arguments.of(new RightSetUp(), Side.HAN, createHanCommonBoard())
            );
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("generate(): 공통 기물 배치 검증")
        void generate(BoardSetUp boardSetUp, Side side, Map<Point, Piece> expected) {
            assertThat(toTypeMap(boardSetUp.generate(side)))
                    .containsAllEntriesOf(toTypeMap(expected));
        }
    }
}
