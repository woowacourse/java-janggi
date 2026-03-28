package domain;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import domain.board.dto.JanggiBoardView;
import org.junit.jupiter.api.Test;
import view.OutputWriter;

public class BoardViewTest {

    @Test
    void test() {
        JanggiIntersectionGenerator janggiIntersectionGenerator = new JanggiIntersectionGenerator(Formation.ELEPHANT_HORSE_ELEPHANT_HORSE, Formation.HORSE_ELEPHANT_ELEPHANT_HORSE);
        JanggiBoard janggiBoard = new JanggiBoard(janggiIntersectionGenerator);

        OutputWriter outputWriter = new OutputWriter();
        JanggiBoardView boardView = JanggiBoardView.from(janggiBoard);
        outputWriter.printJanggiBoard(boardView);
    }

}
