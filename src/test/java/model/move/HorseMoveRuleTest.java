package model.move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import model.position.Position;
import org.junit.jupiter.api.Test;

class HorseMoveRuleTest {
    @Test
    void 마의_이동패턴은_8개이다() {
        Move move = Move.of(Position.of(5, 5), Position.of(7,4));
        MoveRule horseMoveRule = new HorseMoveRule();
        List<MovePattern> patternList = horseMoveRule.patterns(move);
        int expected = 8;
        int actual = patternList.size();

        assertEquals(expected, actual);
    }
}