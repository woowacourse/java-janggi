package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import java.util.Map;

public class JanggiBoard {

    private final Map<JanggiPosition, Piece> janggiBoard;
    private final MoveValidator moveValidator;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
        this.moveValidator = new MoveValidator(this);
    }

    public Map<JanggiPosition, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        Piece targetPiece = getPieceFrom(afterPosition);

//        validateDestinationPiece(piece, targetPiece);
        moveValidator.validateMove(beforePosition, afterPosition);

        changeState(piece, targetPiece);
        changePosition(beforePosition, afterPosition);
    }

    public Piece getPieceFrom(JanggiPosition beforePosition) {
        beforePosition.validateBound();
        return janggiBoard.get(beforePosition);
    }

    private void changeState(Piece piece, Piece targetPiece) {
        piece.updateState();

        if (!targetPiece.isEmpty()) {
            targetPiece.captureIfNotMySide(piece);
        }
    }

    private void changePosition(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);

        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    public boolean isExistPiece(JanggiPosition newPosition) {
        return !getPieceFrom(newPosition).isEmpty();
    }
}
