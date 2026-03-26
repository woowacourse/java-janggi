package janggi.domain.board;

import janggi.domain.piece.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class BoardInitializer {

    public static Board initializeBoard(int hanOpeningFormationChoice, int choOpeningFormationChoice) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeHan(board, hanOpeningFormationChoice);
        initializeCho(board, choOpeningFormationChoice);

        return new Board(board);
    }

    private static void initializeHanAttire(Map<Position, Piece> board, int choice) {
        if (choice == 1) {
            board.put(new Position(2, 1), new ElephantPiece(Team.HAN));
            board.put(new Position(7, 1), new ElephantPiece(Team.HAN));

            board.put(new Position(3, 1), new HorsePiece(Team.HAN));
            board.put(new Position(8, 1), new HorsePiece(Team.HAN));
        }

        if (choice == 2) {
            board.put(new Position(3, 1), new ElephantPiece(Team.HAN));
            board.put(new Position(8, 1), new ElephantPiece(Team.HAN));

            board.put(new Position(2, 1), new HorsePiece(Team.HAN));
            board.put(new Position(7, 1), new HorsePiece(Team.HAN));
        }
        if (choice == 3) {
            board.put(new Position(3, 1), new ElephantPiece(Team.HAN));
            board.put(new Position(7, 1), new ElephantPiece(Team.HAN));

            board.put(new Position(2, 1), new HorsePiece(Team.HAN));
            board.put(new Position(8, 1), new HorsePiece(Team.HAN));
        }

        if (choice == 4) {
            board.put(new Position(2, 1), new ElephantPiece(Team.HAN));
            board.put(new Position(8, 1), new ElephantPiece(Team.HAN));

            board.put(new Position(3, 1), new HorsePiece(Team.HAN));
            board.put(new Position(7, 1), new HorsePiece(Team.HAN));
        }
    }

    private static void initializeChoAttire(Map<Position, Piece> board, int choice) {
        if (choice == 1) { //상마상마
            board.put(new Position(3, 10), new ElephantPiece(Team.CHO));
            board.put(new Position(8, 10), new ElephantPiece(Team.CHO));

            board.put(new Position(2, 10), new HorsePiece(Team.CHO));
            board.put(new Position(7, 10), new HorsePiece(Team.CHO));
        }

        if (choice == 2) { // 마상마상
            board.put(new Position(2, 10), new ElephantPiece(Team.CHO));
            board.put(new Position(7, 10), new ElephantPiece(Team.CHO));

            board.put(new Position(3, 10), new HorsePiece(Team.CHO));
            board.put(new Position(8, 10), new HorsePiece(Team.CHO));
        }

        if (choice == 3) { // 마상상마
            board.put(new Position(3, 10), new ElephantPiece(Team.CHO));
            board.put(new Position(7, 10), new ElephantPiece(Team.CHO));

            board.put(new Position(2, 10), new HorsePiece(Team.CHO));
            board.put(new Position(8, 10), new HorsePiece(Team.CHO));
        }

        if (choice == 4) { // 상마마상
            board.put(new Position(2, 10), new ElephantPiece(Team.CHO));
            board.put(new Position(8, 10), new ElephantPiece(Team.CHO));

            board.put(new Position(3, 10), new HorsePiece(Team.CHO));
            board.put(new Position(7, 10), new HorsePiece(Team.CHO));
        }
    }


    private static void initializeHan(Map<Position, Piece> board, int openingFormationChoice) {
        board.put(new Position(1, 4), new SoliderPiece(Team.HAN));
        board.put(new Position(3, 4), new SoliderPiece(Team.HAN));
        board.put(new Position(5, 4), new SoliderPiece(Team.HAN));
        board.put(new Position(7, 4), new SoliderPiece(Team.HAN));
        board.put(new Position(9, 4), new SoliderPiece(Team.HAN));

        board.put(new Position(2, 3), new CannonPiece(Team.HAN));
        board.put(new Position(8, 3), new CannonPiece(Team.HAN));

        board.put(new Position(5, 2), new GeneralPiece(Team.HAN));

        board.put(new Position(4, 1), new GuardPiece(Team.HAN));
        board.put(new Position(6, 1), new GuardPiece(Team.HAN));

        board.put(new Position(1, 1), new ChariotPiece(Team.HAN));
        board.put(new Position(9, 1), new ChariotPiece(Team.HAN));

        initializeHanAttire(board, openingFormationChoice);
    }

    private static void initializeCho(Map<Position, Piece> board, int openingFormationChoice) {
        board.put(new Position(1, 7), new SoliderPiece(Team.CHO));
        board.put(new Position(3, 7), new SoliderPiece(Team.CHO));
        board.put(new Position(5, 7), new SoliderPiece(Team.CHO));
        board.put(new Position(7, 7), new SoliderPiece(Team.CHO));
        board.put(new Position(9, 7), new SoliderPiece(Team.CHO));

        board.put(new Position(2, 8), new CannonPiece(Team.CHO));
        board.put(new Position(8, 8), new CannonPiece(Team.CHO));

        board.put(new Position(5, 9), new GeneralPiece(Team.CHO));

        board.put(new Position(4, 10), new GuardPiece(Team.CHO));
        board.put(new Position(6, 10), new GuardPiece(Team.CHO));

        board.put(new Position(1, 10), new ChariotPiece(Team.CHO));
        board.put(new Position(9, 10), new ChariotPiece(Team.CHO));

        initializeChoAttire(board, openingFormationChoice);
    }
}
