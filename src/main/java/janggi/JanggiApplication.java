package janggi;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.dto.PositionInfo;
import janggi.ui.InputView;
import janggi.util.FileParser;
import java.util.List;

public class JanggiApplication {
    public static void main(String[] args) {
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/janggi.csv");
        Board board = new Board();
        board.init(positionInfos);
        JanggiGame game = new JanggiGame(board);

        while (true) {
            List<Point> points = InputView.readPoints();
            game.play(points.get(0), points.get(1));
        }

    }
}
