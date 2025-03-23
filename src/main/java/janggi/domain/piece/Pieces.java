package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Pieces {

    private final int BACK_ROW = 1;
    private final int GENERAL_ROW = 2;
    private final int CANNON_ROW = 3;
    private final int SOLDIER_ROW = 4;

    private final List<Piece> pieces;

    private Pieces() {
        this.pieces = new ArrayList<>();
    }

    public static Pieces createPieces(SetupType redSetupType, SetupType greenSetupType) {
        Pieces pieces = new Pieces();
        pieces.createCommonPieces();
        pieces.createSetupTypePieces(Team.RED, redSetupType);
        pieces.createSetupTypePieces(Team.GREEN, greenSetupType);
        return pieces;
    }

    private void createCommonPieces() {
        for (Team team : Team.values()) {
            pieces.add(new General(Position.of(team.decideRow(GENERAL_ROW), 5), team));
            pieces.addAll(Stream.of(4, 6)
                    .map(column -> new Guard(Position.of(team.decideRow(BACK_ROW), column), team))
                    .toList());
            pieces.addAll(Stream.of(1, 3, 5, 7, 9)
                    .map(defaultColumn -> new Soldier(Position.of(team.decideRow(SOLDIER_ROW), defaultColumn), team))
                    .toList());
            pieces.addAll(Stream.of(1, 9)
                    .map(defaultColumn -> new Chariot(Position.of(team.decideRow(BACK_ROW), defaultColumn), team))
                    .toList());
            pieces.addAll(Stream.of(2, 8)
                    .map(column -> new Cannon(Position.of(team.decideRow(CANNON_ROW), column), team))
                    .toList());
        }
    }

    private void createSetupTypePieces(final Team team, final SetupType setupType) {
        pieces.addAll(createElephantPieces(team, setupType));
        pieces.addAll(createHorsePieces(team, setupType));
    }

    private List<Elephant> createElephantPieces(Team team, final SetupType setupType) {
        return setupType.getElephantColumnNumbers(team).stream()
                .map(defaultColumn -> new Elephant(Position.of(team.decideRow(BACK_ROW), defaultColumn), team))
                .toList();
    }

    private List<Horse> createHorsePieces(Team team, final SetupType setupType) {
        return setupType.getHorseColumnNumbers(team).stream()
                .map(defaultColumn -> new Horse(Position.of(team.decideRow(BACK_ROW), defaultColumn), team))
                .toList();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
