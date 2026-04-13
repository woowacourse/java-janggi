import domain.*;
import domain.piece.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private static final Long TEST_ID = 1L;

    private Game createGame(Formation cho, Formation han) {
        return new Game(TEST_ID, new Turn(Side.CHO), cho, han);
    }

    private void move(Game game, Turn turn, Position source, Position target) {
        List<Position> available = game.findPath(source, turn);
        game.movePiece(source, target, available);
    }

    @Nested
    @DisplayName("장기판 초기화")
    class initGame {

        @DisplayName("초나라 포진을 상마마상으로 배치한다.")
        @Test
        void 초나라_포진을_상마마상으로_배치한다() {
            Game game = createGame(Formation.상마마상, Formation.마상상마);
            Map<Position, Piece> pieces = game.getBoard();

            assertThat(pieces.get(Position.of(2, 10))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(Position.of(3, 10))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(7, 10))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(8, 10))).isInstanceOf(Elephant.class);
        }

        @DisplayName("한나라 포진을 마상마상으로 배치한다.")
        @Test
        void 한나라_포진을_마상마상으로_배치한다() {
            Game game = createGame(Formation.상마마상, Formation.마상마상);
            Map<Position, Piece> pieces = game.getBoard();

            assertThat(pieces.get(Position.of(2, 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(3, 1))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(Position.of(7, 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(8, 1))).isInstanceOf(Elephant.class);
        }

        @DisplayName("각 궁이 올바른 진영에 배치된다.")
        @Test
        void 각_궁이_올바른_진영에_배치된다() {
            Game game = createGame(Formation.상마마상, Formation.마상상마);
            Map<Position, Piece> pieces = game.getBoard();

            assertThat(pieces.get(Position.of(5, 2)).isSameSide(Side.HAN)).isTrue();
            assertThat(pieces.get(Position.of(5, 9)).isSameSide(Side.CHO)).isTrue();
        }
    }

    @DisplayName("초 턴에 한 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 초_턴에_한_기물을_선택한_경우_이동할_수_없다() {
        // given
        Game game = createGame(Formation.상마마상, Formation.상마마상);
        Turn turn = new Turn(Side.CHO);
        Position sourcePosition = Position.of(1, 4);

        // when & then
        Assertions.assertThatThrownBy(() -> game.findPath(sourcePosition, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");
    }

    @DisplayName("한 턴에 초 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 한_턴에_초_기물을_선택한_경우_이동할_수_없다() {
        // given
        Game game = createGame(Formation.상마마상, Formation.상마마상);
        Turn turn = new Turn(Side.HAN);
        Position sourcePosition = Position.of(1, 7);

        // when & then
        Assertions.assertThatThrownBy(() -> game.findPath(sourcePosition, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");
    }

    @DisplayName("출발지에 기물이 존재하지 않는 경우 이동할 수 없다.")
    @Test
    void 기물이_존재하지_않는_경우_이동할_수_없다() {
        // given
        Game game = createGame(Formation.상마마상, Formation.상마마상);
        Turn turn = new Turn(Side.CHO);
        Position sourcePosition = Position.of(5, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> game.findPath(sourcePosition, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @Nested
    @DisplayName("기물 이동")
    class MovePiece {

        @Nested
        @DisplayName("차")
        class ChariotMove {

            @Test
            @DisplayName("차가 세로로 이동한다.")
            void 차가_세로로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 3);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Chariot.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("차가 가로로 이동한다.")
            void 차가_가로로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 2);
                move(game, new Turn(Side.HAN), source, target);

                // when
                move(game, new Turn(Side.HAN), target, Position.of(3, 2));

                // then
                assertThat(game.getBoard().get(Position.of(3, 2))).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("차가 대각선으로 이동하면 예외가 발생한다.")
            void 차가_대각선으로_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(1, 1);
                Position target = Position.of(3, 3);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("상")
        class ElephantMove {

            @Test
            @DisplayName("상이 대각선 방향으로 이동한다.")
            void 상이_대각선_방향으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Elephant.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("상이 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 상이_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 2);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("마")
        class HorseMove {

            @Test
            @DisplayName("마가 날 일자로 이동한다.")
            void 마가_날_일자로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.마상마상);
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 3);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Horse.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("마가 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 마가_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.마상마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("포")
        class CannonMove {

            @Test
            @DisplayName("포가 기물을 하나 뛰어넘어 이동한다.")
            void 포가_기물을_하나_뛰어넘어_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                // 사(4,1)를 (4,3)으로 이동시켜 포가 뛰어넘을 기물 배치
                move(game, turn, Position.of(4, 1), Position.of(4, 2));
                move(game, turn, Position.of(4, 2), Position.of(4, 3));

                // when - (2,3) 포가 (4,3) 사를 뛰어넘어 (5,3)으로 이동
                move(game, turn, Position.of(2, 3), Position.of(5, 3));

                // then
                assertThat(game.getBoard().get(Position.of(5, 3))).isInstanceOf(Cannon.class);
            }

            @Test
            @DisplayName("포가 뛰어넘을 기물이 없으면 예외가 발생한다.")
            void 포가_뛰어넘을_기물이_없으면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(2, 3);
                Position target = Position.of(5, 3);

                // when & then - 중간에 기물이 없음
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("포가 포를 뛰어넘으면 예외가 발생한다.")
            void 포가_포를_뛰어넘으면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                // (8,3) 포를 (5,3)으로 이동하기 위해 먼저 중간에 기물 배치
                move(game, turn, Position.of(6, 1), Position.of(6, 2));
                move(game, turn, Position.of(6, 2), Position.of(6, 3));
                // (8,3) 포가 (6,3) 사를 뛰어넘어 (5,3)으로 이동
                move(game, turn, Position.of(8, 3), Position.of(5, 3));

                // when & then
                // (2,3) 포가 (5,3) 포를 뛰어넘어 (7,3)으로 이동 시도
                List<Position> available = game.findPath(Position.of(2, 3), turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(Position.of(2, 3), Position.of(7, 3), available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("사")
        class GuardMove {

            @Test
            @DisplayName("사가 한 칸 이동한다.")
            void 사가_한_칸_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(4, 1);
                Position target = Position.of(5, 1);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Guard.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("사가 두 칸 이상 이동하면 예외가 발생한다.")
            void 사가_두_칸_이상_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(4, 1);
                Position target = Position.of(4, 3);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("궁")
        class KingMove {

            @Test
            @DisplayName("궁이 한 칸 이동한다.")
            void 궁이_한_칸_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 1);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(King.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("궁이 두 칸 이상 이동하면 예외가 발생한다.")
            void 궁이_두_칸_이상_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 4);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("졸/병")
        class SoldierMove {

            @Test
            @DisplayName("졸이 앞으로 한 칸 이동한다.")
            void 졸이_앞으로_한_칸_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 6);

                // when
                move(game, new Turn(Side.CHO), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("병이 앞으로 한 칸 이동한다.")
            void 병이_앞으로_한_칸_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(1, 4);
                Position target = Position.of(1, 5);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("졸이 옆으로 한 칸 이동한다.")
            void 졸이_옆으로_한_칸_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(1, 7);
                Position target = Position.of(2, 7);

                // when
                move(game, new Turn(Side.CHO), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
            }

            @Test
            @DisplayName("졸이 뒤로 이동하면 예외가 발생한다.")
            void 졸이_뒤로_이동하면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.CHO);
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 8);

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("적 기물 잡기")
        class CapturePiece {

            @Test
            @DisplayName("적 기물을 잡을 수 있다.")
            void 적_기물을_잡을_수_있다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);

                // 차를 이동시켜 적 졸을 잡기
                move(game, turn, Position.of(1, 4), Position.of(2, 4));
                move(game, turn, Position.of(1, 1), Position.of(1, 7));

                // then
                assertThat(game.getBoard().get(Position.of(1, 7))).isInstanceOf(Chariot.class);
                assertThat(game.getBoard().get(Position.of(1, 7)).isSameSide(Side.HAN)).isTrue();
            }

            @Test
            @DisplayName("아군 기물을 잡으면 예외가 발생한다.")
            void 아군_기물을_잡으면_예외가_발생한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 4); // HAN 졸 위치

                // when & then
                List<Position> available = game.findPath(source, turn);
                Assertions.assertThatThrownBy(() -> game.movePiece(source, target, available))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("궁성 내 대각선 이동")
        class PalaceDiagonalMove {

            @Test
            @DisplayName("궁이 궁성 중앙에서 대각선으로 이동한다")
            void 궁이_궁성_중앙에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Position source = Position.of(5, 2);
                Position target = Position.of(4, 3);

                // when
                move(game, new Turn(Side.HAN), source, target);

                // then
                assertThat(game.getBoard().get(target)).isInstanceOf(King.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("궁이 궁성 모서리에서 중앙으로 대각선 이동한다")
            void 궁이_궁성_모서리에서_중앙으로_대각선_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                move(game, turn, Position.of(5, 2), Position.of(4, 3));

                // when
                move(game, turn, Position.of(4, 3), Position.of(5, 2));

                // then
                assertThat(game.getBoard().get(Position.of(5, 2))).isInstanceOf(King.class);
            }

            @Test
            @DisplayName("사가 궁성 내에서 대각선으로 이동한다")
            void 사가_궁성_내에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);
                Turn turn = new Turn(Side.HAN);
                move(game, turn, Position.of(5, 2), Position.of(5, 1));

                // when
                move(game, turn, Position.of(4, 1), Position.of(5, 2));

                // then
                assertThat(game.getBoard().get(Position.of(5, 2))).isInstanceOf(Guard.class);
            }

            @Test
            @DisplayName("쫄이 궁성 내에서 대각선으로 이동한다")
            void 쫄이_궁성_내에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);

                // when
                move(game, new Turn(Side.CHO), Position.of(5, 7), Position.of(4,7));
                move(game, new Turn(Side.CHO), Position.of(4, 7), Position.of(4,6));
                move(game, new Turn(Side.CHO), Position.of(4, 6), Position.of(4,5));
                move(game, new Turn(Side.CHO), Position.of(4, 5), Position.of(4,4));
                move(game, new Turn(Side.CHO), Position.of(4, 4), Position.of(4,3));
                move(game, new Turn(Side.CHO), Position.of(4, 3), Position.of(5,2));

                // then
                assertThat(game.getBoard().get(Position.of(4,3))).isInstanceOf(Empty.class);
                assertThat(game.getBoard().get(Position.of(5,2))).isInstanceOf(Soldier.class);
            }

            @Test
            @DisplayName("병이 궁성 내에서 대각선으로 이동한다")
            void 병이_궁성_내에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);

                // when
                move(game, new Turn(Side.HAN), Position.of(5, 4), Position.of(4,4));
                move(game, new Turn(Side.HAN), Position.of(4, 4), Position.of(4,5));
                move(game, new Turn(Side.HAN), Position.of(4, 5), Position.of(4,6));
                move(game, new Turn(Side.HAN), Position.of(4, 6), Position.of(4,7));
                move(game, new Turn(Side.HAN), Position.of(4, 7), Position.of(4,8));
                move(game, new Turn(Side.HAN), Position.of(4, 8), Position.of(5,9));

                // then
                assertThat(game.getBoard().get(Position.of(4,8))).isInstanceOf(Empty.class);
                assertThat(game.getBoard().get(Position.of(5,9))).isInstanceOf(Soldier.class);
            }

            @Test
            @DisplayName("차가 궁성 내에서 대각선으로 이동한다")
            void 차가_궁성_내에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.상마마상, Formation.상마마상);

                // when
                // 차 이동
                move(game, new Turn(Side.CHO), Position.of(1, 10), Position.of(1, 9));
                move(game, new Turn(Side.CHO), Position.of(1, 9), Position.of(4, 9));
                move(game, new Turn(Side.CHO), Position.of(4, 9), Position.of(4, 8));

                // 궁 이동
                move(game, new Turn(Side.CHO), Position.of(5, 9), Position.of(5, 10));

                // 차 이동
                move(game, new Turn(Side.CHO), Position.of(4, 8), Position.of(5, 9));

                // then
                assertThat(game.getBoard().get(Position.of(5,9))).isInstanceOf(Chariot.class);
                assertThat(game.getBoard().get(Position.of(4, 9))).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("포가 궁성 중앙에서 대각선으로 이동한다")
            void 포가_궁성_내에서_대각선으로_이동한다() {
                // given
                Game game = createGame(Formation.마상마상, Formation.상마마상);

                // when
                // 마 이동 & 사 이동
                move(game, new Turn(Side.CHO), Position.of(2, 10), Position.of(3, 8));
                move(game, new Turn(Side.CHO), Position.of(6, 10), Position.of(6, 9));

                // 포 이동
                move(game, new Turn(Side.CHO), Position.of(2, 8), Position.of(4, 8));
                move(game, new Turn(Side.CHO), Position.of(4, 8), Position.of(6, 10));

                // then
                assertThat(game.getBoard().get(Position.of(4,8))).isInstanceOf(Empty.class);
                assertThat(game.getBoard().get(Position.of(6, 10))).isInstanceOf(Cannon.class);
            }
        }
    }

    @Nested
    @DisplayName("게임 종료 판단")
    class IsFinished {

        @Test
        @DisplayName("양 진영 궁이 모두 살아있으면 게임은 종료되지 않는다.")
        void 양_진영_궁이_모두_살아있으면_종료되지_않는다() {
            Game game = createGame(Formation.상마마상, Formation.상마마상);

            assertThat(game.isFinished()).isFalse();
        }

        @Test
        @DisplayName("한 쪽 궁이 잡히면 게임이 종료된다.")
        void 한_쪽_궁이_잡히면_종료된다() {
            Map<Position, Piece> board = Map.of(
                    Position.of(5, 9), PieceType.KING.create(Side.CHO)
            );
            Game game = new Game(TEST_ID, new Turn(Side.CHO), board);

            assertThat(game.isFinished()).isTrue();
        }

        @Test
        @DisplayName("양 진영 궁이 모두 남아있으면 종료되지 않는다.")
        void 양_진영_궁이_모두_남아있으면_종료되지_않는다() {
            Map<Position, Piece> board = Map.of(
                    Position.of(5, 2), PieceType.KING.create(Side.HAN),
                    Position.of(5, 9), PieceType.KING.create(Side.CHO)
            );
            Game game = new Game(TEST_ID, new Turn(Side.CHO), board);

            assertThat(game.isFinished()).isFalse();
        }
    }
}
