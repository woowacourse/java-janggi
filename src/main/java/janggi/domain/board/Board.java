package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Score;
import janggi.domain.piece.Team;

import java.util.*;

public class Board {
    private final Map<Position, Piece> board;
    private Turn turn = new Turn(Team.HAN);

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(Position from, Position to) {
        Piece piece = findCurrentTeamPiece(from);

        if (!piece.canMove(from, to)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        List<Piece> pathPieces = getPathPieces(from, to, piece);
        if (!piece.checkPathRule(pathPieces)) {
            throw new IllegalArgumentException("이동 경로에 장애물이 있습니다.");
        }
        if (!piece.canCapture(piece, board.get(to))) {
            throw new IllegalArgumentException("목적지로 이동하거나 기물을 잡을 수 없습니다.");
        }

        changePiecePosition(piece, from, to);
        changeTurn();
    }

    public Score calculateScore(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(Piece::getScore)
                .reduce(new Score(0), Score::add);
    }

    private List<Piece> getPathPieces(Position from, Position to, Piece piece) {
        List<Piece> pathPieces = new ArrayList<>();
        List<Position> path = piece.findPath(from, to);
        for (Position position : path) {
            if (board.containsKey(position)) {
                pathPieces.add(board.get(position));
            }
        }
        return pathPieces;
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Turn getTurn() {
        return turn;
    }

    private Piece findCurrentTeamPiece(Position from) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 존재하지 않습니다.");
        }
        Piece piece = board.get(from);
        if (!turn.isCurrentTeam(piece.getTeam())) {
            throw new IllegalArgumentException("현재 턴의 기물이 아닙니다.");
        }
        return piece;
    }

    private Map<Position, Piece> getPositionPiecesFromPath(Piece piece, Position from, Position to) {
        List<Position> paths = piece.findPath(from, to);
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        for (Position position : paths) {
            if (board.containsKey(position)) {
                positionPieces.put(position, board.get(position));
            }
        }

        return positionPieces;
    }

    private void changePiecePosition(Piece piece, Position from, Position to) {
        board.put(to, piece);
        board.remove(from);
    }

    private void changeTurn() {
        this.turn = turn.changeTurn();
    }
}
