package game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import board.Board;
import board.Position;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Piece;
import piece.Soldier;
import piece.Team;

class JanggiGameTest {

    @Test
    void 장기_게임은_초나라부터_기물을_움직인다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.BLUE)));
        Turn turn = new Turn();
        JanggiGame janggiGame = new JanggiGame(board, turn);

        assertDoesNotThrow(() -> janggiGame.move(position, new Position(3, 4)));
    }

    @Test
    void 초나라_다음_차례는_한나라가_기물은_움직인다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.RED)));
        Turn turn = new Turn();
        JanggiGame janggiGame = new JanggiGame(board, turn);
        turn.increaseRound();

        assertDoesNotThrow(() -> janggiGame.move(position, new Position(5, 4)));
    }

    @Test
    void 초나라_차례에_한나라_기물을_움직일_수_없다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.RED)));
        Turn turn = new Turn();
        JanggiGame janggiGame = new JanggiGame(board, turn);

        assertThatThrownBy(() -> janggiGame.move(position, new Position(5, 4)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한나라_차례에_초나라_기물을_움직일_수_없다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.BLUE)));
        Turn turn = new Turn();
        JanggiGame janggiGame = new JanggiGame(board, turn);
        turn.increaseRound();

        assertThatThrownBy(() -> janggiGame.move(position, new Position(3, 4)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @MethodSource
    @ParameterizedTest
    void 현재_존재하는_기물에_따라_최종_점수를_계산한다(Map<Position, Piece> pieces, double blueScore, double redScore) {
        Board board = new Board(pieces);
        JanggiGame janggiGame = new JanggiGame(board, new Turn());

        assertThat(janggiGame.calculateTotalScore())
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(
                                Team.BLUE, blueScore,
                                Team.RED, redScore
                        )
                );
    }

    private static Stream<Arguments> 현재_존재하는_기물에_따라_최종_점수를_계산한다() {
        Position redPosition = new Position(1, 5);
        Position bluePosition = new Position(10, 5);
        return Stream.of(
                Arguments.of(
                        Map.of(redPosition, new King(Team.RED), bluePosition, new King(Team.BLUE)),
                        0.0, 1.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Chariot(Team.RED), bluePosition, new Chariot(Team.BLUE)),
                        13.0, 14.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Cannon(Team.RED), bluePosition, new Cannon(Team.BLUE)),
                        7.0, 8.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Horse(Team.RED), bluePosition, new Horse(Team.BLUE)),
                        5.0, 6.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Elephant(Team.RED), bluePosition, new Elephant(Team.BLUE)),
                        3.0, 4.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Guard(Team.RED), bluePosition, new Guard(Team.BLUE)),
                        3.0, 4.5
                ),
                Arguments.of(
                        Map.of(redPosition, new Soldier(Team.RED), bluePosition, new Soldier(Team.BLUE)),
                        2.0, 3.5
                )
        );
    }

    @MethodSource
    @ParameterizedTest
    void 게임이_끝났는지_알려준다(Map<Position, Piece> pieces, boolean expected) {
        Board board = new Board(pieces);
        JanggiGame janggiGame = new JanggiGame(board, new Turn());

        assertThat(janggiGame.isFinish()).isEqualTo(expected);
    }

    private static Stream<Arguments> 게임이_끝났는지_알려준다() {
        return Stream.of(
                Arguments.of(Map.of(
                        new Position(9, 5), new King(Team.BLUE)), true
                ),
                Arguments.of(Map.of(
                        new Position(2, 5), new King(Team.RED),
                        new Position(9, 5), new King(Team.BLUE)), false
                ));
    }

}
