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

    public boolean isPresentAt(Position position) {
        return board.containsKey(position);
    }

    public boolean canMove(Position from, Position to) {
        Piece piece = board.get(from);
        if (!turn.isCurrentTeam(piece.getTeam())) {
            return false;
        }
        return piece.canMove(from, to);
    }

    public boolean determineMoving(Position from, Position to) {
        Map<Position, Piece> paths =  getPositionPiecesFromPath(from, to);
        Piece piece = board.get(from);
        return piece.determineMovingRule(paths, to);
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

    public void changePiecePosition(Position from, Position to) {
        board.put(to, board.get(from));
        board.remove(from);
    }

    public void changeTurn() {
        this.turn = turn.changeTurn();
    }

    public Turn getTurn() {
        return turn;
    }
}
