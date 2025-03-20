import java.util.Optional;
import model.Cannon;
import model.Piece;
import model.Pieces;
import model.Position;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        Pieces pieces = Pieces.createAndInit();
        outputView.printJanggiStart();
        while (true) {
            showCurrentPositionOfPieces(pieces);
            String choiceDirection = inputView.printMovePiece();
            String[] choices = choiceDirection.split(",");
            Piece piece = pieces.findPiece(
                new Position(Integer.parseInt(choices[0]), Integer.parseInt(choices[1])));

            String moveDirection = inputView.printMovePosition(piece);
            Position destinationDirection = Position.initFrom(moveDirection);

            if (piece instanceof Cannon) {
                pieces.validateCannonMove(piece, destinationDirection);
            } else {
                pieces.validateCanMove(piece, destinationDirection);
            }
        }
    }

    private static void showCurrentPositionOfPieces(Pieces pieces) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Optional<Piece> piece = pieces.findPieceOfView(new Position(i, j));
                outputView.printPieceOrHyphen(piece);
            }
            outputView.printBlankLine();
        }
    }

}
