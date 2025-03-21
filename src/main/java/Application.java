import java.util.Optional;
import model.Piece;
import model.PieceInitializer;
import model.Pieces;
import model.Position;
import utils.InputParser;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        Pieces pieces = new Pieces(PieceInitializer.generate());
        outputView.printJanggiStart();
        while (true) {
            showCurrentPositionOfPieces(pieces);
            String choiceDirection = inputView.printMovePiece();
            Position departure = Position.initFrom(InputParser.split(choiceDirection));
            Piece piece = pieces.findPiece(departure);

            String moveDirection = inputView.printMovePosition(piece);
            Position arrival = Position.initFrom(InputParser.split(moveDirection));
            moveOfPiece(pieces, departure, arrival);
        }
    }

    private static void moveOfPiece(Pieces pieces, Position departure, Position arrival) {
        Piece piece = pieces.findPiece(departure);
        if (piece.isCannon()) {
            //pieces.validateCannonMove(piece, destinationDirection);
            return;
        }
        pieces.validateCanMove(departure, arrival);
    }

    private static void showCurrentPositionOfPieces(Pieces pieces) {
        System.out.println("  ０１２３４５６７８");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 9; j++) {
                Optional<Piece> piece = pieces.findPieceOfNullable(new Position(i, j));
                outputView.printPieceOrHyphen(piece);
            }
            outputView.printBlankLine();
        }
    }

}
