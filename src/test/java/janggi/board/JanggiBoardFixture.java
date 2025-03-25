package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Side;

import java.util.Map;

public class JanggiBoardFixture {

//    JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
//
//        Position position = new Position(5, 7);
//        Side side = Side.CHO;

//        Piece piece = new Chariot(side); //

//        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
//        newJanggiBoard.put(position, piece);
//        JanggiBoard modifiedJanggiBoard = new JanggiBoard(newJanggiBoard);
//    computeReachable


//    JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
//
//        Position position = new Position(4, 7);
//        Side side = Side.CHO;

//        Piece piece = new Cannon(side); //

//        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
//        newJanggiBoard.put(position, piece);
//        JanggiBoard modifiedBoard = new JanggiBoard(newJanggiBoard);
//    computeReachable

//    JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
//
//        Position position = new Position(5, 7);
//        Piece piece = new Chariot(Side.CHO);
//        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
//        newJanggiBoard.put(position, piece);
//        JanggiBoard modifiedBoard = new JanggiBoard(newJanggiBoard);

//        Position destination = new Position(5, 0);

//    moveOrCatch

//    JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
//
//        Position position = new Position(5, 7);
//        Piece piece = new Chariot(Side.CHO);
//        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
//        newJanggiBoard.put(position, piece);
//        JanggiBoard modifiedBoard = new JanggiBoard(newJanggiBoard);

//        Position destination = new Position(5, 1);

    //moverOrCatch

    public static JanggiBoard setUpTestBoard(final Position position, final Piece piece) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.put(position, piece);
        return new JanggiBoard(newJanggiBoard);
    }
}
