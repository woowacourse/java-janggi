import java.util.Optional;
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
            Piece piece = pieces.findPiece(Position.initFrom(choiceDirection));
            String moveDirection = inputView.printMovePosition(piece);
            Position destinationDirection = Position.initFrom(moveDirection);
            moveOfPiece(piece, pieces, destinationDirection);
        }
    }

    private static void moveOfPiece(Piece piece, Pieces pieces, Position destinationDirection) {
        if (piece.isCannon()) {
            pieces.validateCannonMove(piece, destinationDirection);
            return;
        }
        pieces.validateCanMove(piece, destinationDirection);
    }

    private static void showCurrentPositionOfPieces(Pieces pieces) {
        System.out.println("  ０１２３４５６７８");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 9; j++) {
                Optional<Piece> piece = pieces.findPieceOfView(new Position(i, j));
                outputView.printPieceOrHyphen(piece);
            }
            outputView.printBlankLine();
        }
    }

}
