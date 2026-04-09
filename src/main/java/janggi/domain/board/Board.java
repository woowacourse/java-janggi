package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.Score;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> janggiBoard;

    Board(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public void movePiece(Position from, Position to) {
        Piece piece = selectPiece(from);
        Path path = findPath(piece, from, to);
        validateRoute(piece, path);
        validateDestination(piece, to);
        executeMove(piece, from, to);
    }

    public Piece selectPiece(Position position) {
        return Optional.ofNullable(janggiBoard.get(position))
                .orElseThrow(() -> new IllegalArgumentException("보드에 기물이 존재하지 않습니다."));
    }

    private Path findPath(Piece piece, Position from, Position to) {
        Paths movablePaths = piece.findMovablePaths(from);
        return movablePaths.findPathByDestination(to);
    }

    private void validateRoute(Piece piece, Path path) {
        Map<Position, Piece> piecesOnRoute = findPiecesOnRoute(path);
        if (!piece.canPassRoute(piecesOnRoute)) {
            throw new IllegalArgumentException("경로가 막혀있습니다.");
        }
    }

    private Map<Position, Piece> findPiecesOnRoute(Path path) {
        return janggiBoard.entrySet().stream()
                .filter(entry -> path.isOnWayPoints(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void validateDestination(Piece piece, Position to) {
        Optional.ofNullable(janggiBoard.get(to))
                .ifPresent(target -> {
                    if (!piece.canCatch(target)) {
                        throw new IllegalArgumentException("목적지의 기물을 잡을 수 없습니다.");
                    }
                });
    }

    private void executeMove(Piece piece, Position from, Position to) {
        janggiBoard.remove(from);
        janggiBoard.put(to, piece);
    }

    public boolean isAliveEssentialPiece(Camp camp) {
        return janggiBoard.values().stream()
                .filter(piece -> piece.isSameCamp(camp))
                .anyMatch(Piece::isEssential);
    }

    public Board clone() {
        Map<Position, Piece> copiedMap = new HashMap<>(this.janggiBoard);
        return new Board(copiedMap);
    }

    public Map<Position, Piece> janggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

    public Camp calculateScoreResult() {
        Score choScore = janggiBoard.values().stream()
                .filter(piece -> piece.isSameCamp(Camp.CHO))
                .map(Piece::score)
                .reduce(new Score(0), Score::plus);

        Score hanScore = janggiBoard.values().stream()
                .filter(piece -> piece.isSameCamp(Camp.HAN))
                .map(Piece::score)
                .reduce(new Score(0), Score::plus)
                .multiply(1.5);

        if (choScore.isGreaterThan(hanScore)) {
            return Camp.CHO;
        }

        return Camp.HAN;
    }
}
