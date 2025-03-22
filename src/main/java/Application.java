import java.util.List;
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
            String choiceDeparture = inputView.choiceDeparture();
            List<String> departureColumnAndRow = InputParser.split(choiceDeparture);
            Position departure = new Position(departureColumnAndRow.get(0), departureColumnAndRow.get(1));
            Piece piece = pieces.findPieceBy(departure);

            String choiceArrival = inputView.choiceArrivalOf(piece);
            List<String> arrivalColumnAndRow = InputParser.split(choiceArrival);
            Position arrival = new Position(arrivalColumnAndRow.get(0), arrivalColumnAndRow.get(1));
            moveOfPiece(pieces, departure, arrival);
        }
    }

    private static void moveOfPiece(Pieces pieces, Position departure, Position arrival) {
        Piece piece = pieces.findPieceBy(departure);
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
