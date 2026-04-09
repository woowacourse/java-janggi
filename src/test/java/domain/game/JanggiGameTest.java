package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.game.condition.BikjangCondition;
import domain.game.condition.ConsecutivePassCondition;
import domain.game.condition.GameEndCondition;
import domain.game.condition.GeneralCapturedCondition;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private JanggiGame createGame(Map<Position, Piece> pieces) {
        return new JanggiGame(
                Turn.first(),
                new Board(pieces),
                List.of(
                        new GeneralCapturedCondition(),
                        new ConsecutivePassCondition(),
                        new BikjangCondition()
                ),
                new GameRecord(),
                new ScoreCalculator()
        );
    }

    @Test
    void 궁이_잡히면_게임이_종료된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(2, 5), new General(Team.CHO));
        pieces.put(new Position(1, 5), new General(Team.HAN));
        JanggiGame game = createGame(pieces);

        game.move(new Position(1, 1), new Position(1, 5));

        assertThat(game.isRunning()).isFalse();
    }

    @Test
    void 연속_패스로_게임이_종료된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 4), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        JanggiGame game = createGame(pieces);

        game.pass();
        game.pass();

        assertThat(game.isRunning()).isFalse();
    }

    @Test
    void 패스_후_이동하면_연속_패스가_초기화된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 4), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        pieces.put(new Position(5, 1), new Chariot(Team.HAN));
        JanggiGame game = createGame(pieces);

        game.pass();
        game.move(new Position(5, 1), new Position(5, 2));

        assertThat(game.isRunning()).isTrue();
    }

    @Test
    void 종료된_게임에서_이동하면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 4), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        JanggiGame game = createGame(pieces);

        game.pass();
        game.pass();

        assertThatThrownBy(() -> game.pass())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 종료된 게임입니다.");
    }

    @Test
    void 팀별_점수를_계산한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 4), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(5, 3), new Soldier(Team.HAN));
        JanggiGame game = createGame(pieces);

        assertThat(game.scoreOf(Team.CHO)).isEqualTo(13.0);
        assertThat(game.scoreOf(Team.HAN)).isEqualTo(2.0);
    }

    @Test
    void 빅장이면_게임이_종료된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        JanggiGame game = createGame(pieces);

        game.pass();

        assertThat(game.isRunning()).isFalse();
    }
}
