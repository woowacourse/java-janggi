package janggi.domain;

import janggi.exception.BusinessException;
import janggi.exception.game.GameNotOverException;
import janggi.exception.game.KingNotFoundException;
import janggi.exception.move.EmptyPositionException;

import janggi.exception.move.InvalidTargetException;
import janggi.exception.move.SamePositionException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardView {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initialPieces) {
        this.board = new HashMap<>(initialPieces);
    }

    public void move(Position from, Position to) {
        Piece movingPiece = findPieceAt(from);

        validateMove(from, to, movingPiece);

        movingPiece.verifyMove(from, to, this);

        executeMove(from, to, movingPiece);
    }

    private Piece findPieceAt(Position position) {
        Piece piece = board.get(position);
        if (piece == null) {
            throw new EmptyPositionException();
        }
        return piece;
    }

    private void validateMove(Position from, Position to, Piece movingPiece) {
        if (from.equals(to)) {
            throw new SamePositionException();
        }

        if (hasPieceAt(to) && movingPiece.isSameTeam(getPieceAt(to))) {
            throw new InvalidTargetException();
        }
    }

    private void executeMove(Position from, Position to, Piece movingPiece) {
        board.remove(from);
        board.put(to, movingPiece);
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public Piece getPieceAt(Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public double calculateScore(Team team) {
        double score = board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();

        if (team == Team.HAN) {
            score += 1.5;
        }

        return score;
    }

    public Team getWinner() {
        return board.values().stream()
                .filter(this::isKing)
                .map(Piece::getTeam)
                .findFirst()
                .orElseThrow(GameNotOverException::new);
    }

    private boolean isKing(Piece piece) {
        String name = piece.getName();
        return name.equals("楚") || name.equals("漢");
    }

    public boolean isKingCaptured() {
        long kingCount = board.values().stream()
                .filter(this::isKing)
                .count();

        return kingCount < 2;
    }

    public boolean isCheck(Team targetTeam) {
        Position kingPosition = findKingPosition(targetTeam);
        Team attackerTeam = targetTeam.switchTeam();

        return board.entrySet().stream()
                .filter(entry -> entry.getValue().isSameTeam(attackerTeam))
                .anyMatch(entry -> canAttack(entry.getKey(), kingPosition));
    }

    private Position findKingPosition(Team team) {
        return board.entrySet().stream()
                .filter(entry -> isKingOf(entry.getValue(), team))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(KingNotFoundException::new);
    }

    private boolean isKingOf(Piece piece, Team team) {
        return isKing(piece) && piece.isSameTeam(team);
    }

    private boolean canAttack(Position from, Position kingPosition) {
        Piece attacker = board.get(from);
        try {
            attacker.verifyMove(from, kingPosition, this);
            return true;
        } catch (BusinessException e) {
            return false;
        }
    }
}
