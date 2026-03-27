package janggi;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.presentation.dto.GameStatusInfo;
import janggi.presentation.dto.MoveCommand;
import janggi.presentation.dto.PositionInfo;
import janggi.presentation.ui.InputView;
import janggi.presentation.ui.OutputView;
import janggi.util.Console;
import janggi.util.FileParser;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JanggiApplication {
    public static void main(String[] args) {
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/janggi.csv");
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        positionInfos.forEach(info -> pieces.put(info.point(), info.piece()));
        Board board = new Board();
        board.init(pieces);
        JanggiGame game = new JanggiGame(board);

        OutputView.printGameStatus(GameStatusInfo.from(game.getBoardStatus()));
        OutputView.printStartGame();
        while (!game.isFinished()) {
            MoveCommand points = InputView.readPoints();
            game.play(points.from(), points.to());
            OutputView.printGameStatus(GameStatusInfo.from(game.getBoardStatus()));
        }
        OutputView.printWinner(game.getWinner());
        Console.close();
    }
}
