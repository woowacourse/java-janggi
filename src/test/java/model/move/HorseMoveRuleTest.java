package model.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseMoveRuleTest {

    @Test
    void 마는_총_8가지_방향의_이동_패턴을_생성해야_한다() {
        HorseMoveRule rule = new HorseMoveRule();
        Move dummyMove = new Move(Position.of(5, 5), Position.of(7, 6));

        List<MovePattern> patterns = rule.patterns(dummyMove);

        assertThat(patterns).hasSize(8);
    }

    @ParameterizedTest()
    @CsvSource({
            "5, 5, 3, 4",
            "5, 5, 3, 6",
            "5, 5, 4, 7",
            "5, 5, 6, 7"
    })
    void 마의_패턴은_유효한_이동에_대해_매칭되어야_한다(int fx, int fy, int tx, int ty) {
        HorseMoveRule rule = new HorseMoveRule();
        Move move = new Move(Position.of(fx, fy), Position.of(tx, ty));
        Board emptyBoard = new Board();

        boolean hasMatchedPattern = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, emptyBoard, Country.CHO));

        assertThat(hasMatchedPattern).isTrue();
    }
}