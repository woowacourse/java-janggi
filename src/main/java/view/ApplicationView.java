package view;

import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class ApplicationView {

    private final InputView inputView = new InputView();
    private final BoardOutputView boardOutputView = new BoardOutputView();
    private final GuideOutputView guideOutputView = new GuideOutputView();
    private final ErrorOutputView errorOutputView = new ErrorOutputView();

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

    public void printError(Exception exception) {
        errorOutputView.printErrorMessage(exception);
    }
}
