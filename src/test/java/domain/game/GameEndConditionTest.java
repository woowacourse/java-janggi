package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.game.condition.BikjangCondition;
import domain.game.condition.ConsecutivePassCondition;
import domain.game.condition.GeneralCapturedCondition;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameEndConditionTest {

    @Test
    void 궁이_잡히면_종료_조건을_만족한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        Board board = new Board(pieces);
        GameRecord record = new GameRecord();

        GeneralCapturedCondition condition = new GeneralCapturedCondition();

        assertThat(condition.isSatisfied(board, record)).isTrue();
    }

    @Test
    void 양측_궁이_모두_있으면_종료_조건을_만족하지_않는다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        Board board = new Board(pieces);
        GameRecord record = new GameRecord();

        GeneralCapturedCondition condition = new GeneralCapturedCondition();

        assertThat(condition.isSatisfied(board, record)).isFalse();
    }

    @Test
    void 연속_패스가_2회이면_종료_조건을_만족한다() {
        Board board = new Board(new HashMap<>());
        GameRecord record = new GameRecord();
        record.recordPass();
        record.recordPass();

        ConsecutivePassCondition condition = new ConsecutivePassCondition();

        assertThat(condition.isSatisfied(board, record)).isTrue();
    }

    @Test
    void 연속_패스가_1회이면_종료_조건을_만족하지_않는다() {
        Board board = new Board(new HashMap<>());
        GameRecord record = new GameRecord();
        record.recordPass();

        ConsecutivePassCondition condition = new ConsecutivePassCondition();

        assertThat(condition.isSatisfied(board, record)).isFalse();
    }

    @Test
    void 패스_사이에_이동이_있으면_연속_패스가_초기화된다() {
        Board board = new Board(new HashMap<>());
        GameRecord record = new GameRecord();
        record.recordPass();
        record.recordMove();
        record.recordPass();

        ConsecutivePassCondition condition = new ConsecutivePassCondition();

        assertThat(condition.isSatisfied(board, record)).isFalse();
    }

    @Test
    void 양측_궁이_같은_열에서_마주보고_사이에_기물이_없으면_빅장이다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        Board board = new Board(pieces);
        GameRecord record = new GameRecord();

        BikjangCondition condition = new BikjangCondition();

        assertThat(condition.isSatisfied(board, record)).isTrue();
    }

    @Test
    void 양측_궁_사이에_기물이_있으면_빅장이_아니다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        pieces.put(new Position(5, 5), new Soldier(Team.CHO));
        Board board = new Board(pieces);
        GameRecord record = new GameRecord();

        BikjangCondition condition = new BikjangCondition();

        assertThat(condition.isSatisfied(board, record)).isFalse();
    }

    @Test
    void 양측_궁이_다른_열에_있으면_빅장이_아니다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 4), new General(Team.CHO));
        pieces.put(new Position(9, 5), new General(Team.HAN));
        Board board = new Board(pieces);
        GameRecord record = new GameRecord();

        BikjangCondition condition = new BikjangCondition();

        assertThat(condition.isSatisfied(board, record)).isFalse();
    }
}
