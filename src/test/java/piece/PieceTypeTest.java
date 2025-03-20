package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTypeTest {

    @Nested
    @DisplayName("피스 타입 생성")
    class Construct {

        @DisplayName("피스 타입을 올바로 생성한다.")
        @Test
        void construct1() {
            // given
            final List<PieceType> expectedType = List.of(
                    PieceType.GENERAL,
                    PieceType.GUARD,
                    PieceType.HORSE,
                    PieceType.ELEPHANT,
                    PieceType.CHARIOT,
                    PieceType.CANNON,
                    PieceType.SOLDIER
            );

            // when
            // then
            assertThat(Arrays.asList(PieceType.values()))
                    .containsAll(expectedType)
                    .hasSize(7);
        }

        @DisplayName("각 피스 타입이 초기 좌표를 가지고 있는 지 확인한다.")
        @Test
        void getInitPositions() {
            // given
            final int expectedSize = 2;
            final PieceType pieceType = PieceType.CANNON;
            final TeamType teamType = TeamType.RED;

            // when
            final List<Position> initPositions = pieceType.getInitPositions(teamType);

            // then
            Assertions.assertThat(initPositions).hasSize(expectedSize);
        }
    }

    @Nested
    @DisplayName("타입 이동 테스트")
    class IsAbleMove {

        @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
        @Test
        void general() {
            // given
            final PieceType pieceType = PieceType.GENERAL;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 2);
            final Position notAbleDest = new Position(1, 3);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("guard는 주변 한칸으로 이동할 수 있다.")
        @Test
        void guard() {
            // given
            final PieceType pieceType = PieceType.GUARD;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 2);
            final Position notAbleDest = new Position(1, 3);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("horse는 직-대 방향으로 이동할 수 있다.")
        @Test
        void horse() {
            // given
            final PieceType pieceType = PieceType.HORSE;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(3, 2);
            final Position notAbleDest = new Position(1, 2);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("horse는 직 방향에 기물이 존재하면 이동할 수 없다.")
        @Test
        void horse1() {
            // given
            final PieceType pieceType = PieceType.HORSE;
            final Position now = new Position(2, 3);
            final Position notAbleDest = new Position(1, 1);
            final Board board = new Board(Map.of(
                    new Position(2, 2), new Piece(PieceType.CANNON, TeamType.RED)
            ));
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("Elephant는 직-대-대 방향으로 이동할 수 있다.")
        @Test
        void elephant() {
            // given
            final PieceType pieceType = PieceType.ELEPHANT;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(4, 3);
            final Position notAbleDest = new Position(1, 2);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("Elephant는 직 또는 직-대 방향에 기물이 존재하면 이동할 수 없다.")
        @Test
        void elephant1() {
            // given
            final PieceType pieceType = PieceType.ELEPHANT;
            final Position now = new Position(2, 2);
            final Position dest1 = new Position(4, 5);
            final Position dest2 = new Position(5, 4);


            final Board board = new Board(Map.of(
                    new Position(2, 3), new Piece(PieceType.CANNON, TeamType.RED),
                    new Position(4, 3), new Piece(PieceType.CANNON, TeamType.RED)
            ));
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, dest1, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, dest2, board, teamType);

            // then
            assertThat(actual1).isFalse();
            assertThat(actual2).isFalse();
        }


        @DisplayName("Chariot은 직선 방향으로 모든 곳을 이동할 수 있다.")
        @Test
        void chariot() {
            // given
            final PieceType pieceType = PieceType.CHARIOT;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 2);
            final Position notAbleDest = new Position(2, 2);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("Chariot 은 목적지까지 어떠한 기물도 존재해서는 안된다")
        @Test
        void chariot2() {
            // given
            final PieceType pieceType = PieceType.CHARIOT;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 3);
            final Position notAbleDest = new Position(1, 5);
            final Map<Position, Piece> map = Map.of(new Position(1, 4),
                    new Piece(PieceType.ELEPHANT, TeamType.BLUE));
            final Board board = new Board(map);
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("Cannon은 목적지가 같은 라인이 아니라면 false를 반환한다.")
        @Test
        void cannon1() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 3);
            final Position notAbleDest = new Position(2, 2);
            final TeamType teamType = TeamType.RED;
            final Map<Position, Piece> map = Map.of(new Position(1, 2),
                    new Piece(PieceType.CHARIOT, teamType));
            final Board board = new Board(map);

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("Cannon은 목적지까지 가는 중 아무런 기물이 없다면 false를 반환한다.")
        @Test
        void cannon2() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final Position now = new Position(1, 1);
            final Position ableDest = new Position(1, 3);
            final Position notAbleDest = new Position(2, 1);
            final TeamType teamType = TeamType.RED;
            final Map<Position, Piece> map = Map.of(new Position(1, 2),
                    new Piece(PieceType.CHARIOT, teamType));
            final Board board = new Board(map);

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("Cannon은 목적지까지 가던 중 포를 만나면 false를 반환한다.")
        @Test
        void cannon3() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final Position now = new Position(1, 1);
            final Position notAbleDest = new Position(1, 3);
            final TeamType teamType = TeamType.RED;
            final Map<Position, Piece> map = Map.of(new Position(1, 2),
                    new Piece(PieceType.CANNON, teamType));
            final Board board = new Board(map);

            // when
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            assertThat(actual2).isFalse();
        }

        @DisplayName("Cannon은 목적지까지 가던 중 2개 이상의 기물을 만나면 false를 반환한다.")
        @Test
        void cannon4() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final Position now = new Position(1, 1);
            final Position notAbleDest = new Position(1, 4);
            final TeamType teamType = TeamType.RED;
            final Map<Position, Piece> map = Map.of(new Position(1, 2),
                    new Piece(PieceType.CHARIOT, teamType), new Position(1, 3),
                    new Piece(PieceType.CHARIOT, teamType));
            final Board board = new Board(map);

            // when
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            assertThat(actual2).isFalse();
        }

        @DisplayName("포는 포를 죽일 수 없다.")
        @Test
        void cannon5() {
            // given
            final PieceType pieceType = PieceType.CANNON;
            final Position now = new Position(1, 1);
            final Position notAbleDest = new Position(1, 4);
            final TeamType teamType = TeamType.RED;
            final Map<Position, Piece> map = Map.of(new Position(1, 4),
                    new Piece(PieceType.CANNON, TeamType.BLUE), new Position(1, 3),
                    new Piece(PieceType.CHARIOT, teamType));
            final Board board = new Board(map);

            // when
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            assertThat(actual2).isFalse();
        }

        @DisplayName("Soldier는 뒷 방향을 제외하고 한 칸을 이동할 수 있다.")
        @Test
        void soldiers() {
            // given
            final PieceType pieceType = PieceType.SOLDIER;
            final Position now = new Position(2, 2);
            final Position ableDest = new Position(3, 2);
            final Position notAbleDest = new Position(1, 2);
            final Board board = new Board(new HashMap<>());
            final TeamType teamType = TeamType.RED;

            // when
            final boolean actual1 = pieceType.isAbleMove(now, ableDest, board, teamType);
            final boolean actual2 = pieceType.isAbleMove(now, notAbleDest, board, teamType);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }


    }

}
