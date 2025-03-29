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
    private final Palace palace;

    public Board(PlaceStrategy placeStrategy) {
        this.board = placeStrategy.initialize();
        this.palace = new Palace();
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

    // 출발지와 목적지가 궁성 내부에 있는지
    public boolean isInnerBottomPalace(Position position) {
        return palace.isInnerBottomPalace(position);
    }

    public boolean isInnerUpperPalace(Position position) {
        return palace.isInnerUpperPalace(position);
    }

    // 해당 포지션이 궁성 코너 좌표와 같은지
    public boolean isBottomPalaceCorner(Position other) {
        return palace.isBottomPalaceCorner(other);
    }

    public boolean isUpperPalaceCorner(Position other) {
        return palace.isUpperPalaceCorner(other);
    }

    public Position getBottomPalaceCenter() {
        return palace.getBottomPalaceCenter();
    }

    public Position getUpperPalaceCenter() {
        return palace.getUpperPalaceCenter();
    }

    public double calculateGreenScore() {
        return board.keySet().stream()
                .filter(position -> board.get(position).getTeam() == Team.GREEN)
                .mapToInt(position -> board.get(position).getType().getPoint())
                .sum();
    }

    public double calculateRedScore() {
        return Team.RED.getBonusPoint() + board.keySet().stream()
                .filter(position -> board.get(position).getTeam() == Team.RED)
                .mapToInt(position -> board.get(position).getType().getPoint())
                .sum();
    }
}
