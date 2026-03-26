package janggi;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.dto.PositionInfo;
import janggi.ui.InputView;
import janggi.ui.OutputView;
import janggi.util.FileParser;
import java.util.List;

public class JanggiApplication {
    public static void main(String[] args) {
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/test.csv");
        Board board = new Board();
        board.init(positionInfos);
        JanggiGame game = new JanggiGame(board);

        while (!game.isFinished()) {
            List<Point> points = InputView.readPoints();
            game.play(points.get(0), points.get(1));
        }
        OutputView.printWinner(game.getWinner());
    }
}
