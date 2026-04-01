package janggi.model.board;

import janggi.model.Team;
import janggi.model.board.position.Position;
import janggi.model.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Board move(
            Team team,
            Position from,
            Position to
    ) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        Piece pieceAtFrom = board.get(from);

        if (!pieceAtFrom.isSameTeam(team)) {
            throw new IllegalArgumentException("상대편 기물을 움직일 수 없습니다.");
        }

        PositionPath path = pieceAtFrom.getLegalPath(from, to);

        if (!path.isEmpty()) {
            List<Piece> piecesOnPath = path.findPiecesOn(board);
            validateMovePathAndDestination(to, pieceAtFrom, piecesOnPath);
        }

        Map<Position, Piece> movedBoard = new HashMap<>(board);
        movedBoard.put(to, pieceAtFrom);
        movedBoard.remove(from);

        return new Board(movedBoard);
    }

    private void validateMovePathAndDestination(
            Position to,
            Piece pieceAtFrom,
            List<Piece> piecesOnPath
    ) {
        boolean hasTarget = board.containsKey(to);

        if ((!hasTarget && !pieceAtFrom.canPassThrough(piecesOnPath))
                || (hasTarget && !pieceAtFrom.canPassThrough(piecesOnPath, board.get(to)))) {
            throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
        }
    }

    public boolean isGameOver() {
        return !isHanAlive() || !isChoAlive();
    }

    private boolean isChoAlive() {
        return board.values().stream().anyMatch(piece -> piece.isSameTeam(Team.CHO));
    }

    private boolean isHanAlive() {
        return board.values().stream().anyMatch(piece -> piece.isSameTeam(Team.HAN));
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
