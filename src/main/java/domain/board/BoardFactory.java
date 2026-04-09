package domain.board;

import domain.Position;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
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
        boardStates.put(new Position(0, 3), PieceFactory.SOLDIER.create(CountryType.CHO));
        boardStates.put(new Position(2, 3), PieceFactory.SOLDIER.create(CountryType.CHO));
        boardStates.put(new Position(4, 3), PieceFactory.SOLDIER.create(CountryType.CHO));
        boardStates.put(new Position(6, 3), PieceFactory.SOLDIER.create(CountryType.CHO));
        boardStates.put(new Position(8, 3), PieceFactory.SOLDIER.create(CountryType.CHO));

        boardStates.put(new Position(0, 6), PieceFactory.SOLDIER.create(CountryType.HAN));
        boardStates.put(new Position(2, 6), PieceFactory.SOLDIER.create(CountryType.HAN));
        boardStates.put(new Position(4, 6), PieceFactory.SOLDIER.create(CountryType.HAN));
        boardStates.put(new Position(6, 6), PieceFactory.SOLDIER.create(CountryType.HAN));
        boardStates.put(new Position(8, 6), PieceFactory.SOLDIER.create(CountryType.HAN));
    }

    private void initializeGuardPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(3, 0), PieceFactory.GUARD.create(CountryType.CHO));
        boardStates.put(new Position(5, 0), PieceFactory.GUARD.create(CountryType.CHO));

        boardStates.put(new Position(3, 9), PieceFactory.GUARD.create(CountryType.HAN));
        boardStates.put(new Position(5, 9), PieceFactory.GUARD.create(CountryType.HAN));
    }

    private void initializeCannonPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(1, 2), PieceFactory.CANNON.create(CountryType.CHO));
        boardStates.put(new Position(7, 2), PieceFactory.CANNON.create(CountryType.CHO));

        boardStates.put(new Position(1, 7), PieceFactory.CANNON.create(CountryType.HAN));
        boardStates.put(new Position(7, 7), PieceFactory.CANNON.create(CountryType.HAN));
    }

    private void initializeChariotPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(0, 0), PieceFactory.CHARIOT.create(CountryType.CHO));
        boardStates.put(new Position(8, 0), PieceFactory.CHARIOT.create(CountryType.CHO));

        boardStates.put(new Position(0, 9), PieceFactory.CHARIOT.create(CountryType.HAN));
        boardStates.put(new Position(8, 9), PieceFactory.CHARIOT.create(CountryType.HAN));
    }

    private void initializeGeneralPosition(Map<Position, Piece> boardStates) {
        boardStates.put(new Position(4, 1), PieceFactory.GENERAL.create(CountryType.CHO));

        boardStates.put(new Position(4, 8), PieceFactory.GENERAL.create(CountryType.HAN));
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
            return PieceFactory.ELEPHANT.create(countryType);
        }
        return PieceFactory.HORSE.create(countryType);
    }
}
