package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        private Piece cannon;
        private Piece allySoldier;
        private Piece allyCannon;
        private Piece enemySoldier;
        private Piece enemyCannon;
        private Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            cannon = new Cannon(TeamType.RED);
            enemySoldier = new Soldier(TeamType.BLUE);
            enemyCannon = new Cannon(TeamType.BLUE);
            allySoldier = new Soldier(TeamType.BLUE);
            allyCannon = new Cannon(TeamType.RED);
        }

        @Test
        @DisplayName("포는 기물을 뛰어 넘어서 이동할 수 있다.")
        void success_1() {
            positionPieceMap = Map.of(
                    Position.valueOf(6, 7), cannon,
                    Position.valueOf(6, 5), enemySoldier,
                    Position.valueOf(7, 7), allySoldier);
            List<Position> expected = List.of(
                    Position.valueOf(6, 1),
                    Position.valueOf(6, 2),
                    Position.valueOf(6, 3),
                    Position.valueOf(6, 4),
                    Position.valueOf(8, 7),
                    Position.valueOf(9, 7),
                    Position.valueOf(10, 7));
            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("포는 포를 넘을 수 없다.")
        void success_2() {
            positionPieceMap = Map.of(
                    Position.valueOf(6, 7), cannon,
                    Position.valueOf(4, 7), enemySoldier,
                    Position.valueOf(6, 5), enemyCannon,
                    Position.valueOf(9, 7), allyCannon);
            List<Position> expected = List.of(
                    Position.valueOf(1, 7),
                    Position.valueOf(2, 7),
                    Position.valueOf(3, 7));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("포는 포를 잡을 수 없다.")
        void success_3() {
            positionPieceMap = Map.of(
                    Position.valueOf(6, 7), cannon,
                    Position.valueOf(6, 3), enemyCannon,
                    Position.valueOf(6, 4), allySoldier);
            List<Position> expected = List.of();

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("포는 궁성영역에서 대각선으로 이동할 수 있다.")
        void success_4() {
            positionPieceMap = Map.of(
                    Position.valueOf(8, 4), cannon,
                    Position.valueOf(9, 5), enemySoldier,
                    Position.valueOf(10, 6), enemySoldier);
            List<Position> expected = List.of(Position.valueOf(10, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(8, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("포는 궁성영역에서 대각선으로 이동할 수 있다.")
        void success_5() {
            positionPieceMap = Map.of(
                    Position.valueOf(8, 4), cannon,
                    Position.valueOf(9, 5), enemySoldier,
                    Position.valueOf(10, 6), enemySoldier);
            List<Position> expected = List.of(Position.valueOf(10, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(8, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("포는 궁성 대각선에서 포다리가 없으면 상대 기물을 잡을 수 없다")
        void success_6() {
            positionPieceMap = Map.of(
                    Position.valueOf(10, 5), cannon,
                    Position.valueOf(9, 5), enemySoldier,
                    Position.valueOf(8, 6), enemySoldier
            );
            List<Position> expected = List.of(
                    Position.valueOf(8, 5),
                    Position.valueOf(7, 5),
                    Position.valueOf(6, 5),
                    Position.valueOf(5, 5),
                    Position.valueOf(4, 5),
                    Position.valueOf(3, 5),
                    Position.valueOf(2, 5),
                    Position.valueOf(1, 5)
            );

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(10, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
