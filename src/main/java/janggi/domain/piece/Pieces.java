package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.SetupType;
import janggi.domain.Team;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pieces {

    private static final int BACK_ROW = 1;
    private static final int GENERAL_ROW = 2;
    private static final int CANNON_ROW = 3;
    private static final int SOLDIER_ROW = 4;

    private static final int GENERAL_COLUMN = 5;
    private static final List<Integer> SOLDIER_COLUMN = List.of(1, 3, 5, 7, 9);
    private static final List<Integer> GUARD_COLUMN = List.of(4, 6);
    private static final List<Integer> CHARIOT_COLUMN = List.of(1, 9);
    private static final List<Integer> CANNON_COLUMN = List.of(2, 8);

    private final Map<Position, Piece> pieces;

    private Pieces() {
        this.pieces = new HashMap<>();
    }

    public static Pieces createPieces(final SetupType redSetupType, final SetupType greenSetupType) {
        Pieces pieces = new Pieces();
        pieces.createCommonPieces();
        pieces.createSetupTypePieces(Team.RED, redSetupType);
        pieces.createSetupTypePieces(Team.GREEN, greenSetupType);
        return pieces;
    }

    private void createCommonPieces() {
        for (Team team : Team.values()) {
            createGeneral(team);
            createGuard(team);
            createSoldier(team);
            createChariot(team);
            createCannon(team);
        }
    }

    private void createGeneral(final Team team) {
        pieces.put(Position.of(team.decideRow(GENERAL_ROW), GENERAL_COLUMN), new General(team));
    }

    private void createGuard(final Team team) {
        pieces.putAll(GUARD_COLUMN.stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(BACK_ROW), column),
                        column -> new Guard(team))));
    }

    private void createSoldier(final Team team) {
        pieces.putAll(SOLDIER_COLUMN.stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(SOLDIER_ROW), column),
                        column -> new Soldier(team))));
    }

    private void createChariot(final Team team) {
        pieces.putAll(CHARIOT_COLUMN.stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(BACK_ROW), column),
                        column -> new Chariot(team))));
    }

    private void createCannon(final Team team) {
        pieces.putAll(CANNON_COLUMN.stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(CANNON_ROW), column),
                        column -> new Cannon(team))));
    }

    private void createSetupTypePieces(final Team team, final SetupType setupType) {
        pieces.putAll(createElephantPieces(team, setupType));
        pieces.putAll(createHorsePieces(team, setupType));
    }

    private Map<Position, Elephant> createElephantPieces(final Team team, final SetupType setupType) {
        return setupType.getElephantColumnNumbers(team).stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(BACK_ROW), column),
                        column -> new Elephant(team)));
    }

    private Map<Position, Horse> createHorsePieces(final Team team, final SetupType setupType) {
        return setupType.getHorseColumnNumbers(team).stream()
                .collect(Collectors.toMap(column -> Position.of(team.decideRow(BACK_ROW), column),
                        column -> new Horse(team)));
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public static Score getInitScore(Team team) {
        double scoreValue = 0;
        if (team.isRed()) {
            scoreValue += 1.5;
        }
        scoreValue += SOLDIER_COLUMN.size() * PieceType.SOLDIER.getScore().value();
        scoreValue += GUARD_COLUMN.size() * PieceType.GUARD.getScore().value();
        scoreValue += CHARIOT_COLUMN.size() * PieceType.CHARIOT.getScore().value();
        scoreValue += CANNON_COLUMN.size() * PieceType.CANNON.getScore().value();
        scoreValue += SetupType.getInitScore();
        return new Score(scoreValue);
    }
}
