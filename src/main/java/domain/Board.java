package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private static final String SHOULD_CHOOSE_CORRECT_TEAM_PIECE = "자신의 아군 기물만 이동할 수 있습니다.";
    private static final String EMPTY_POSITION = "해당 위치에 기물이 존재하지 않습니다.";
    public static final int NORAML_JANG_AMOUNT = 2;
    public static final double HAN_ADVANTAGE_SCORE = 1.5;
    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board of(SettingType choSettingType, SettingType hanSettingType) {
        Map<Position, Piece> setup = new BoardInitializer().setup(choSettingType, hanSettingType);
        return new Board(setup);
    }

    public static Board of(Map<Position, Piece> pieces) {
        return new Board(new HashMap<>(pieces));
    }

    public void move(Team turn, Position start, Position destination) {
        validateIsAlly(turn, start);
        validateCanMove(start, destination);
        validateCrashWithAlly(start, destination);

        Piece startPiece = getPieceOrThrowException(start);
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private void validateIsAlly(Team turn, Position startPosition) {
        Piece startPiece = getPieceOrThrowException(startPosition);
        if (!startPiece.isSameTeam(turn)) {
            throw new IllegalArgumentException(SHOULD_CHOOSE_CORRECT_TEAM_PIECE);
        }
    }

    private void validateCanMove(Position start, Position destination) {
        Piece startPiece = getPieceOrThrowException(start);

        List<Position> movablePath = startPiece.findMovablePath(start, destination);
        startPiece.movePolicy(this, movablePath, start, destination);
    }

    private void validateCrashWithAlly(Position start, Position destination) {
        Piece startPiece = getPieceOrThrowException(start);
        Piece destinationPiece = getPieceWithNull(destination);

        if (destinationPiece == null) {
            return;
        }
        startPiece.capture(destinationPiece);
    }

    public Piece getPieceOrThrowException(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException(EMPTY_POSITION);
        }
        return piece;
    }

    public Piece getPieceWithNull(Position position) {
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public Team judgeResult() {
        if (!isAnyJangDead()) {
            return judgeByScore();
        }
        return findAnyJang();
    }

    public boolean isAnyJangDead() {
        return getJangAmount() != NORAML_JANG_AMOUNT;
    }

    private long getJangAmount() {
        return pieces.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.JANG)
                .count();
    }

    private Team judgeByScore() {
        double hanScore = getScoreByTeam(Team.HAN);
        double choScore = getScoreByTeam(Team.CHO);
        if (hanScore > choScore) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public double getScoreByTeam(Team team) {
        double sum = getScore(team);
        if (team.isHan()) {
            sum += HAN_ADVANTAGE_SCORE;
        }
        return sum;
    }

    private Team findAnyJang() {
        return pieces.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.JANG)
                .findAny()
                .get().getTeam();
    }

    private double getScore(Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(piece -> piece.getScore())
                .sum();
    }

    public boolean isBikjang() {
        Position jangOfCho = findJangByTeam(Team.CHO);
        Position jangOfHan = findJangByTeam(Team.HAN);

        if (!jangOfCho.isSameColumn(jangOfHan)) {
            return false;
        }

        List<Position> path = jangOfCho.getVerticalPathExcludeDestination(jangOfHan);
        if (hasAnyPieceInPath(path)) {
            return false;
        }
        return true;
    }

    private Position findJangByTeam(Team team) {
        return pieces.keySet().stream()
                .filter(position -> pieces.get(position).getPieceType() == PieceType.JANG)
                .filter(position -> pieces.get(position).isSameTeam(team))
                .findAny().get();
    }

    public boolean hasAnyPieceInPath(List<Position> path) {
        for (Position position : path) {
            if (pieces.get(position) != null) {
                return true;
            }
        }
        return false;
    }
}
