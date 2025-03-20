package domain.JanggiBoard;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import domain.piece.포;
import java.util.List;
import java.util.Map;

public final class JanggiBoard {

    private final Map<JanggiPosition, JanggiPiece> janggiBoard;

    public JanggiBoard(final JanggiBoardInitializer initializer) {
        this.janggiBoard = initializer.initializeJanggiBoard();
    }

    public void movePiece(final JanggiPosition origin, final JanggiPosition destination) {
        destination.validatePositionInBoardBound();
        JanggiPiece piece = getPieceFrom(origin);

        if (!piece.is포()) {
            moveOtherPiece(piece, origin, destination);
        }
        if (piece.is포()) {
            move포(piece, origin, destination);
        }

        checkTargetPieceTeam(piece, destination);
        janggiBoard.put(destination, piece);
    }

    public JanggiPiece getPieceFrom(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private void moveOtherPiece(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        checkHurdleExistInRoute(piece, origin, destination);
        janggiBoard.put(origin, new Empty());
        JanggiPiece targetPiece = janggiBoard.get(destination);
        targetPiece.captureIfNotMyTeam(piece);
    }

    private void move포(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        checkOnlyOneHurdleInRoute(piece, origin, destination);
        checkHurdlePieceNot포(origin, destination);
        checkTargetPieceNot포(destination);

        janggiBoard.put(origin, new Empty());
        JanggiPiece pieceInDanger = janggiBoard.get(destination);
        if (!pieceInDanger.isEmpty()) {
            capturePieceIfNot포AndNotMySide(piece, pieceInDanger);
        }
    }

    private void checkTargetPieceTeam(final JanggiPiece movePiece, final JanggiPosition destination) {
        JanggiPiece targetPiece = getPieceFrom(destination);
        if (movePiece.isMyTeam(targetPiece)) {
            throw new IllegalStateException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void checkHurdleExistInRoute(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        if (getHurdleCount(piece, origin, destination) > 0) {
            throw new IllegalArgumentException("경로에 장애물이 있어서 기물을 움직일 수 없습니다.");
        }
    }

    private void checkOnlyOneHurdleInRoute(JanggiPiece piece, JanggiPosition origin, JanggiPosition destination) {
        int hurdleCount = getHurdleCount(piece, origin, destination);
        if (hurdleCount != 1) {
            throw new IllegalStateException("경로에 장애물이 1개 있어야 움직일 수 있습니다.");
        }
    }

    private void checkHurdlePieceNot포(JanggiPosition origin, JanggiPosition destination) {
        JanggiPiece hurdlePiece = getFirstHurdlePiece(origin, destination);
        if (hurdlePiece.is포()) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
    }

    private void checkTargetPieceNot포(JanggiPosition destination) {
        if (getPieceFrom(destination).is포()) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
    }

    private static void capturePieceIfNot포AndNotMySide(final JanggiPiece piece, final JanggiPiece pieceInDanger) {
        if (!pieceInDanger.getClass().equals(포.class)) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
        pieceInDanger.captureIfNotMyTeam(piece);
    }

    private JanggiPiece getFirstHurdlePiece(final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece piece = getPieceFrom(origin);
        JanggiPiece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(origin, destination);
        JanggiPosition newPosition = origin;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (existPiece(newPosition)) {
                hurdlePiece = getPieceFrom(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCount(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        List<Pattern> path = piece.findPath(origin, destination);
        List<Pattern> patterns = path.subList(0, path.size() - 1);
        int count = 0;
        JanggiPosition newPosition = origin;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (existPiece(newPosition)) {
                count++;
            }
        }
        return count;
    }

    private boolean existPiece(final JanggiPosition newPosition) {
        return !getPieceFrom(newPosition).isEmpty();
    }
}
