import java.util.List;
import java.util.Optional;
import model.Column;
import model.Piece;
import model.PieceInitializer;
import model.Pieces;
import model.Position;
import model.Row;
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
            List<Integer> columnAndRowOfDeparture = InputParser.splitAndConvert(choiceDeparture);
            Position departure = new Position(columnAndRowOfDeparture);
            Piece departurePiece = pieces.findPieceBy(departure);

            String choiceArrival = inputView.choiceArrivalOf(departurePiece);
            List<Integer> columnAndRowOfArrival = InputParser.splitAndConvert(choiceArrival);
            Position arrival = new Position(columnAndRowOfArrival);
            //moveOfPiece(pieces, departure, arrival);
        }
    }
/*
    private static void moveOfPiece(Pieces pieces, Position departure, Position arrival) {
        Piece piece = pieces.findPieceBy(departure);
        if (piece.isCannon()) {
            //pieces.validateCannonMove(piece, destinationDirection);
            return;
        }
        pieces.validateCanMove(departure, arrival);
    }


 */
    private static void showCurrentPositionOfPieces(Pieces pieces) {

        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                Optional<Piece> piece = pieces.findPieceOfNullable(
                    new Position(column, row));
                outputView.printPieceOrHyphen(piece);
            }
            outputView.printBlankLine();
        }
    }

}
