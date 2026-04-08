import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.Game;
import domain.Position;
import domain.Side;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Empty;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.PieceType;
import domain.piece.Soldier;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GameTest {

    @DisplayName("초 턴에 한 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 초_턴에_한_기물을_선택한_경우_이동할_수_없다() {
        Game game = createGame("1", "1");
        Position sourcePosition = Position.of(1, 4);
        Position targetPosition = Position.of(1, 1);

        Assertions.assertThatThrownBy(() -> game.move(sourcePosition, targetPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");
    }

    @DisplayName("한 턴에 초 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 한_턴에_초_기물을_선택한_경우_이동할_수_없다() {
        Game game = createGame("1", "1");
        moveBySide(game, Side.CHO, Position.of(1, 7), Position.of(1, 6));

        Assertions.assertThatThrownBy(() -> game.move(Position.of(3, 7), Position.of(3, 6)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");
    }

    @DisplayName("출발지에 기물이 존재하지 않는 경우 이동할 수 없다.")
    @Test
    void 기물이_존재하지_않는_경우_이동할_수_없다() {
        Game game = createGame("1", "1");
        Position sourcePosition = Position.of(5, 5);
        Position targetPosition = Position.of(1, 1);

        Assertions.assertThatThrownBy(() -> game.move(sourcePosition, targetPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("양측 궁이 모두 존재하면 게임이 종료되지 않는다.")
    @Test
    void 양측_궁이_모두_존재하면_게임이_종료되지_않는다() {
        Game game = createGame("1", "1");

        assertThat(game.isGameEnd()).isFalse();
    }

    @DisplayName("한쪽 궁이 없으면 게임이 종료된다.")
    @Test
    void 한쪽_궁이_없으면_게임이_종료된다() {
        Board board = new Board(Map.of(
            Position.of(5, 2), PieceType.KING.create(Side.HAN)
        ));
        Game game = new Game(board);

        assertThat(game.isGameEnd()).isTrue();
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
                Game game = createGame("1", "1");
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 3);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Chariot.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("차가 가로로 이동한다.")
            void 차가_가로로_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 1);
                Position mid = Position.of(1, 2);
                Position target = Position.of(3, 2);

                moveBySide(game, Side.HAN, source, mid);
                moveBySide(game, Side.HAN, mid, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("차가 대각선으로 이동하면 예외가 발생한다.")
            void 차가_대각선으로_이동하면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 1);
                Position target = Position.of(3, 3);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("상")
        class ElephantMove {

            @Test
            @DisplayName("상이 대각선 방향으로 이동한다.")
            void 상이_대각선_방향으로_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Elephant.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("상이 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 상이_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 2);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("마")
        class HorseMove {

            @Test
            @DisplayName("마가 날 일자로 이동한다.")
            void 마가_날_일자로_이동한다() {
                Game game = createGame("1", "4");
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 3);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Horse.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("마가 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 마가_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                Game game = createGame("1", "4");
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("포")
        class CannonMove {

            @Test
            @DisplayName("포가 기물을 하나 뛰어넘어 이동한다.")
            void 포가_기물을_하나_뛰어넘어_이동한다() {
                Game game = createGame("1", "1");

                moveBySide(game, Side.HAN, Position.of(4, 1), Position.of(4, 2));
                moveBySide(game, Side.HAN, Position.of(4, 2), Position.of(4, 3));

                moveBySide(game, Side.HAN, Position.of(2, 3), Position.of(5, 3));

                assertThat(game.getBoard().get(Position.of(5, 3))).isInstanceOf(Cannon.class);
            }

            @Test
            @DisplayName("포가 뛰어넘을 기물이 없으면 예외가 발생한다.")
            void 포가_뛰어넘을_기물이_없으면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(2, 3);
                Position target = Position.of(5, 3);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("포가 포를 뛰어넘으면 예외가 발생한다.")
            void 포가_포를_뛰어넘으면_예외가_발생한다() {
                Game game = createGame("1", "1");

                moveBySide(game, Side.HAN, Position.of(6, 1), Position.of(6, 2));
                moveBySide(game, Side.HAN, Position.of(6, 2), Position.of(6, 3));
                moveBySide(game, Side.HAN, Position.of(8, 3), Position.of(5, 3));

                Assertions.assertThatThrownBy(
                        () -> moveBySide(game, Side.HAN, Position.of(2, 3), Position.of(7, 3)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("포를 넘어갈 수 없습니다.");
            }
        }

        @Nested
        @DisplayName("사")
        class GuardMove {

            @Test
            @DisplayName("사가 한 칸 이동한다.")
            void 사가_한_칸_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(4, 1);
                Position target = Position.of(5, 1);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Guard.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("사가 두 칸 이상 이동하면 예외가 발생한다.")
            void 사가_두_칸_이상_이동하면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(4, 1);
                Position target = Position.of(4, 3);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("궁")
        class KingMove {

            @Test
            @DisplayName("궁이 한 칸 이동한다.")
            void 궁이_한_칸_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 1);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(King.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("궁이 두 칸 이상 이동하면 예외가 발생한다.")
            void 궁이_두_칸_이상_이동하면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 4);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("졸/병")
        class SoldierMove {

            @Test
            @DisplayName("졸이 앞으로 한 칸 이동한다.")
            void 졸이_앞으로_한_칸_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 6);

                moveBySide(game, Side.CHO, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("병이 앞으로 한 칸 이동한다.")
            void 병이_앞으로_한_칸_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 4);
                Position target = Position.of(1, 5);

                moveBySide(game, Side.HAN, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(game.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("졸이 옆으로 한 칸 이동한다.")
            void 졸이_옆으로_한_칸_이동한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 7);
                Position target = Position.of(2, 7);

                moveBySide(game, Side.CHO, source, target);

                assertThat(game.getBoard().get(target)).isInstanceOf(Soldier.class);
            }

            @Test
            @DisplayName("졸이 뒤로 이동하면 예외가 발생한다.")
            void 졸이_뒤로_이동하면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 8);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.CHO, source, target))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("적 기물 잡기")
        class CapturePiece {

            @Test
            @DisplayName("적 기물을 잡을 수 있다.")
            void 적_기물을_잡을_수_있다() {
                Game game = createGame("1", "1");

                moveBySide(game, Side.HAN, Position.of(1, 4), Position.of(2, 4));
                moveBySide(game, Side.HAN, Position.of(1, 1), Position.of(1, 7));

                assertThat(game.getBoard().get(Position.of(1, 7))).isInstanceOf(Chariot.class);
                assertThat(game.getBoard().get(Position.of(1, 7)).isSameSide(Side.HAN)).isTrue();
            }

            @Test
            @DisplayName("아군 기물을 잡으면 예외가 발생한다.")
            void 아군_기물을_잡으면_예외가_발생한다() {
                Game game = createGame("1", "1");
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 4);

                Assertions.assertThatThrownBy(() -> moveBySide(game, Side.HAN, source, target))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("아군 기물은 잡을 수 없습니다.");
            }
        }
    }

    private Game createGame(String choFormation, String hanFormation) {
        Board board = BoardFactory.createBoard(Formation.from(choFormation), Formation.from(hanFormation));
        return new Game(board);
    }

    private void moveBySide(Game game, Side side, Position source, Position target) {
        if (game.getCurrentTurn() != side) {
            passCurrentTurn(game);
        }
        game.move(source, target);
    }

    private void passCurrentTurn(Game game) {
        if (game.getCurrentTurn() == Side.CHO) {
            moveOneOf(game, new MoveCandidate[]{
                new MoveCandidate(9, 7, 9, 6),
                new MoveCandidate(9, 6, 8, 6),
                new MoveCandidate(8, 6, 8, 5),
                new MoveCandidate(8, 5, 7, 5),
                new MoveCandidate(7, 7, 7, 6),
                new MoveCandidate(5, 7, 5, 6),
                new MoveCandidate(3, 7, 3, 6),
                new MoveCandidate(1, 7, 1, 6)
            });
            return;
        }

        moveOneOf(game, new MoveCandidate[]{
            new MoveCandidate(9, 4, 9, 5),
            new MoveCandidate(9, 5, 8, 5),
            new MoveCandidate(8, 5, 8, 6),
            new MoveCandidate(8, 6, 7, 6),
            new MoveCandidate(7, 4, 7, 5),
            new MoveCandidate(5, 4, 5, 5),
            new MoveCandidate(3, 4, 3, 5),
            new MoveCandidate(1, 4, 1, 5)
        });
    }

    private void moveOneOf(Game game, MoveCandidate[] candidates) {
        for (MoveCandidate candidate : candidates) {
            try {
                game.move(candidate.source, candidate.target);
                return;
            } catch (IllegalArgumentException ignored) {
            }
        }
        throw new IllegalStateException("턴 전환을 위한 이동 가능한 수가 없습니다.");
    }

    private static class MoveCandidate {

        private final Position source;
        private final Position target;

        private MoveCandidate(int sourceX, int sourceY, int targetX, int targetY) {
            this.source = Position.of(sourceX, sourceY);
            this.target = Position.of(targetX, targetY);
        }
    }
}
