package Controller;

import game.Board;
import game.Country;
import game.StartingPosition;
import game.Team;
import java.util.List;
import piece_initiaizer.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import position.PositionRank;
import view.InputView;
import view.OutputView;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;
    private Board board;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        StartingPosition startingCho = StartingPosition.fromOption(inputView.getStartingPosition());
        StartingPosition startingHan = StartingPosition.fromOption(inputView.getStartingPosition());

        Team cho = new Team(startingCho, new StaticPieceInitializer(), Country.CHO);
        Team han = new Team(startingHan, new StaticPieceInitializer(), Country.HAN);
        board = new Board(cho, han);

        outputView.displayBoard(board);

        while (true) {
            List<String> moveInfo = inputView.readMoveCommand();
            Position source = parsePosition(moveInfo.get(0), moveInfo.get(1));
            Position target = parsePosition(moveInfo.get(2), moveInfo.get(3));

            if (source != null && target != null) {
                board.move(source, target);
                outputView.displayBoard(board);
            }
        }
    }

    private Position parsePosition(String fileStr, String rankStr) {
        try {
            PositionFile file = PositionFile.fromString(fileStr);
            int rank = Integer.parseInt(rankStr);
            return new Position(file, new PositionRank(rank));
        } catch (Exception e) {
            outputView.printMessage("[ERROR] 올바른 위치 형식이 아닙니다.");
            return null;
        }
    }
}
