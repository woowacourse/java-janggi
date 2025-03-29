package domain.board;

import domain.piece.Piece;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Path;
import domain.point.Point;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board implements PieceVisibleBoard {

    private final Map<Point, Piece> pieceByPoint;
    private final PathFinder pathFinder;

    public Board(final Map<Point, Piece> pieceByPoint, final PathFinder pathFinder) {
        this.pieceByPoint = pieceByPoint;
        this.pathFinder = pathFinder;
    }

    public boolean isPlaying() {
        return isWangsOfAllTeamAlive();
    }

    public boolean canMove(final Point source, final Point destination) {
        if (!existsPiece(source)) {
            return false;
        }
        Piece piece = getPiece(source);
        return piece.canMove(source, destination, this);
    }

    public void movePiece(final Point source, final Point destination) {
        Piece sourcePiece = getPiece(source);
        if (!sourcePiece.canMove(source, destination, this)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }

        pieceByPoint.put(destination, sourcePiece);
        removePiece(source);
    }

    public boolean existsPiece(final Point point) {
        return existsPoint(point)
                && pieceByPoint.containsKey(point);
    }

    public boolean existsPo(final Point point) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPiece(point);
        return piece.type() == PieceType.PO;
    }

    public void removePiece(final Point point) {
        if (!existsPiece(point)) {
            return;
        }
        Piece piece = getPiece(point);
        pieceByPoint.remove(point, piece);
    }

    public boolean matchTeam(final Point point, final Team team) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPiece(point);
        return piece.team() == team;
    }

    public boolean existsNextPoint(final Point point, final Direction direction) {
        if (!existsPoint(point)) {
            return false;
        }
        return pathFinder.hasNextPoint(point, direction);
    }

    public Point getNextPoint(final Point point, final Direction direction) {
        validatePoint(point);
        return pathFinder.getNextPoint(point, direction);
    }

    public boolean canMoveByPath(final Point point, final Path path) {
        if (!existsPoint(point)) {
            return false;
        }
        return pathFinder.canMoveByPath(point, path);
    }

    public Point getPointMovedByPath(final Point point, final Path path) {
        validatePoint(point);
        return pathFinder.getPointMovedByPath(point, path);
    }

    public double calculateScore(Team team) {
        double sum = 0;
        final double HAN_HANDICAP_SCORE = 1.5;
        if (team == Team.HAN) {
            sum += HAN_HANDICAP_SCORE;
        }
        return sum + pieceByPoint.values().stream()
                .filter(piece -> piece.team() == team)
                .mapToInt(Piece::score)
                .sum();
    }

    public Team findWinTeam() {
        if (isPlaying()) {
            return determineWinTeamByScore();
        }
        Set<Team> foundTeam = findTeamsOfWang();
        if (foundTeam.contains(Team.CHO)) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    private boolean existsPoint(final Point point) {
        return pathFinder.existsPoint(point);
    }

    private void validatePoint(final Point point) {
        if (!existsPoint(point)) {
            throw new IllegalArgumentException(point.row() + ", " + point.column() + ": 존재하지 않는 좌표입니다.");
        }
    }

    private boolean isWangsOfAllTeamAlive() {
        return findTeamsOfWang().containsAll(List.of(Team.CHO, Team.HAN));
    }

    private Set<Team> findTeamsOfWang() {
        return pieceByPoint.values().stream()
                .filter(piece -> piece.type() == PieceType.WANG)
                .map(Piece::team)
                .collect(Collectors.toSet());
    }

    private Team determineWinTeamByScore() {
        double hanScore = calculateScore(Team.HAN);
        double choScore = calculateScore(Team.CHO);
        if (hanScore > choScore) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    private Piece getPiece(final Point point) {
        if (!existsPiece(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieceByPoint.get(point);
    }

    public Map<Point, Piece> getPieceByPoint() {
        return pieceByPoint;
    }
}
