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

        Piece generalOfRed = new General(RED_POSITION_OF_GENERAL, Team.RED);
        Piece generalOfGreen = new General(GREEN_POSITION_OF_GENERAL, Team.GREEN);
        tmpPieces.put(RED_POSITION_OF_GENERAL, generalOfRed);
        tmpPieces.put(GREEN_POSITION_OF_GENERAL, generalOfGreen);

        Piece guardLeftOfRed = new Guard(RED_LEFT_POSITION_OF_GUARD, Team.RED);
        Piece guardRightOfRed = new Guard(RED_RIGHT_POSITION_OF_GUARD, Team.RED);
        Piece guardLeftOfGreen = new Guard(GREEN_LEFT_POSITION_OF_GUARD, Team.GREEN);
        Piece guardRightOfGreen = new Guard(GREEN_RIGHT_POSITION_OF_GUARD, Team.GREEN);

        tmpPieces.put(RED_LEFT_POSITION_OF_GUARD, guardLeftOfRed);
        tmpPieces.put(RED_RIGHT_POSITION_OF_GUARD, guardRightOfRed);
        tmpPieces.put(GREEN_LEFT_POSITION_OF_GUARD, guardLeftOfGreen);
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_GUARD, guardRightOfGreen);

        Piece horseLeftOfRed = new Horse(RED_LEFT_POSITION_OF_HORSE, Team.RED);
        Piece horseRightOfRed = new Horse(RED_RIGHT_POSITION_OF_HORSE, Team.RED);
        Piece horseLeftOfGreen = new Horse(GREEN_LEFT_POSITION_OF_HORSE, Team.GREEN);
        Piece horseRightOfGreen = new Horse(GREEN_RIGHT_POSITION_OF_HORSE, Team.GREEN);

        tmpPieces.put(RED_LEFT_POSITION_OF_HORSE, horseLeftOfRed);
        tmpPieces.put(RED_RIGHT_POSITION_OF_HORSE, horseRightOfRed);
        tmpPieces.put(GREEN_LEFT_POSITION_OF_HORSE, horseLeftOfGreen);
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_HORSE, horseRightOfGreen);

        Piece elephantLeftOfRed = new Elephant(RED_LEFT_POSITION_OF_ELEPHANT, Team.RED);
        Piece elephantRightOfRed = new Elephant(RED_RIGHT_POSITION_OF_ELEPHANT, Team.RED);
        Piece elephantLeftOfGreen = new Elephant(GREEN_LEFT_POSITION_OF_ELEPHANT, Team.GREEN);
        Piece elephantRightOfGreen = new Elephant(GREEN_RIGHT_POSITION_OF_ELEPHANT, Team.GREEN);


        tmpPieces.put(RED_LEFT_POSITION_OF_ELEPHANT, elephantLeftOfRed);
        tmpPieces.put(RED_RIGHT_POSITION_OF_ELEPHANT, elephantRightOfRed);
        tmpPieces.put(GREEN_LEFT_POSITION_OF_ELEPHANT, elephantLeftOfGreen);
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_ELEPHANT, elephantRightOfGreen);


        Piece chariotLeftOfRed = new Chariot(RED_LEFT_POSITION_OF_CHARIOT, Team.RED);
        Piece chariotRightOfRed = new Chariot(RED_RIGHT_POSITION_OF_CHARIOT, Team.RED);
        Piece chariotLeftOfGreen = new Chariot(GREEN_LEFT_POSITION_OF_CHARIOT, Team.GREEN);
        Piece chariotRightOfGreen = new Chariot(GREEN_RIGHT_POSITION_OF_CHARIOT, Team.GREEN);


        tmpPieces.put(RED_LEFT_POSITION_OF_CHARIOT, chariotLeftOfRed);
        tmpPieces.put(RED_RIGHT_POSITION_OF_CHARIOT, chariotRightOfRed);
        tmpPieces.put(GREEN_LEFT_POSITION_OF_CHARIOT, chariotLeftOfGreen);
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_CHARIOT, chariotRightOfGreen);


        Piece cannonLeftOfRed = new Cannon(RED_LEFT_POSITION_OF_CANNON, Team.RED);
        Piece cannonRightOfRed = new Cannon(RED_RIGHT_POSITION_OF_CANNON, Team.RED);
        Piece cannonLeftOfGreen = new Cannon(GREEN_LEFT_POSITION_OF_CANNON, Team.GREEN);
        Piece cannonRightOfGreen = new Cannon(GREEN_RIGHT_POSITION_OF_CANNON, Team.GREEN);

        tmpPieces.put(RED_LEFT_POSITION_OF_CANNON, cannonLeftOfRed);
        tmpPieces.put(RED_RIGHT_POSITION_OF_CANNON, cannonRightOfRed);
        tmpPieces.put(GREEN_LEFT_POSITION_OF_CANNON, cannonLeftOfGreen);
        tmpPieces.put(GREEN_RIGHT_POSITION_OF_CANNON, cannonRightOfGreen);

        Piece firstJol = new Jol(JOL_FIRST);
        Piece secondJol = new Jol(JOL_SECOND);
        Piece thirdJol = new Jol(JOL_THIRD);
        Piece fourthJol = new Jol(JOL_FOURTH);
        Piece fifthJol = new Jol(JOL_FIFTH);

        tmpPieces.put(JOL_FIRST, firstJol);
        tmpPieces.put(JOL_SECOND, secondJol);
        tmpPieces.put(JOL_THIRD, thirdJol);
        tmpPieces.put(JOL_FOURTH, fourthJol);
        tmpPieces.put(JOL_FIFTH, fifthJol);

        Piece firstByeong = new Byeong(BYEONG_FIRST);
        Piece secondByeong = new Byeong(BYEONG_SECOND);
        Piece thirdByeong = new Byeong(BYEONG_THIRD);
        Piece fourthByeong = new Byeong(BYEONG_FOURH);
        Piece fifthByeong = new Byeong(BYEONG_FIFTH);


        tmpPieces.put(BYEONG_FIRST, firstByeong);
        tmpPieces.put(BYEONG_SECOND, secondByeong);
        tmpPieces.put(BYEONG_THIRD, thirdByeong);
        tmpPieces.put(BYEONG_FOURH, fourthByeong);
        tmpPieces.put(BYEONG_FIFTH, fifthByeong);

        return tmpPieces;
    }
}

