package domain;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

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

    public static Map<Position, State> initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Map<Position, State> board = new LinkedHashMap<>();
        initializeSettings(board, choTableSetting, hanTableSetting);
        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            EmptyState emptyState = new EmptyState();
            initializeRow(board, y, emptyState);
        }
        return board;
    }

    private static void initializeSettings(Map<Position, State> board, TableSetting choTableSetting,
                                           TableSetting hanTableSetting) {
        initializeSoldierPosition(board);
        initializeGuardPosition(board);
        initializeCannonPosition(board);
        initializeChariotPosition(board);
        initializeGeneralPosition(board);
        initializeTableSettings(board, choTableSetting, hanTableSetting);
    }

    private static void initializeRow(Map<Position, State> board, int y, EmptyState emptyState) {
        for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
            Position position = new Position(x, y);
            if (!board.containsKey(position)) {
                board.put(position, emptyState);
            }
        }
    }

    private static void initializeSoldierPosition(Map<Position, State> board) {
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            board.put(choPosition, new FullState(new Soldier(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            board.put(hanPosition, new FullState(new Soldier(Country.HAN)));
        }
    }

    private static void initializeGuardPosition(Map<Position, State> board) {
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            board.put(choPosition, new FullState(new Guard(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            board.put(hanPosition, new FullState(new Guard(Country.HAN)));
        }
    }

    private static void initializeCannonPosition(Map<Position, State> board) {
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            board.put(choPosition, new FullState(new Cannon(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            board.put(hanPosition, new FullState(new Cannon(Country.HAN)));
        }
    }

    private static void initializeChariotPosition(Map<Position, State> board) {
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            board.put(choPosition, new FullState(new Chariot(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            board.put(hanPosition, new FullState(new Chariot(Country.HAN)));
        }
    }

    private static void initializeGeneralPosition(Map<Position, State> board) {
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            board.put(choPosition, new FullState(new General(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            board.put(hanPosition, new FullState(new General(Country.HAN)));
        }
    }

    private static void initializeTableSettings(Map<Position, State> board, TableSetting choTableSetting,
                                                TableSetting hanTableSetting) {
        initializeChoTableSetting(board, choTableSetting);
        initializeHanTableSetting(board, hanTableSetting);
    }

    private static void initializeChoTableSetting(Map<Position, State> board, TableSetting choTableSetting) {
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

    private static void initializeHanTableSetting(Map<Position, State> board, TableSetting hanTableSetting) {
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
