package view;

import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import view.dto.ScoreDto;

public class ApplicationView {

    private final InputView inputView = new InputView();
    private final BoardOutputView boardOutputView = new BoardOutputView();
    private final GuideOutputView guideOutputView = new GuideOutputView();
    private final ResultOutputView resultOutputView = new ResultOutputView();
    private final ErrorOutputView errorOutputView = new ErrorOutputView();

    public boolean askStartNewGame() {
        guideOutputView.printAskNewGameGuide();

        return inputView.askStartNewGame();
    }

    public void printNewGameId(int newGameId) {
        guideOutputView.printNewGameId(newGameId);
    }

    public int readExistGameId(List<Integer> existGameIds) {
        guideOutputView.printGameIdGuide(existGameIds);

        return inputView.readGameId();
    }

    public ChoWings readChowings() {
        guideOutputView.printWingInputGuide(Side.CHO);

        return inputView.readChoWings();
    }

    public HanWings readHanWings() {
        guideOutputView.printWingInputGuide(Side.HAN);

        return inputView.readHanWings();
    }

    public Intersection readSelectPieceToMove(
            Map<Intersection, Piece> board,
            Side side
    ) {
        guideOutputView.printSelectPieceGuide(side);
        boardOutputView.printBoard(board);

        return inputView.readIntersection();
    }

    public Intersection readMovePiece(
            Map<Intersection, Piece> board,
            List<Intersection> movableIntersections
    ) {
        guideOutputView.printMovePieceGuide();
        boardOutputView.printBoard(board, movableIntersections);

        return inputView.readIntersection();
    }

    public void printWinner(Side winner, List<ScoreDto> scores) {
        resultOutputView.printWinner(winner, scores);
    }

    public void printError(Exception exception) {
        errorOutputView.printErrorMessage(exception);
    }
}
