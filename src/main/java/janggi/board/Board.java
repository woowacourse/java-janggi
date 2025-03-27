package janggi.board;

import janggi.board.strategy.PlaceStrategy;
import janggi.exception.GameOverException;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;

import java.util.Collections;
import java.util.Map;

public class Board {
    public static final int ROW_SIZE = 10;
    public static final int COLUMN_SIZE = 9;

    private final Map<Position, Piece> board;

    public Board(PlaceStrategy placeStrategy) {
        this.board = placeStrategy.initialize();
    }

    public void movePiece(Position start, Position goal, Team team) {
        Piece attacker = board.get(start);
        validateMove(start, goal, team, attacker);
        Piece target = move(start, goal);
        validateGeneralTarget(target);
    }

    private void validateMove(Position start, Position goal, Team team, Piece attacker) {
        isExistPieceAtPosition(start);
        validateAttackTurn(team, attacker);
        attacker.validateMovable(this, start, goal);
    }

    private void isExistPieceAtPosition(Position start) {
        if (!isExists(start)) {
            throw new IllegalArgumentException("[ERROR] 출발 지점에 기물이 존재하지 않습니다.");
        }
    }

    private void validateAttackTurn(Team team, Piece attacker) {
        if (!attacker.isSameTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 같은 진영의 기물만 움직일 수 있습니다.");
        }
    }

    private boolean isExists(Position position) {
        return board.containsKey(position);
    }

    private void validateGeneralTarget(Piece target) {
        if (target != null && target.isSameType(PieceType.GENERAL)) {
            throw new GameOverException();
        }
    }

    private Piece move(Position start, Position goal) {
        Piece piece = board.remove(start);
        return board.put(goal, piece);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public boolean existPiece(Position position) {
        return board.containsKey(position);
    }
}
