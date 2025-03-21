package model;

import java.util.HashMap;
import java.util.Map;

public class PieceInitializer {

    private static final Position RED_POSITION_OF_GENERAL = new Position(1, 4);
    private static final Position GREEN_POSITION_OF_GENERAL = new Position(8, 4);
    private static final Position RED_LEFT_POSITION_OF_GUARD = new Position(0, 3);
    private static final Position RED_RIGHT_POSITION_OF_GUARD = new Position(0, 5);
    private static final Position GREEN_LEFT_POSITION_OF_GUARD = new Position(9, 3);
    private static final Position GREEN_RIGHT_POSITION_OF_GUARD = new Position(9, 5);

    private static final Position RED_LEFT_POSITION_OF_HORSE = new Position(0, 1);
    private static final Position RED_RIGHT_POSITION_OF_HORSE = new Position(0, 7);
    private static final Position GREEN_LEFT_POSITION_OF_HORSE = new Position(9, 1);
    private static final Position GREEN_RIGHT_POSITION_OF_HORSE = new Position(9, 7);

    private static final Position RED_LEFT_POSITION_OF_ELEPHANT = new Position(0, 2);
    private static final Position RED_RIGHT_POSITION_OF_ELEPHANT = new Position(0, 6);
    private static final Position GREEN_LEFT_POSITION_OF_ELEPHANT = new Position(9, 2);
    private static final Position GREEN_RIGHT_POSITION_OF_ELEPHANT = new Position(9, 6);

    private static final Position RED_LEFT_POSITION_OF_CHARIOT = new Position(0, 0);
    private static final Position RED_RIGHT_POSITION_OF_CHARIOT = new Position(0, 8);
    private static final Position GREEN_LEFT_POSITION_OF_CHARIOT = new Position(9, 0);
    private static final Position GREEN_RIGHT_POSITION_OF_CHARIOT = new Position(9, 8);

    private static final Position RED_LEFT_POSITION_OF_CANNON = new Position(2, 1);
    private static final Position RED_RIGHT_POSITION_OF_CANNON = new Position(2, 7);
    private static final Position GREEN_LEFT_POSITION_OF_CANNON = new Position(7, 1);
    private static final Position GREEN_RIGHT_POSITION_OF_CANNON = new Position(7, 7);

    private static final Position JOL_FIRST = new Position(6, 0);
    private static final Position JOL_SECOND = new Position(6, 2);
    private static final Position JOL_THIRD = new Position(6, 4);
    private static final Position JOL_FOURTH = new Position(6, 6);
    private static final Position JOL_FIFTH = new Position(6, 8);

    private static final Position BYEONG_FIRST = new Position(3, 0);
    private static final Position BYEONG_SECOND = new Position(3, 2);
    private static final Position BYEONG_THIRD = new Position(3, 4);
    private static final Position BYEONG_FOURH = new Position(3, 6);
    private static final Position BYEONG_FIFTH = new Position(3, 8);

    public static Map<Position, Piece> generate() {
        Map<Position, Piece> tmpPieces = new HashMap<>();

        tmpPieces.put(RED_POSITION_OF_GENERAL, new General(Team.RED));
        tmpPieces.put(GREEN_POSITION_OF_GENERAL, new General(Team.GREEN));

        tmpPieces.put(RED_LEFT_POSITION_OF_GUARD, new Guard(Team.RED));
        tmpPieces.put(RED_RIGHT_POSITION_OF_GUARD, new Guard(Team.RED));
        tmpPieces.put(GREEN_LEFT_POSITION_OF_GUARD, new Guard(Team.GREEN));
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_GUARD, new Guard(Team.GREEN));

        tmpPieces.put(RED_LEFT_POSITION_OF_HORSE, new Horse(Team.RED));
        tmpPieces.put(RED_RIGHT_POSITION_OF_HORSE, new Horse(Team.RED));
        tmpPieces.put(GREEN_LEFT_POSITION_OF_HORSE, new Horse(Team.GREEN));
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_HORSE, new Horse(Team.GREEN));

        tmpPieces.put(RED_LEFT_POSITION_OF_ELEPHANT, new Elephant(Team.RED));
        tmpPieces.put(RED_RIGHT_POSITION_OF_ELEPHANT, new Elephant(Team.RED));
        tmpPieces.put(GREEN_LEFT_POSITION_OF_ELEPHANT, new Elephant(Team.GREEN));
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_ELEPHANT, new Elephant(Team.GREEN));

        tmpPieces.put(RED_LEFT_POSITION_OF_CHARIOT, new Chariot(Team.RED));
        tmpPieces.put(RED_RIGHT_POSITION_OF_CHARIOT, new Chariot(Team.RED));
        tmpPieces.put(GREEN_LEFT_POSITION_OF_CHARIOT, new Chariot(Team.GREEN));
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_CHARIOT, new Chariot(Team.GREEN));

        tmpPieces.put(RED_LEFT_POSITION_OF_CANNON, new Cannon(Team.RED));
        tmpPieces.put(RED_RIGHT_POSITION_OF_CANNON, new Cannon(Team.RED));
        tmpPieces.put(GREEN_LEFT_POSITION_OF_CANNON, new Cannon(Team.GREEN));
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_CANNON, new Cannon(Team.GREEN));

        tmpPieces.put(JOL_FIRST, new Jol());
        tmpPieces.put(JOL_SECOND, new Jol());
        tmpPieces.put(JOL_THIRD, new Jol());
        tmpPieces.put(JOL_FOURTH, new Jol());
        tmpPieces.put(JOL_FIFTH, new Jol());

        tmpPieces.put(BYEONG_FIRST, new Byeong());
        tmpPieces.put(BYEONG_SECOND, new Byeong());
        tmpPieces.put(BYEONG_THIRD, new Byeong());
        tmpPieces.put(BYEONG_FOURH, new Byeong());
        tmpPieces.put(BYEONG_FIFTH, new Byeong());
        return tmpPieces;
    }
}

