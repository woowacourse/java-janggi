package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;
    private Turn turn = new Turn(Team.HAN);

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public void move(Position from, Position to) {
        if (!hasPieceAt(from)) {
            throw new IllegalArgumentException("해당 출발 위치에는 기물이 존재하지 않습니다");
        }

        if (!isCurrentTeamPiece(from)) {
            throw new IllegalArgumentException("해당 기물은 현재 턴의 진영 기물이 아닙니다.");
        }

        if (!canMoveByBasicMovingRule(from, to)) {
            throw new IllegalArgumentException("해당 기물은 그 위치로 이동할 수 없습니다.");
        }

        if (!canMoveBySpecialMovingRule(from, to)) {
            throw new IllegalArgumentException("해당 기물의 이동 규칙에 맞지 않습니다.");
        }

        movePiece(from, to);
        changeTurn();
    }

    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    public boolean isCurrentTeamPiece(Position from) {
        Piece piece = board.get(from);
        return turn.isCurrentTeam(piece.getTeam());
    }

    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        Piece piece = board.get(from);
        return piece.canMoveByBasicMovingRule(from, to);
    }

    public boolean canMoveBySpecialMovingRule(Position from, Position to) {
        Map<Position, Piece> paths = getPositionPiecesFromPath(from, to);
        Piece piece = board.get(from);
        return piece.canMoveBySpecialMovingRule(paths, to);
    }

    public void movePiece(Position from, Position to) {
        board.put(to, board.get(from));
        board.remove(from);
    }

    public void changeTurn() {
        this.turn = turn.changeTurn();
    }

    private Map<Position, Piece> getPositionPiecesFromPath(Position from, Position to) {
        Piece piece = board.get(from);
        List<Position> paths = piece.findPath(from, to);
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        for (Position position : paths) {
            if (board.containsKey(position)) {
                positionPieces.put(position, board.get(position));
            }
        }
        return positionPieces;
    }
}
