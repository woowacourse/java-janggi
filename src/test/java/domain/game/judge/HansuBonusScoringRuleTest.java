package domain.game.judge;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HansuBonusScoringRuleTest {
    private final HansuBonusScoringRule rule = new HansuBonusScoringRule();

    @Test
    void 초_팀의_기물_점수를_합산한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(1, 2), new Cannon(Team.CHO));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        assertThat(rule.score(board, Team.CHO)).isEqualTo(22.0);
    }

    @Test
    void 한_팀은_1점5의_보너스_점수가_있다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        pieces.put(new Position(1, 2), new Cannon(Team.HAN));
        pieces.put(new Position(1, 3), new Soldier(Team.HAN));
        Board board = new Board(pieces);

        assertThat(rule.score(board, Team.HAN)).isEqualTo(23.5);
    }

    @Test
    void 궁은_점수가_0이다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        Board board = new Board(pieces);

        assertThat(rule.score(board, Team.CHO)).isEqualTo(0.0);
    }

    @Test
    void 초_팀_모든_기물의_점수를_합산한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(1, 9), new Chariot(Team.CHO));
        pieces.put(new Position(3, 2), new Cannon(Team.CHO));
        pieces.put(new Position(3, 8), new Cannon(Team.CHO));
        pieces.put(new Position(1, 2), new Horse(Team.CHO));
        pieces.put(new Position(1, 7), new Horse(Team.CHO));
        pieces.put(new Position(1, 4), new Elephant(Team.CHO));
        pieces.put(new Position(1, 5), new Elephant(Team.CHO));
        pieces.put(new Position(1, 3), new Guard(Team.CHO));
        pieces.put(new Position(1, 6), new Guard(Team.CHO));
        pieces.put(new Position(4, 1), new Soldier(Team.CHO));
        pieces.put(new Position(4, 3), new Soldier(Team.CHO));
        pieces.put(new Position(4, 5), new Soldier(Team.CHO));
        pieces.put(new Position(4, 7), new Soldier(Team.CHO));
        pieces.put(new Position(4, 9), new Soldier(Team.CHO));
        pieces.put(new Position(2, 5), new General(Team.CHO));
        Board board = new Board(pieces);

        assertThat(rule.score(board, Team.CHO)).isEqualTo(72.0);
    }
}
