package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceAttribute;
import janggi.domain.piece.PieceType;
import janggi.domain.Route;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board implements BaseBoard {
    private static final String INVALID_PIECE_SIDE_MESSAGE = "자기 진영의 기물만 움직일 수 있습니다.";

    private final Map<Position, Piece> board;
    private final MaterialScore materialScore;

    public Board(Map<Position, Piece> board, int hanScore, int choScore) {
        this.board = board;
        this.materialScore = new MaterialScore(hanScore, choScore);
    }

    @Override
    public boolean isEmpty(Position position) {
        return board.get(position).isEqualPieceType(PieceType.NONE);
    }

    @Override
    public boolean isEqualPieceType(Position position, PieceType pieceType) {
        return board.get(position).isEqualPieceType(pieceType);
    }

    @Override
    public boolean isAlly(Side side, Position position) {
        return board.get(position).isEqualSide(side);
    }

    public List<List<PieceAttribute>> getCurrentBoard() {
        CurrentBoard currentBoard = CurrentBoard.from(Collections.unmodifiableMap(board));
        return currentBoard.getValues();
    }

    public PieceAttribute move(Position start, Position end, Side movableSide) {
        Piece piece = board.get(start);
        if (!piece.isEqualSide(movableSide)) {
            throw new IllegalArgumentException(INVALID_PIECE_SIDE_MESSAGE);
        }

        Route route = piece.findRoute(start, end);
        piece.validateRoute(route, this);

        movePiece(start, end, piece, movableSide);

        return piece.getPieceInfo();
    }

    public boolean isEndGame() {
        return materialScore.isAnyGungDead();
    }

    public SideScore getScore() {
        return materialScore.getCurrentScore();
    }

    private void movePiece(Position start, Position end, Piece piece, Side side) {
        Piece destinationPiece = board.get(end);

        if(destinationPiece.isEqualPieceType(PieceType.GUNG)) {
            materialScore.updateGungDead(side.getOppositeSide());
        }

        if(!destinationPiece.isEqualPieceType(PieceType.NONE)) {
            materialScore.decreaseScore(side.getOppositeSide(), destinationPiece.getPieceScore());
        }

        board.put(end, piece);
        board.put(start, new Empty());
    }
}
