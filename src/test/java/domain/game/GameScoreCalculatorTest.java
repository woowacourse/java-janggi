package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.pieces.Cha;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Side;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameScoreCalculatorTest {

    @Test
    void 빈_보드의_한나라_점수는_1_5점이다() {
        // given
        Board board = new Board(new HashMap<>());
        GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

        // when
        GameScore gameScore = gameScoreCalculator.calculate(board);

        // then
        assertThat(gameScore.hanScore()).isEqualTo(new Score(1.5));
    }

    @Test
    void 초나라_차_하나의_점수를_계산한다() {
        // given
        Position position = new Position(0, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(position, new Cha(Side.CHO));

        Board board = new Board(pieces);
        GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

        // when
        GameScore gameScore = gameScoreCalculator.calculate(board);

        // then
        assertThat(gameScore.choScore()).isEqualTo(new Score(13));
    }

    @Test
    void 한나라_차_하나의_점수를_계산한다() {
        // given
        Position position = new Position(8, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(position, new Cha(Side.HAN));

        Board board = new Board(pieces);
        GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

        // when
        GameScore gameScore = gameScoreCalculator.calculate(board);

        // then
        assertThat(gameScore.hanScore()).isEqualTo(new Score(14.5));
    }

    @Test
    void 한나라_기물_두_개의_점수를_계산한다() {
        // given
        Position chaPosition = new Position(8, 0);
        Position gungPosition = new Position(8, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(chaPosition, new Cha(Side.HAN));
        pieces.put(gungPosition, new Po(Side.HAN));

        Board board = new Board(pieces);
        GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

        // when
        GameScore gameScore = gameScoreCalculator.calculate(board);

        // then
        assertThat(gameScore.hanScore()).isEqualTo(new Score(21.5));
    }
}
