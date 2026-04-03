package janggi.domain.board;

import janggi.domain.movestrategy.*;
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
            board.put(new Position(2, 1), new Piece(Team.HAN, new ElephantStrategy()));
            board.put(new Position(7, 1), new Piece(Team.HAN, new ElephantStrategy()));

            board.put(new Position(3, 1), new Piece(Team.HAN, new HorseStrategy()));
            board.put(new Position(8, 1), new Piece(Team.HAN, new HorseStrategy()));
        }

        if (choice == 2) {
            board.put(new Position(3, 1), new Piece(Team.HAN, new ElephantStrategy()));
            board.put(new Position(8, 1), new Piece(Team.HAN, new ElephantStrategy()));

            board.put(new Position(2, 1), new Piece(Team.HAN, new HorseStrategy()));
            board.put(new Position(7, 1), new Piece(Team.HAN, new HorseStrategy()));
        }
        if (choice == 3) {
            board.put(new Position(3, 1), new Piece(Team.HAN, new ElephantStrategy()));
            board.put(new Position(7, 1), new Piece(Team.HAN, new ElephantStrategy()));

            board.put(new Position(2, 1), new Piece(Team.HAN, new HorseStrategy()));
            board.put(new Position(8, 1), new Piece(Team.HAN, new HorseStrategy()));
        }

        if (choice == 4) {
            board.put(new Position(2, 1), new Piece(Team.HAN, new ElephantStrategy()));
            board.put(new Position(8, 1), new Piece(Team.HAN, new ElephantStrategy()));

            board.put(new Position(3, 1), new Piece(Team.HAN, new HorseStrategy()));
            board.put(new Position(7, 1), new Piece(Team.HAN, new HorseStrategy()));
        }
    }

    private static void initializeChoAttire(Map<Position, Piece> board, int choice) {
        if (choice == 1) { //상마상마
            board.put(new Position(3, 10), new Piece(Team.CHO, new ElephantStrategy()));
            board.put(new Position(8, 10), new Piece(Team.CHO, new ElephantStrategy()));

            board.put(new Position(2, 10), new Piece(Team.CHO, new HorseStrategy()));
            board.put(new Position(7, 10), new Piece(Team.CHO, new HorseStrategy()));
        }

        if (choice == 2) { // 마상마상
            board.put(new Position(2, 10), new Piece(Team.CHO, new ElephantStrategy()));
            board.put(new Position(7, 10), new Piece(Team.CHO, new ElephantStrategy()));

            board.put(new Position(3, 10), new Piece(Team.CHO, new HorseStrategy()));
            board.put(new Position(8, 10), new Piece(Team.CHO, new HorseStrategy()));
        }

        if (choice == 3) { // 마상상마
            board.put(new Position(3, 10), new Piece(Team.CHO, new ElephantStrategy()));
            board.put(new Position(7, 10), new Piece(Team.CHO, new ElephantStrategy()));

            board.put(new Position(2, 10), new Piece(Team.CHO, new HorseStrategy()));
            board.put(new Position(8, 10), new Piece(Team.CHO, new HorseStrategy()));
        }

        if (choice == 4) { // 상마마상
            board.put(new Position(2, 10), new Piece(Team.CHO, new ElephantStrategy()));
            board.put(new Position(8, 10), new Piece(Team.CHO, new ElephantStrategy()));

            board.put(new Position(3, 10), new Piece(Team.CHO, new HorseStrategy()));
            board.put(new Position(7, 10), new Piece(Team.CHO, new HorseStrategy()));
        }
    }


    private static void initializeHan(Map<Position, Piece> board, int openingFormationChoice) {
        board.put(new Position(1, 4), new Piece(Team.HAN, new SoliderStrategy(BoardDirection.UP)));
        board.put(new Position(3, 4), new Piece(Team.HAN, new SoliderStrategy(BoardDirection.UP)));
        board.put(new Position(5, 4), new Piece(Team.HAN, new SoliderStrategy(BoardDirection.UP)));
        board.put(new Position(7, 4), new Piece(Team.HAN, new SoliderStrategy(BoardDirection.UP)));
        board.put(new Position(9, 4), new Piece(Team.HAN, new SoliderStrategy(BoardDirection.UP)));

        board.put(new Position(2, 3), new Piece(Team.HAN, new CannonStrategy()));
        board.put(new Position(8, 3), new Piece(Team.HAN, new CannonStrategy()));

        board.put(new Position(5, 2), new Piece(Team.HAN, new GeneralStrategy()));

        board.put(new Position(4, 1), new Piece(Team.HAN, new GuardStrategy()));
        board.put(new Position(6, 1), new Piece(Team.HAN, new GuardStrategy()));

        board.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        board.put(new Position(9, 1), new Piece(Team.HAN, new ChariotStrategy()));

        initializeHanAttire(board, openingFormationChoice);
    }

    private static void initializeCho(Map<Position, Piece> board, int openingFormationChoice) {
        board.put(new Position(1, 7), new Piece(Team.CHO, new SoliderStrategy(BoardDirection.DOWN)));
        board.put(new Position(3, 7), new Piece(Team.CHO, new SoliderStrategy(BoardDirection.DOWN)));
        board.put(new Position(5, 7), new Piece(Team.CHO, new SoliderStrategy(BoardDirection.DOWN)));
        board.put(new Position(7, 7), new Piece(Team.CHO, new SoliderStrategy(BoardDirection.DOWN)));
        board.put(new Position(9, 7), new Piece(Team.CHO, new SoliderStrategy(BoardDirection.DOWN)));

        board.put(new Position(2, 8), new Piece(Team.CHO, new CannonStrategy()));
        board.put(new Position(8, 8), new Piece(Team.CHO, new CannonStrategy()));

        board.put(new Position(5, 9), new Piece(Team.CHO, new GeneralStrategy()));

        board.put(new Position(4, 10), new Piece(Team.CHO, new GuardStrategy()));
        board.put(new Position(6, 10), new Piece(Team.CHO, new GuardStrategy()));

        board.put(new Position(1, 10), new Piece(Team.CHO, new ChariotStrategy()));
        board.put(new Position(9, 10), new Piece(Team.CHO, new ChariotStrategy()));

        initializeChoAttire(board, openingFormationChoice);
    }
}
