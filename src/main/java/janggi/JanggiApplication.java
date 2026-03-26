package janggi;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.dto.GameStatusInfo;
import janggi.dto.PositionInfo;
import janggi.ui.InputView;
import janggi.ui.OutputView;
import janggi.util.Console;
import janggi.util.FileParser;
import java.util.List;

public class JanggiApplication {
    public static void main(String[] args) {
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/janggi.csv");
        Board board = new Board();
        board.init(positionInfos);
        JanggiGame game = new JanggiGame(board);
        OutputView.printGameStatus(GameStatusInfo.from(game.getBoardStatus()));
        while (!game.isFinished()) {
            List<Point> points = InputView.readPoints();
            game.play(points.get(0), points.get(1));
            OutputView.printGameStatus(GameStatusInfo.from(game.getBoardStatus()));
        }
        OutputView.printWinner(game.getWinner());
        Console.close();
    }
}
