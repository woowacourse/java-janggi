package domain;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Piece;
import view.SangMaOrderCommand;

public class JanggiManager {

    private final Board board;

    public JanggiManager(SangMaOrderCommand hanSangMaOrderCommand, SangMaOrderCommand choSangMaOrderCommand) {
        BoardGenerator boardGenerator = new BoardGenerator();
        this.board = boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    public boolean isThereWang(Point destination) {
        Node destinationNode = board.findNodeByPoint(destination);
        if (!board.existsPieceByNode(destinationNode)) {
            return false;
        }
        return board.hasTypeByNode(destinationNode, PieceType.WANG);
    }

    public void movePiece(Point source, Point destination) {
        Node sourceNode = board.findNodeByPoint(source);
        Node destinationNode = board.findNodeByPoint(destination);

        Piece sourcePiece = board.findPieceByNode(sourceNode);
        if (!sourcePiece.canMove(sourceNode, destinationNode, board)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }

        board.putPiece(destinationNode, sourcePiece);
        board.removePieceByNode(sourceNode);
    }

    public Board board() {
        return board;
    }
}
