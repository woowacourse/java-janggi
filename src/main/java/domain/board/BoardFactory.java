package domain.board;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.country.CountryType;
import domain.Position;
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

public class BoardFactory {
    public Board create(TableSetting choTableSetting, TableSetting hanTableSetting) {
        return new Board(new BoardStates(initialize(choTableSetting, hanTableSetting)));
    }

    private Map<Position, State> initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Map<Position, State> boardStates = new LinkedHashMap<>();
        initializeEmptyState(boardStates);
        initializeSoldierPosition(boardStates);
        initializeGuardPosition(boardStates);
        initializeCannonPosition(boardStates);
        initializeChariotPosition(boardStates);
        initializeGeneralPosition(boardStates);
        initializeChoTableSetting(boardStates, choTableSetting);
        initializeHanTableSetting(boardStates, hanTableSetting);
        return boardStates;
    }

    private void initializeEmptyState(Map<Position, State> boardStates) {
        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
                Position position = new Position(x, y);
                boardStates.put(position, new EmptyState());
            }
        }
    }

    private void initializeSoldierPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(0, 3), new FullState(new Soldier(CountryType.CHO)));
        boardStates.put(new Position(2, 3), new FullState(new Soldier(CountryType.CHO)));
        boardStates.put(new Position(4, 3), new FullState(new Soldier(CountryType.CHO)));
        boardStates.put(new Position(6, 3), new FullState(new Soldier(CountryType.CHO)));
        boardStates.put(new Position(8, 3), new FullState(new Soldier(CountryType.CHO)));

        boardStates.put(new Position(0, 6), new FullState(new Soldier(CountryType.HAN)));
        boardStates.put(new Position(2, 6), new FullState(new Soldier(CountryType.HAN)));
        boardStates.put(new Position(4, 6), new FullState(new Soldier(CountryType.HAN)));
        boardStates.put(new Position(6, 6), new FullState(new Soldier(CountryType.HAN)));
        boardStates.put(new Position(8, 6), new FullState(new Soldier(CountryType.HAN)));
    }

    private void initializeGuardPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(3, 0), new FullState(new Guard(CountryType.CHO)));
        boardStates.put(new Position(5, 0), new FullState(new Guard(CountryType.CHO)));

        boardStates.put(new Position(3, 9), new FullState(new Guard(CountryType.HAN)));
        boardStates.put(new Position(5, 9), new FullState(new Guard(CountryType.HAN)));
    }

    private void initializeCannonPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(1, 2), new FullState(new Cannon(CountryType.CHO)));
        boardStates.put(new Position(7, 2), new FullState(new Cannon(CountryType.CHO)));

        boardStates.put(new Position(1, 7), new FullState(new Cannon(CountryType.HAN)));
        boardStates.put(new Position(7, 7), new FullState(new Cannon(CountryType.HAN)));
    }

    private void initializeChariotPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(0, 0), new FullState(new Chariot(CountryType.CHO)));
        boardStates.put(new Position(8, 0), new FullState(new Chariot(CountryType.CHO)));

        boardStates.put(new Position(0, 9), new FullState(new Chariot(CountryType.HAN)));
        boardStates.put(new Position(8, 9), new FullState(new Chariot(CountryType.HAN)));
    }

    private void initializeGeneralPosition(Map<Position, State> boardStates) {
        boardStates.put(new Position(4, 1), new FullState(new General(CountryType.CHO)));

        boardStates.put(new Position(4, 8), new FullState(new General(CountryType.HAN)));
    }

    private void initializeChoTableSetting(Map<Position, State> boardStates, TableSetting choTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(choTableSetting, CountryType.CHO);

        boardStates.put(new Position(1, 0), new FullState(pieces.getFirst()));
        boardStates.put(new Position(2, 0), new FullState(pieces.get(1)));
        boardStates.put(new Position(6, 0), new FullState(pieces.get(2)));
        boardStates.put(new Position(7, 0), new FullState(pieces.getLast()));
    }

    private void initializeHanTableSetting(Map<Position, State> boardStates, TableSetting hanTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(hanTableSetting, CountryType.HAN);

        boardStates.put(new Position(1, 9), new FullState(pieces.getFirst()));
        boardStates.put(new Position(2, 9), new FullState(pieces.get(1)));
        boardStates.put(new Position(6, 9), new FullState(pieces.get(2)));
        boardStates.put(new Position(7, 9), new FullState(pieces.getLast()));
    }

    private List<Piece> makeTableSettingPieces(TableSetting tableSetting, CountryType countryType) {
        List<Piece> tableSettingPieces = new ArrayList<>();
        for (PieceType pieceType : tableSetting.getFormation(countryType)) {
            tableSettingPieces.add(makeElephantOrHorse(pieceType, countryType));
        }
        return tableSettingPieces;
    }

    private Piece makeElephantOrHorse(PieceType pieceType, CountryType countryType) {
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(countryType);
        }
        return new Horse(countryType);
    }
}
