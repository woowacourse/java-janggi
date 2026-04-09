package core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import participant.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class JanggiGameTest {

    @Nested
    @DisplayName("공격 차례를 검증한다")
    class TurnCheck {

        @Test
        void 초의_턴으로_시작할_때_한의_기물로_공격하는_경우_예외를_던진다() {
            // given
            Turn choTurn = Turn.CHO_TURN;
            Position departure = toPosition(0, 0);
            Position destination = toPosition(1, 0);
            Piece hanPiece = piece(Side.HAN, PieceType.CHA);

            JanggiGame game = game(choTurn, Map.of(
                departure, hanPiece
            ));
            // when & then
            assertThatThrownBy(() -> game.move(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 기물을_한_번_이동시키면_턴이_바뀐다() {
            // given
            Position choDeparture = toPosition(0, 0);
            Position choDestination = toPosition(1, 0);
            Position hanDeparture = toPosition(9, 0);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);
            Piece hanPiece = piece(Side.HAN, PieceType.CHA);

            JanggiGame game = startWith(Map.of(
                choDeparture, choPiece,
                hanDeparture, hanPiece
            ));
            // when & then
            game = game.move(choDeparture, choDestination);

            assertThat(game.getTurnSide()).isEqualTo(Side.HAN);
        }

        @Test
        void 기본_게임은_초가_먼저_시작한다() {
            // given
            Position departure = toPosition(9, 0);
            Position destination = toPosition(8, 0);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);

            JanggiGame janggiGame = startWith(Map.of(
                departure, choPiece
            ));
            // when
            assertThatCode(() -> janggiGame.move(departure, destination))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("게임의 진행, 종료 상태를 검증한다")
    class GameEntityStatusCheck {

        @Test
        void 궁이_잡히면_게임이_종료된다() {
            // given
            Position departure = toPosition(3, 4);
            Position destination = toPosition(8, 4);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);
            Piece hanGung = piece(Side.HAN, PieceType.GUNG);

            JanggiGame game = startWith(Map.of(
                departure, choPiece,
                destination, hanGung
            ));
            // when
            game = game.move(departure, destination);
            // then
            assertThat(game.isOver()).isTrue();
        }

        @Test
        void 궁이_잡히지_않으면_게임이_계속_진행된다() {
            // given
            Position departure = toPosition(1, 4);
            Position destination = toPosition(1, 5);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);
            Piece hanGung = piece(Side.HAN, PieceType.GUNG);

            JanggiGame game = startWith(Map.of(
                departure, choPiece,
                toPosition(8, 4), hanGung
            ));
            // when
            game = game.move(departure, destination);
            // then
            assertThat(game.isOver()).isFalse();
        }

        @Test
        void 게임이_종료된_상태에_기물을_움직일_경우_예외를_던진다() {
            // given
            Position departure = toPosition(1, 4);
            Position destination = toPosition(1, 5);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);

            JanggiGame game = new JanggiGame(
                new Board(Map.of(departure, choPiece)),
                Turn.CHO_TURN,
                GameStatus.HAN_WIN_BY_GUNG
            );
            // when & then
            assertThatThrownBy(() -> game.move(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("승리 진영을 조회한다")
    class WinnerSideCheck {

        @Test
        void 게임이_종료되지_않았을_때_승리_진영을_조회하는_경우_예외를_던진다() {
            // given
            JanggiGame game = startWith(Map.of());
            // when & then
            assertThatThrownBy(game::getResult)
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 초가_한의_궁을_잡아_종료되면_초가_승리한다() {
            // given
            Position departure = toPosition(3, 4);
            Position destination = toPosition(8, 4);
            Piece choPiece = piece(Side.CHO, PieceType.CHA);
            Piece hanGung = piece(Side.HAN, PieceType.GUNG);

            JanggiGame game = startWith(Map.of(
                departure, choPiece,
                destination, hanGung
            ));
            JanggiGame finished = game.move(departure, destination);
            // when
            GameStatus result = finished.getResult();
            // then
            assertThat(result).isEqualTo(GameStatus.CHO_WIN_BY_GUNG);
        }

        @Test
        void 한이_초의_궁을_잡아_종료되면_한이_승리한다() {
            /// given
            Position departure = toPosition(3, 4);
            Position destination = toPosition(8, 4);
            Piece hanPiece = piece(Side.HAN, PieceType.CHA);
            Piece choGung = piece(Side.CHO, PieceType.GUNG);

            JanggiGame game = game(Turn.HAN_TURN, Map.of(
                departure, hanPiece,
                destination, choGung
            ));
            JanggiGame finished = game.move(departure, destination);
            // when
            GameStatus result = finished.getResult();
            // then
            assertThat(result).isEqualTo(GameStatus.HAN_WIN_BY_GUNG);
        }

        @Test
        void 장군_없이_게임이_종료된_경우_남아있는_기물의_점수_합이_초가_높으면_초가_승리한다() {
            // given
            Piece choPiece = piece(Side.CHO, PieceType.CHA);
            Piece hanPiece = piece(Side.HAN, PieceType.JOL_BYEONG);

            JanggiGame game = new JanggiGame(
                new Board(Map.of(
                    toPosition(0, 0), choPiece,
                    toPosition(9, 0), hanPiece
                )),
                Turn.CHO_TURN,
                GameStatus.PLAYING
            );
            // when
            JanggiGame endGame = game.endByScore();
            // then
            assertThat(endGame.getResult()).isEqualTo(GameStatus.CHO_WIN_BY_SCORE);
        }

        @Test
        void 장군_없이_게임이_종료된_경우_남아있는_기물의_점수_합이_한이_높으면_한이_승리한다() {
            // given
            Piece hanPiece = piece(Side.HAN, PieceType.CHA);
            Piece choPiece = piece(Side.CHO, PieceType.JOL_BYEONG);

            JanggiGame game = new JanggiGame(
                new Board(Map.of(
                    toPosition(0, 0), hanPiece,
                    toPosition(9, 0), choPiece
                )),
                Turn.CHO_TURN,
                GameStatus.PLAYING
            );
            // when
            JanggiGame endGame = game.endByScore();
            // then
            assertThat(endGame.getResult()).isEqualTo(GameStatus.HAN_WIN_BY_SCORE);
        }
    }

    private static Position toPosition(int row, int column) {
        return new Position(row, column);
    }

    private static Piece piece(Side side, PieceType type) {
        return new Piece(side, type);
    }

    private static JanggiGame startWith(Map<Position, Piece> pieces) {
        return JanggiGame.startWith(new Board(pieces));
    }

    private static JanggiGame game(Turn turn, Map<Position, Piece> pieces) {
        return new JanggiGame(new Board(pieces), turn, GameStatus.PLAYING);
    }
}