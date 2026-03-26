package janggi.domain;

import janggi.domain.piece.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class BoardInitializer {
    public static Board initializeBoard() {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeHan(board, hanOpeningFormationChoice);
        initializeCho(board, choOpeningFormationChoice);

        return new Board(board);
    }

    private static void initializeHan(Map<Position, Piece> board) {
        board.put(new Position(1, 4), new SoliderPiece(Name.SOLDIER, Team.HAN));
        board.put(new Position(3, 4), new SoliderPiece(Name.SOLDIER, Team.HAN));
        board.put(new Position(5, 4), new SoliderPiece(Name.SOLDIER, Team.HAN));
        board.put(new Position(7, 4), new SoliderPiece(Name.SOLDIER, Team.HAN));
        board.put(new Position(9, 4), new SoliderPiece(Name.SOLDIER, Team.HAN));

        board.put(new Position(2, 3), new CannonPiece(Name.CANNON, Team.HAN));
        board.put(new Position(8, 3), new CannonPiece(Name.CANNON, Team.HAN));

        board.put(new Position(5, 2), new GeneralPiece(Name.GENERAL, Team.HAN));

        board.put(new Position(4, 1), new GuardPiece(Name.GUARD, Team.HAN));
        board.put(new Position(6, 1), new GuardPiece(Name.GUARD, Team.HAN));

        board.put(new Position(3, 1), new ElephantPiece(Name.ELEPHANT, Team.HAN));
        board.put(new Position(7, 1), new ElephantPiece(Name.ELEPHANT, Team.HAN));

        board.put(new Position(2, 1), new HorsePiece(Name.HORSE, Team.HAN));
        board.put(new Position(8, 1), new HorsePiece(Name.HORSE, Team.HAN));

        board.put(new Position(1, 1), new ChariotPiece(Name.CHARIOT, Team.HAN));
        board.put(new Position(9, 1), new ChariotPiece(Name.CHARIOT, Team.HAN));
    }

    private static void initializeCho(Map<Position, Piece> board) {
        board.put(new Position(1, 7), new SoliderPiece(Name.SOLDIER, Team.CHO));
        board.put(new Position(3, 7), new SoliderPiece(Name.SOLDIER, Team.CHO));
        board.put(new Position(5, 7), new SoliderPiece(Name.SOLDIER, Team.CHO));
        board.put(new Position(7, 7), new SoliderPiece(Name.SOLDIER, Team.CHO));
        board.put(new Position(9, 7), new SoliderPiece(Name.SOLDIER, Team.CHO));

        board.put(new Position(2, 8), new CannonPiece(Name.CANNON, Team.CHO));
        board.put(new Position(8, 8), new CannonPiece(Name.CANNON, Team.CHO));

        board.put(new Position(5, 9), new GeneralPiece(Name.GENERAL, Team.CHO));

        board.put(new Position(4, 10), new GuardPiece(Name.GUARD, Team.CHO));
        board.put(new Position(6, 10), new GuardPiece(Name.GUARD, Team.CHO));

        board.put(new Position(3, 10), new ElephantPiece(Name.ELEPHANT, Team.CHO));
        board.put(new Position(7, 10), new ElephantPiece(Name.ELEPHANT, Team.CHO));

        board.put(new Position(2, 10), new HorsePiece(Name.HORSE, Team.CHO));
        board.put(new Position(8, 10), new HorsePiece(Name.HORSE, Team.CHO));

        board.put(new Position(1, 10), new ChariotPiece(Name.CHARIOT, Team.CHO));
        board.put(new Position(9, 10), new ChariotPiece(Name.CHARIOT, Team.CHO));
    }
}
