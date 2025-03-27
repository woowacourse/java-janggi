package janggi.board;

import janggi.GameState;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> janggiBoard = new HashMap<>();

    public Board(final List<Piece> pieces) {
        janggiBoard.putAll(
                pieces.stream()
                        .collect((Collectors.toMap(Piece::getBoardPosition, piece -> piece))
                        ));
    }

    public GameState pieceMove(final Position presentPosition, final Position futurePosition) {
        final Piece piece = janggiBoard.get(presentPosition);
        piece.moveTo(futurePosition, janggiBoard);
        return updatePiecePosition(presentPosition, futurePosition);
    }

    private GameState updatePiecePosition(final Position presentPosition, final Position futurePosition) {
        final Piece removePiece = janggiBoard.remove(presentPosition);
        final Piece piece = janggiBoard.get(futurePosition);

        if (isJanggunCapture(piece)) {
            return GameState.END;
        }

        janggiBoard.put(futurePosition, removePiece);
        removePiece.updatePiecePositionBy(futurePosition);
        return GameState.IN_PROGRESS;
    }

    private boolean isJanggunCapture(final Piece piece) {
        return piece != null && PieceType.isJanggun(piece.getPieceProfile().getPieceType());
    }

    public void validateEmptyPieceBy(final Position presentPosition) {
        if (isNotContainPiece(presentPosition)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다. 기물이 존재하는 좌표를 입력해 주세요.");
        }
    }

    private boolean isNotContainPiece(final Position presentPosition) {
        return !janggiBoard.containsKey(presentPosition);
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }
}
