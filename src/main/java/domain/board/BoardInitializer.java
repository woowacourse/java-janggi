package domain.board;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Country;
import domain.Position;
import domain.TableSetting;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {
    public static Map<Position, State> initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Map<Position, State> boardStates = new LinkedHashMap<>();
        initializeSoldierPosition(boardStates);
        initializeGuardPosition(boardStates);
        initializeCannonPosition(boardStates);
        initializeChariotPosition(boardStates);
        initializeGeneralPosition(boardStates);
        initializeChoTableSetting(boardStates, choTableSetting);
        initializeHanTableSetting(boardStates, hanTableSetting);
        initializeEmptyState(boardStates);
        return boardStates;
    }

    private static void initializeSoldierPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(0, 3), new FullState(new Soldier(Country.CHO)));
        boardStates.put(new Position(2, 3), new FullState(new Soldier(Country.CHO)));
        boardStates.put(new Position(4, 3), new FullState(new Soldier(Country.CHO)));
        boardStates.put(new Position(6, 3), new FullState(new Soldier(Country.CHO)));
        boardStates.put(new Position(8, 3), new FullState(new Soldier(Country.CHO)));

        boardStates.put(new Position(0, 6), new FullState(new Soldier(Country.HAN)));
        boardStates.put(new Position(2, 6), new FullState(new Soldier(Country.HAN)));
        boardStates.put(new Position(4, 6), new FullState(new Soldier(Country.HAN)));
        boardStates.put(new Position(6, 6), new FullState(new Soldier(Country.HAN)));
        boardStates.put(new Position(8, 6), new FullState(new Soldier(Country.HAN)));
    }

    private static void initializeGuardPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(3, 0), new FullState(new Guard(Country.CHO)));
        boardStates.put(new Position(5, 0), new FullState(new Guard(Country.CHO)));

        boardStates.put(new Position(3, 9), new FullState(new Guard(Country.HAN)));
        boardStates.put(new Position(5, 9), new FullState(new Guard(Country.HAN)));
    }

    private static void initializeCannonPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(1, 2), new FullState(new Cannon(Country.CHO)));
        boardStates.put(new Position(7, 2), new FullState(new Cannon(Country.CHO)));

        boardStates.put(new Position(1, 7), new FullState(new Cannon(Country.HAN)));
        boardStates.put(new Position(7, 7), new FullState(new Cannon(Country.HAN)));
    }

    private static void initializeChariotPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(0, 0), new FullState(new Chariot(Country.CHO)));
        boardStates.put(new Position(8, 0), new FullState(new Chariot(Country.CHO)));

        boardStates.put(new Position(0, 9), new FullState(new Chariot(Country.HAN)));
        boardStates.put(new Position(8, 9), new FullState(new Chariot(Country.HAN)));
    }

    private static void initializeGeneralPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(4, 1), new FullState(new General(Country.CHO)));

        boardStates.put(new Position(4, 8), new FullState(new General(Country.HAN)));
    }

    private static void initializeChoTableSetting(Map<Position, State> boardStates, TableSetting choTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(choTableSetting, Country.CHO);

        boardStates.put(new Position(1, 0), new FullState(pieces.getFirst()));
        boardStates.put(new Position(2, 0), new FullState(pieces.get(1)));
        boardStates.put(new Position(6, 0), new FullState(pieces.get(2)));
        boardStates.put(new Position(7, 0), new FullState(pieces.getLast()));
    }

    private static void initializeHanTableSetting(Map<Position, State> boardStates, TableSetting hanTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(hanTableSetting, Country.HAN);

        boardStates.put(new Position(1, 9), new FullState(pieces.getFirst()));
        boardStates.put(new Position(2, 9), new FullState(pieces.get(1)));
        boardStates.put(new Position(6, 9), new FullState(pieces.get(2)));
        boardStates.put(new Position(7, 9), new FullState(pieces.getLast()));
    }

    private static List<Piece> makeTableSettingPieces(TableSetting tableSetting, Country country) {
        List<Piece> tableSettingPieces = new ArrayList<>();
        for (PieceType pieceType : tableSetting.getFormation(country)) {
            tableSettingPieces.add(makeElephantOrHorse(pieceType, country));
        }
        return tableSettingPieces;
    }

    private static Piece makeElephantOrHorse(PieceType pieceType, Country country) {
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(country);
        }
        return new Horse(country);
    }

    private static void initializeEmptyState(Map<Position, State> boardStates) {
        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            initializeRowEmptyState(boardStates, y);
        }
    }

    private static void initializeRowEmptyState(Map<Position, State> boardStates, int y) {
        for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
            Position position = new Position(x, y);
            addEmptyStateToBlankPosition(boardStates, position);
        }
    }

    private static void addEmptyStateToBlankPosition(Map<Position, State> boardStates, Position position) {
        if (!boardStates.containsKey(position)) {
            boardStates.put(position, new EmptyState());
        }
    }
}
