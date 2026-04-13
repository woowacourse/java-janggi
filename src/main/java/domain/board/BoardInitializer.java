package domain.board;

import static domain.board.Position.INITIAL_POSITION;
import static domain.board.Position.X_MAXIMUM_POSITION;
import static domain.board.Position.Y_MAXIMUM_POSITION;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoardInitializer {
    private static final int TABLE_SETTING_SIZE = 4;

    private final Map<Position, State> board = new LinkedHashMap<>();

    public BoardInitializer() {
    }

    public Map<Position, State> initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeSettings(choTableSetting, hanTableSetting);
        fillEmptyPositions(board);

        return board;
    }

    public void fillEmptyPositions(Map<Position, State> board) {
        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            initializeRow(y, new EmptyState(), board);
        }
    }

    private void initializeRow(int y, EmptyState emptyState, Map<Position, State> board) {
        for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
            Position position = new Position(x, y);
            if (!board.containsKey(position)) {
                board.put(position, emptyState);
            }
        }
    }

    private void initializeSettings(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeSoldierPosition();
        initializeGuardPosition();
        initializeCannonPosition();
        initializeChariotPosition();
        initializeGeneralPosition();
        initializeTableSettings(choTableSetting, hanTableSetting);
    }

    private void initializeSoldierPosition() {
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            board.put(choPosition, new FullState(new Soldier(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            board.put(hanPosition, new FullState(new Soldier(Country.HAN)));
        }
    }

    private void initializeGuardPosition() {
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            board.put(choPosition, new FullState(new Guard(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            board.put(hanPosition, new FullState(new Guard(Country.HAN)));
        }
    }

    private void initializeCannonPosition() {
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            board.put(choPosition, new FullState(new Cannon(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            board.put(hanPosition, new FullState(new Cannon(Country.HAN)));
        }
    }

    private void initializeChariotPosition() {
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            board.put(choPosition, new FullState(new Chariot(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            board.put(hanPosition, new FullState(new Chariot(Country.HAN)));
        }
    }

    private void initializeGeneralPosition() {
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            board.put(choPosition, new FullState(new General(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            board.put(hanPosition, new FullState(new General(Country.HAN)));
        }
    }

    private void initializeTableSettings(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeChoTableSetting(choTableSetting);
        initializeHanTableSetting(hanTableSetting);
    }

    private void initializeChoTableSetting(TableSetting choTableSetting) {
        for (int index = 0; index < TABLE_SETTING_SIZE; index++) {
            PieceType pieceType = choTableSetting.getFormation(Country.CHO).get(index);
            if (pieceType == PieceType.HORSE) {
                board.put(InitialPosition.ELEPHANT_AND_HORSE.getChoPositions().get(index),
                        new FullState(new Horse(Country.CHO)));
                continue;
            }
            board.put(InitialPosition.ELEPHANT_AND_HORSE.getChoPositions().get(index),
                    new FullState(new Elephant(Country.CHO)));
        }
    }

    private void initializeHanTableSetting(TableSetting hanTableSetting) {
        for (int index = 0; index < TABLE_SETTING_SIZE; index++) {
            PieceType pieceType = hanTableSetting.getFormation(Country.HAN).get(index);
            if (pieceType == PieceType.HORSE) {
                board.put(InitialPosition.ELEPHANT_AND_HORSE.getHanPositions().get(index),
                        new FullState(new Horse(Country.HAN)));
                continue;
            }
            board.put(InitialPosition.ELEPHANT_AND_HORSE.getHanPositions().get(index),
                    new FullState(new Elephant(Country.HAN)));
        }
    }
}
