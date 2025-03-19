import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Piece;
import java.util.Map;
import view.OutputView;
import view.SangMaOrderCommand;

public class Application {

    public static void main(String[] args) {
        BoardInitializer boardInitializer = new BoardInitializer();
        Map<Point, Node> nodeByPoint = boardInitializer.initializeNodesAndEdges();
        Map<Node, Piece> pieceByNode = boardInitializer.initializePiecePosition(nodeByPoint,
                SangMaOrderCommand.SANG_MA_SANG_MA,
                SangMaOrderCommand.SANG_MA_MA_SANG);
        Board board = new Board(pieceByNode, nodeByPoint);
        OutputView.printBoard(board);
    }
}
