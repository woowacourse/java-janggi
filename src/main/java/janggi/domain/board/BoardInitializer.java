package janggi.domain.board;

import janggi.domain.piece.CannonPiece;
import janggi.domain.piece.ChariotPiece;
import janggi.domain.piece.ElephantPiece;
import janggi.domain.piece.GeneralPiece;
import janggi.domain.piece.GuardPiece;
import janggi.domain.piece.HorsePiece;
import janggi.domain.piece.Name;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierPiece;
import janggi.domain.piece.Team;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {
    private static final int HAN_BASE_ROW = 1;
    private static final int HAN_GENERAL_ROW = 2;
    private static final int HAN_CANNON_ROW = 3;
    private static final int HAN_SOLDIER_ROW = 4;
    private static final int CHO_SOLDIER_ROW = 7;
    private static final int CHO_CANNON_ROW = 8;
    private static final int CHO_GENERAL_ROW = 9;
    private static final int CHO_BASE_ROW = 10;
    private static final int GENERAL_COLUMN = 5;

    private static final List<Integer> SOLDIER_COLUMNS = List.of(1, 3, 5, 7, 9);
    private static final List<Integer> CANNON_COLUMNS = List.of(2, 8);
    private static final List<Integer> GUARD_COLUMNS = List.of(4, 6);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(1, 9);
    private static final List<Integer> HAN_FORMATION_COLUMNS = List.of(2, 3, 7, 8);
    private static final List<Integer> CHO_FORMATION_COLUMNS = List.of(8, 7, 3, 2);

    public static Board initializeBoard(int hanOpeningFormationChoice, int choOpeningFormationChoice) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeHan(board, hanOpeningFormationChoice);
        initializeCho(board, choOpeningFormationChoice);

        return new Board(board);
    }

    private static void initializeHan(Map<Position, Piece> board, int openingFormationChoice) {
        initializeCommonPieces(board, Team.HAN, HAN_BASE_ROW, HAN_GENERAL_ROW, HAN_CANNON_ROW, HAN_SOLDIER_ROW);
        initializeFormation(board, Team.HAN, HAN_BASE_ROW, OpeningFormation.from(openingFormationChoice),
                HAN_FORMATION_COLUMNS);
    }

    private static void initializeCho(Map<Position, Piece> board, int openingFormationChoice) {
        initializeCommonPieces(board, Team.CHO, CHO_BASE_ROW, CHO_GENERAL_ROW, CHO_CANNON_ROW, CHO_SOLDIER_ROW);
        initializeFormation(board, Team.CHO, CHO_BASE_ROW, OpeningFormation.from(openingFormationChoice),
                CHO_FORMATION_COLUMNS);
    }

    private static void initializeCommonPieces(Map<Position, Piece> board, Team team, int baseRow,
                                               int generalRow, int cannonRow, int soldierRow) {
        initializeSoldiers(board, team, soldierRow);
        initializeCannons(board, team, cannonRow);
        initializeGeneral(board, team, generalRow);
        initializeGuards(board, team, baseRow);
        initializeChariots(board, team, baseRow);
    }

    private static void initializeSoldiers(Map<Position, Piece> board, Team team, int y) {
        for (int x : SOLDIER_COLUMNS) {
            board.put(new Position(x, y), new SoldierPiece(team));
        }
    }

    private static void initializeCannons(Map<Position, Piece> board, Team team, int y) {
        for (int x : CANNON_COLUMNS) {
            board.put(new Position(x, y), new CannonPiece(team));
        }
    }

    private static void initializeGeneral(Map<Position, Piece> board, Team team, int y) {
        board.put(new Position(GENERAL_COLUMN, y), new GeneralPiece(team));
    }

    private static void initializeGuards(Map<Position, Piece> board, Team team, int y) {
        for (int x : GUARD_COLUMNS) {
            board.put(new Position(x, y), new GuardPiece(team));
        }
    }

    private static void initializeChariots(Map<Position, Piece> board, Team team, int y) {
        for (int x : CHARIOT_COLUMNS) {
            board.put(new Position(x, y), new ChariotPiece(team));
        }
    }

    private static void initializeFormation(Map<Position, Piece> board, Team team, int y, OpeningFormation formation,
                                            List<Integer> columns) {
        List<Name> pieceNames = formation.getFormation();

        for (int i = 0; i < pieceNames.size(); i++) {
            int x = columns.get(i);
            board.put(new Position(x, y), createFormationPiece(pieceNames.get(i), team));
        }
    }

    private static Piece createFormationPiece(Name name, Team team) {
        if (name == Name.ELEPHANT) {
            return new ElephantPiece(team);
        }
        return new HorsePiece(team);
    }
}
