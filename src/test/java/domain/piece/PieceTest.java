package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {

    @Nested
    class Ma {

        @Test
        void 마가_왼쪽_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(3, 2);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 3));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_왼쪽_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(5, 2);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 3));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_위_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(2, 3);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_위_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(2, 5);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_오른쪽_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(3, 6);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 5));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_오른쪽_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(5, 6);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 5));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_아래_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(6, 5);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(5, 4));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 마가_아래_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(6, 3);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);

            // when
            List<Position> moves = ma.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(5, 4));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1"
        })
        void 마로_한_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> ma.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @CsvSource({
                "2, 2",
                "2, -2",
                "-2, 2",
                "-2, -2",
                "2, 0",
                "0, 2",
                "-2, 0",
                "0, -2"
        })
        void 마로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece ma = new Piece(Team.HAN, PieceType.MA, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> ma.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    class Cha {
        @Test
        void 차가_위로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 1);
            Position dest = new Position(1, 1);
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);

            // when
            List<Position> path = cha.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 1), new Position(2, 1));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 차가_아래로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(1, 1);
            Position dest = new Position(4, 1);
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);

            // when
            List<Position> path = cha.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(2, 1), new Position(3, 1));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 차가_왼쪽으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(1, 4);
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);

            // when
            List<Position> path = cha.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4), new Position(2, 4));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 차가_오른쪽으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(1, 1);
            Position dest = new Position(1, 4);
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);

            // when
            List<Position> path1 = cha.calculatePath(src, dest);

            // then
            List<Position> expected1 = List.of(new Position(1, 2), new Position(1, 3));
            assertThat(path1).isEqualTo(expected1);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1"
        })
        void 차로_이동할_수_없는_위치인_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 5;
            int column = 5;
            Position src = new Position(row, column);
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);
            Position dest = new Position(row + movedRow, column + movedColumn);
            // when & then
            assertThatThrownBy(() -> cha.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @MethodSource(value = "createDiagonalMovePosition")
        void 차가_궁성의_꼭짓점에서_꼭짓점으로_이동하는_경로를_계산할_수_있다(Position src, Position dest, Position jumpedPosition) {
            // given
            Piece cha = new Piece(Team.HAN, PieceType.CHA, src);

            // when
            List<Position> path = cha.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(jumpedPosition);
            assertThat(path).isEqualTo(expected);
        }

        private static Stream<Arguments> createDiagonalMovePosition() {
            return Stream.of(
                    Arguments.of(
                            new Position(1, 4),
                            new Position(3, 6),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(1, 6),
                            new Position(3, 4),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(3, 4),
                            new Position(1, 6),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(3, 6),
                            new Position(1, 4),
                            new Position(2, 5)
                    )
            );
        }
    }

    @Nested
    class PoTest {
        @Test
        void 포가_위로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 1);
            Position dest = new Position(1, 1);
            Piece po = new Piece(Team.HAN, PieceType.PO, src);

            // when
            List<Position> path = po.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 1), new Position(2, 1));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 포가_아래로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(1, 1);
            Position dest = new Position(4, 1);
            Piece po = new Piece(Team.HAN, PieceType.PO, src);

            // when
            List<Position> path = po.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(2, 1), new Position(3, 1));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 포가_왼쪽으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(1, 4);
            Piece po = new Piece(Team.HAN, PieceType.PO, src);

            // when
            List<Position> path = po.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4), new Position(2, 4));
            assertThat(path).isEqualTo(expected);
        }

        @Test
        void 포가_오른쪽으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(1, 1);
            Position dest = new Position(1, 4);
            Piece po = new Piece(Team.HAN, PieceType.PO, src);

            // when
            List<Position> path1 = po.calculatePath(src, dest);

            // then
            List<Position> expected1 = List.of(new Position(1, 2), new Position(1, 3));
            assertThat(path1).isEqualTo(expected1);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1"
        })
        void 포로_이동할_수_없는_위치인_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 5;
            int column = 5;
            Position src = new Position(row, column);
            Piece po = new Piece(Team.HAN, PieceType.PO, src);
            Position dest = new Position(row + movedRow, column + movedColumn);
            // when & then
            assertThatThrownBy(() -> po.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @MethodSource(value = "createDiagonalMovePosition")
        void 포가_궁성의_꼭짓점에서_꼭짓점으로_이동하는_경로를_계산할_수_있다(Position src, Position dest, Position jumpedPosition) {
            // given
            Piece po = new Piece(Team.HAN, PieceType.PO, src);

            // when
            List<Position> path = po.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(jumpedPosition);
            assertThat(path).isEqualTo(expected);
        }

        private static Stream<Arguments> createDiagonalMovePosition() {
            return Stream.of(
                    Arguments.of(
                            new Position(1, 4),
                            new Position(3, 6),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(1, 6),
                            new Position(3, 4),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(3, 4),
                            new Position(1, 6),
                            new Position(2, 5)
                    ),
                    Arguments.of(
                            new Position(3, 6),
                            new Position(1, 4),
                            new Position(2, 5)
                    )
            );
        }
    }

    @Nested
    class GungTest {
        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1",
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
        })
        void 궁은_초나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 9;
            int column = 5;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, startPosition);

            //when
            List<Position> actual = gung.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1",
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
        })
        void 궁은_한나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 2;
            int column = 5;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, startPosition);
            //when
            List<Position> actual = gung.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "1, 1",
        })
        void 궁은_한나라_궁성_왼쪽위에서는_궁성안으로만_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 1;
            int column = 4;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, startPosition);
            //when
            List<Position> actual = gung.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 4, 0, -1",
                "1, 6, 0, 1",
                "2, 6, 0, 1",
                "3, 6, 0, 1",
                "3, 6, 1, 0",
                "3, 4, 0, -1",
                "3, 4, 1, 0",
                "2, 4, 0, -1"
        })
        void 궁이_한나라_궁성에서_궁성_밖으로_이동하면_예외를_발생시킨다() {
            //given
            Position startPosition = new Position(1, 4);
            Position targetPosition = new Position(1, 3);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, startPosition);
            //when & then
            Assertions.assertThatThrownBy(() -> gung.calculatePath(startPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 5, 2, 6",
                "1, 5, 2, 4",
                "2, 4, 1, 5",
                "2, 4, 3, 5",
                "3, 5, 2, 4",
                "3, 5, 2, 6",
                "2, 6, 1, 5",
                "2, 6, 3, 5",
        })
        void 궁이_한나라_궁성_가장자리중앙에서_다른가장자리중앙으로_이동하면_예외를_발생시킨다(int row, int column, int newRow, int newColumn) {
            //given
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(newRow, newColumn);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, startPosition);
            //when & then
            Assertions.assertThatThrownBy(() -> gung.calculatePath(startPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 궁은_두_칸_이동할_수_없다() {
            // given
            Position src = new Position(4, 1);
            Position dest = new Position(4, 3);
            Piece gung = new Piece(Team.CHO, PieceType.GUNG, src);
            // when & then
            Assertions.assertThatThrownBy(() -> gung.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    class PawnTest {
        @Test
        void 졸은_오른쪽으로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, new Position(4, 4));

            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 5));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 졸은_왼쪽으로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, new Position(4, 4));

            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 3));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 졸은_위으로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, new Position(4, 4));

            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(3, 4));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 졸이_아래로_이동할_경우_예외를_발생시킨다() {
            //given
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, new Position(4, 4));

            //when & then
            assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 4), new Position(5, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @CsvSource({
                "2, 2",
                "2, -2",
                "-2, 2",
                "-2, -2",
                "2, 0",
                "0, 2",
                "-2, 0",
                "0, -2"
        })
        void 졸으로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> pawn.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @Test
        void 병은_아래로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.HAN, PieceType.PAWN, new Position(4, 4));
            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(5, 4));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 병은_왼쪽으로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.HAN, PieceType.PAWN, new Position(4, 4));
            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 3));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 병은_오른쪽으로_이동할_수_있다() {
            //given
            Piece pawn = new Piece(Team.HAN, PieceType.PAWN, new Position(4, 4));
            //when
            List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 5));

            // then
            assertThat(move).isEqualTo(List.of());
        }

        @Test
        void 병이_위로_이동할_경우_예외를_발생시킨다() {
            //given
            Piece pawn = new Piece(Team.HAN, PieceType.PAWN, new Position(4, 4));
            //when & then
            assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 4), new Position(3, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @CsvSource({
                "2, 2",
                "2, -2",
                "-2, 2",
                "-2, -2",
                "2, 0",
                "0, 2",
                "-2, 0",
                "0, -2"
        })
        void 병으로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece pawn = new Piece(Team.CHO, PieceType.PAWN, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> pawn.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    class SangTest {
        @Test
        void 상이_왼쪽_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(2, 1);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 3), new Position(3, 2));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_왼쪽_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(6, 1);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 3), new Position(5, 2));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_위_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(1, 2);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4), new Position(2, 3));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_위_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(1, 6);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(3, 4), new Position(2, 5));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_오른쪽_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(2, 7);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 5), new Position(3, 6));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_오른쪽_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(6, 7);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(4, 5), new Position(5, 6));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_아래_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(7, 6);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(5, 4), new Position(6, 5));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @Test
        void 상이_아래_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
            // given
            Position src = new Position(4, 4);
            Position dest = new Position(7, 2);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);

            // when
            List<Position> moves = sang.calculatePath(src, dest);

            // then
            List<Position> expected = List.of(new Position(5, 4), new Position(6, 3));
            Assertions.assertThat(moves).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1"
        })
        void 상으로_한_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> sang.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }

        @ParameterizedTest
        @CsvSource({
                "2, 2",
                "2, -2",
                "-2, 2",
                "-2, -2",
                "2, 0",
                "0, 2",
                "-2, 0",
                "0, -2"
        })
        void 상으로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
            // given
            int row = 4;
            int column = 4;
            Position src = new Position(row, column);
            Piece sang = new Piece(Team.HAN, PieceType.SANG, src);
            Position dest = new Position(row + movedRow, column + movedColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> sang.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    class SaTest {
        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1",
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
        })
        void 사은_초나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 9;
            int column = 5;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece sa = new Piece(Team.CHO, PieceType.SA, startPosition);

            //when
            List<Position> actual = sa.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "-1, 0",
                "0, -1",
                "1, 1",
                "1, -1",
                "-1, 1",
                "-1, -1",
        })
        void 사은_한나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 2;
            int column = 5;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece sa = new Piece(Team.CHO, PieceType.SA, startPosition);

            //when
            List<Position> actual = sa.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 0",
                "0, 1",
                "1, 1",
        })
        void 사은_한나라_궁성_왼쪽위에서는_궁성안으로만_이동할_수_있다(int movedRow, int movedColumn) {
            //given
            int row = 1;
            int column = 4;
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(row + movedRow, column + movedColumn);
            Piece sa = new Piece(Team.CHO, PieceType.SA, startPosition);

            //when
            List<Position> actual = sa.calculatePath(startPosition, targetPosition);

            // then
            Assertions.assertThat(actual).isEqualTo(List.of());
        }

        @ParameterizedTest
        @CsvSource({
                "1, 4, 0, -1",
                "1, 6, 0, 1",
                "2, 6, 0, 1",
                "3, 6, 0, 1",
                "3, 6, 1, 0",
                "3, 4, 0, -1",
                "3, 4, 1, 0",
                "2, 4, 0, -1"
        })
        void 사가_한나라_궁성에서_궁성_밖으로_이동하면_예외를_발생시킨다() {
            //given
            Position startPosition = new Position(1, 4);
            Position targetPosition = new Position(1, 3);
            Piece sa = new Piece(Team.CHO, PieceType.SA, startPosition);

            //when & then
            Assertions.assertThatThrownBy(() -> sa.calculatePath(startPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 5, 2, 6",
                "1, 5, 2, 4",
                "2, 4, 1, 5",
                "2, 4, 3, 5",
                "3, 5, 2, 4",
                "3, 5, 2, 6",
                "2, 6, 1, 5",
                "2, 6, 3, 5",
        })
        void 사가_한나라_궁성_가장자리중앙에서_다른가장자리중앙으로_이동하면_예외를_발생시킨다(int row, int column, int newRow, int newColumn) {
            //given
            Position startPosition = new Position(row, column);
            Position targetPosition = new Position(newRow, newColumn);
            Piece sa = new Piece(Team.CHO, PieceType.SA, startPosition);

            //when & then
            Assertions.assertThatThrownBy(() -> sa.calculatePath(startPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }


        @Test
        void 사는_두_칸_이동할_수_없다() {
            // given
            Position src = new Position(4, 1);
            Position dest = new Position(4, 3);
            Piece sa = new Piece(Team.CHO, PieceType.SA, src);

            // when & then
            Assertions.assertThatThrownBy(() -> sa.calculatePath(src, dest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이 위치로 이동할 수 없습니다.");
        }
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "HAN, CHO, false",
            "CHO, HAN, false",
            "CHO, CHO, true"
    })
    void 두_기물의_팀이_같은지_판단한다(Team team1, Team team2, boolean expected) {
        // given
        Piece piece1 = new Piece(team1, PieceType.MA, new Position(1, 1));
        Piece piece2 = new Piece(team2, PieceType.MA, new Position(1, 1));

        // when
        boolean actual = piece1.isTeam(piece2);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "HAN, CHO, false ",
            "CHO, HAN, false",
            "CHO, CHO, true"
    })
    void 기물이_특정_팀인지_판단한다(Team pieceTeam, Team team, boolean expected) {
        // given
        Piece piece = new Piece(pieceTeam, PieceType.MA, new Position(1, 1));

        // when
        boolean actual = piece.isTeam(team);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
