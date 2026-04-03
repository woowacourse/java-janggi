package domain.board;

import domain.Position;
import domain.country.CountryType;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    public Board create(TableSetting choTableSetting, TableSetting hanTableSetting) {
        return new Board(new BoardStates(initialize(choTableSetting, hanTableSetting)));
    }

    private Map<Position, Piece> initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Map<Position, Piece> boardStates = new HashMap<>();
        initializeSoldierPosition(boardStates);
        initializeGuardPosition(boardStates);
        initializeCannonPosition(boardStates);
        initializeChariotPosition(boardStates);
        initializeGeneralPosition(boardStates);
        initializeChoTableSetting(boardStates, choTableSetting);
        initializeHanTableSetting(boardStates, hanTableSetting);
        return boardStates;
    }

    private void initializeSoldierPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(0, 3), new Soldier(CountryType.CHO));
        boardStates.put(new Position(2, 3), new Soldier(CountryType.CHO));
        boardStates.put(new Position(4, 3), new Soldier(CountryType.CHO));
        boardStates.put(new Position(6, 3), new Soldier(CountryType.CHO));
        boardStates.put(new Position(8, 3), new Soldier(CountryType.CHO));

        boardStates.put(new Position(0, 6), new Soldier(CountryType.HAN));
        boardStates.put(new Position(2, 6), new Soldier(CountryType.HAN));
        boardStates.put(new Position(4, 6), new Soldier(CountryType.HAN));
        boardStates.put(new Position(6, 6), new Soldier(CountryType.HAN));
        boardStates.put(new Position(8, 6), new Soldier(CountryType.HAN));
    }

    private void initializeGuardPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(3, 0), new Guard(CountryType.CHO));
        boardStates.put(new Position(5, 0), new Guard(CountryType.CHO));

        boardStates.put(new Position(3, 9), new Guard(CountryType.HAN));
        boardStates.put(new Position(5, 9), new Guard(CountryType.HAN));
    }

    private void initializeCannonPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(1, 2), new Cannon(CountryType.CHO));
        boardStates.put(new Position(7, 2), new Cannon(CountryType.CHO));

        boardStates.put(new Position(1, 7), new Cannon(CountryType.HAN));
        boardStates.put(new Position(7, 7), new Cannon(CountryType.HAN));
    }

    private void initializeChariotPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(0, 0), new Chariot(CountryType.CHO));
        boardStates.put(new Position(8, 0), new Chariot(CountryType.CHO));

        boardStates.put(new Position(0, 9), new Chariot(CountryType.HAN));
        boardStates.put(new Position(8, 9), new Chariot(CountryType.HAN));
    }

    private void initializeGeneralPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(4, 1), new General(CountryType.CHO));

        boardStates.put(new Position(4, 8), new General(CountryType.HAN));
    }

    private void initializeChoTableSetting(Map<Position, Piece> boardStates, TableSetting choTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(choTableSetting, CountryType.CHO);

        boardStates.put(new Position(1, 0), pieces.getFirst());
        boardStates.put(new Position(2, 0), pieces.get(1));
        boardStates.put(new Position(6, 0), pieces.get(2));
        boardStates.put(new Position(7, 0), pieces.getLast());
    }

    private void initializeHanTableSetting(Map<Position, Piece> boardStates, TableSetting hanTableSetting) {
        List<Piece> pieces = makeTableSettingPieces(hanTableSetting, CountryType.HAN);

        boardStates.put(new Position(1, 9), pieces.getFirst());
        boardStates.put(new Position(2, 9), pieces.get(1));
        boardStates.put(new Position(6, 9), pieces.get(2));
        boardStates.put(new Position(7, 9), pieces.getLast());
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
