package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.piece.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public MoveResult move(Position from, Position to, Team team) {
        Piece piece = findCurrentTeamPiece(from, team);

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

        return changePiecePosition(piece, from, to);
    }

    public Score calculateScore(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(Piece::getScore)
                .reduce(new Score(0), Score::add);
    }

    public boolean isGeneralCaptured(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .noneMatch(piece -> piece.isSameType(PieceType.GENERAL));
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

    private Piece findCurrentTeamPiece(Position from, Team team) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 존재하지 않습니다.");
        }
        Piece piece = board.get(from);
        if (!piece.isSameTeam(team)) {
            throw new IllegalArgumentException("현재 턴의 기물이 아닙니다.");
        }
        return piece;
    }

    private MoveResult changePiecePosition(Piece piece, Position from, Position to) {
        Piece capturedPiece = board.get(to);
        board.put(to, piece);
        board.remove(from);
        return new MoveResult(from, to, capturedPiece != null);
    }
}
