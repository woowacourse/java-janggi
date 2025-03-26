package janggi.board;

import static janggi.board.BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BoardFactory {

    private static final int HAN_Y = 1;
    private static final int CHO_Y = 10;

    public Board makeBoard(final BoardOrder choBoardOrder, final BoardOrder hanBoardOrder) {
        final Map<Piece, Position> positions = makePositions();
        final Map<Position, Piece> pieces = makePieces(positions);
        changePieces(choBoardOrder, getYByTeam(Team.CHO), pieces);
        changePieces(hanBoardOrder, getYByTeam(Team.HAN), pieces);
        return new Board(positions, pieces);
    }

    private Map<Piece, Position> makePositions() {
        final Map<Piece, Position> positions = new HashMap<>();
        makeChoPieces(positions);
        makeHanPieces(positions);
        return positions;
    }

    private static void makeChoPieces(final Map<Piece, Position> positions) {
        positions.put(new Chariot(Team.CHO), new Position(CHO_Y, 1));
        positions.put(new Elephant(Team.CHO), new Position(CHO_Y, 2));
        positions.put(new Horse(Team.CHO), new Position(CHO_Y, 3));
        positions.put(new Guard(Team.CHO), new Position(CHO_Y, 4));
        positions.put(new Guard(Team.CHO), new Position(CHO_Y, 6));
        positions.put(new Elephant(Team.CHO), new Position(CHO_Y, 7));
        positions.put(new Horse(Team.CHO), new Position(CHO_Y, 8));
        positions.put(new Chariot(Team.CHO), new Position(CHO_Y, 9));

        positions.put(new King(Team.CHO), new Position(9, 5));
        positions.put(new Cannon(Team.CHO), new Position(8, 2));
        positions.put(new Cannon(Team.CHO), new Position(8, 8));

        positions.put(new Soldier(Team.CHO), new Position(7, 1));
        positions.put(new Soldier(Team.CHO), new Position(7, 3));
        positions.put(new Soldier(Team.CHO), new Position(7, 5));
        positions.put(new Soldier(Team.CHO), new Position(7, 7));
        positions.put(new Soldier(Team.CHO), new Position(7, 9));
    }

    private static void makeHanPieces(final Map<Piece, Position> positions) {
        positions.put(new Chariot(Team.HAN), new Position(HAN_Y, 1));
        positions.put(new Elephant(Team.HAN), new Position(HAN_Y, 2));
        positions.put(new Horse(Team.HAN), new Position(HAN_Y, 3));
        positions.put(new Guard(Team.HAN), new Position(HAN_Y, 4));
        positions.put(new Guard(Team.HAN), new Position(HAN_Y, 6));
        positions.put(new Elephant(Team.HAN), new Position(HAN_Y, 7));
        positions.put(new Horse(Team.HAN), new Position(HAN_Y, 8));
        positions.put(new Chariot(Team.HAN), new Position(HAN_Y, 9));

        positions.put(new King(Team.HAN), new Position(2, 5));
        positions.put(new Cannon(Team.HAN), new Position(3, 2));
        positions.put(new Cannon(Team.HAN), new Position(3, 8));

        positions.put(new Soldier(Team.HAN), new Position(4, 1));
        positions.put(new Soldier(Team.HAN), new Position(4, 3));
        positions.put(new Soldier(Team.HAN), new Position(4, 5));
        positions.put(new Soldier(Team.HAN), new Position(4, 7));
        positions.put(new Soldier(Team.HAN), new Position(4, 9));
    }

    private Map<Position, Piece> makePieces(final Map<Piece, Position> positions) {
        return positions.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }

    private int getYByTeam(final Team team) {
        if (team == Team.CHO) {
            return CHO_Y;
        }
        return HAN_Y;
    }

    private void changePieces(final BoardOrder order, final int y, final Map<Position, Piece> pieces) {
        if (order == HORSE_ELEPHANT_HORSE_ELEPHANT) {
            swapFirstPieces(pieces, y);
            swapSecondPieces(pieces, y);
        }
        if (order == ELEPHANT_HORSE_HORSE_ELEPHANT) {
            swapSecondPieces(pieces, y);
        }
        if (order == HORSE_ELEPHANT_ELEPHANT_HORSE) {
            swapFirstPieces(pieces, y);
        }
    }

    private void swapFirstPieces(final Map<Position, Piece> pieces, final int y) {
        final Position firstPosition = new Position(y, 2);
        final Piece firstPiece = pieces.get(firstPosition);
        final Position secondPosition = new Position(y, 3);
        final Piece secondPiece = pieces.get(secondPosition);
        pieces.put(firstPosition, secondPiece);
        pieces.put(secondPosition, firstPiece);
    }

    private void swapSecondPieces(final Map<Position, Piece> pieces, final int y) {
        final Position firstPosition = new Position(y, 7);
        final Piece firstPiece = pieces.get(firstPosition);
        final Position secondPosition = new Position(y, 8);
        final Piece secondPiece = pieces.get(secondPosition);
        pieces.put(firstPosition, secondPiece);
        pieces.put(secondPosition, firstPiece);
    }
}
