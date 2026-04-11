package domain.board;

import domain.game.Score;
import domain.game.Scores;
import domain.game.Team;
import domain.piece.CannonRule;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(Position src, Position dest, Team team) {
        Piece piece = findPiece(src)
                .orElseThrow(() -> new IllegalArgumentException("이동할 기물이 없는 위치입니다."));
        validateMove(src, dest, team, piece);
        Optional<Piece> destPiece = findPiece(dest);
        if (destPiece.isPresent()) {
            piece.validateDestination(destPiece.get());
        }
        applyMove(src, dest, piece);
    }

    private void validateMove(Position src, Position dest, Team team, Piece piece) {
        piece.validateSameTurnAndPiece(team);
        piece.validateCanMove(src, dest);
        List<Position> route = piece.searchRoute(src, dest);
        validateRoute(dest, piece, route);
    }

    private void validateRoute(Position dest, Piece piece, List<Position> route) {
        if (piece.isCannon()) {
            validateCannonRoute(route, dest, (CannonRule) piece);
            return;
        }
        validateIntermediateRoute(route);
    }

    private void validateCannonRoute(List<Position> route, Position dest, CannonRule cannonRule) {
        int count = 0;
        for (Position position : route) {
            Optional<Piece> piece = findPiece(position);
            if (piece.isPresent()) {
                cannonRule.validateJumpOver(piece.get());
                count++;
            }
        }
        cannonRule.validateJumpCount(count);
        Optional<Piece> destPiece = findPiece(dest);
        if (destPiece.isPresent()) {
            cannonRule.validateCaptureDest(destPiece.get());
        }
    }

    private void validateIntermediateRoute(List<Position> route) {
        for (Position position : route) {
            if (findPiece(position).isPresent()) {
                throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
            }
        }
    }

    private void applyMove(Position src, Position dest, Piece piece) {
        pieces.put(dest, piece);
        pieces.remove(src);
    }

    private Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Map<Position, Piece> getState() {
        return Collections.unmodifiableMap(pieces);
    }

    public Optional<Piece> findPieceAt(int row, int col) {
        return Optional.ofNullable(pieces.get(new Position(row, col)));
    }

    public boolean hasPieceAt(Position position) {
        return pieces.containsKey(position);
    }

    public boolean isGeneralAlive() {
        int generalCount = 0;
        for (Piece piece : pieces.values()) {
            generalCount += piece.getGeneralCount();
        }
        return generalCount == 2;
    }

    public Scores calculateScore() {
        Map<Team, Score> scores = new EnumMap<>(Team.class);
        for (Team team : Team.values()) {
            scores.put(team, team.getInitialScore());
        }
        for (Piece piece : pieces.values()) {
            scores.merge(piece.getTeam(), piece.getScore(), Score::add);
        }
        return new Scores(scores);
    }

    public boolean decideWinner() {
        for (Piece piece : pieces.values()) {
            if (piece.getGeneralCount() == 1) {
                return piece.isChoTeam();
            }
        }
        throw new IllegalStateException("장군이 없습니다.");
    }
}
