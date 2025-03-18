package model;

import java.util.List;

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

    public static List<Piece> generate() {
        Piece generalOfRed = new General(RED_POSITION_OF_GENERAL, Team.RED);
        Piece generalOfGreen = new General(GREEN_POSITION_OF_GENERAL, Team.GREEN);

        Piece guardLeftOfRed = new Guard(RED_LEFT_POSITION_OF_GUARD, Team.RED);
        Piece guardRightOfRed = new Guard(RED_RIGHT_POSITION_OF_GUARD, Team.RED);
        Piece guardLeftOfGreen = new Guard(GREEN_LEFT_POSITION_OF_GUARD, Team.GREEN);
        Piece guardRightOfGreen = new Guard(GREEN_RIGHT_POSITION_OF_GUARD, Team.GREEN);

        Piece horseLeftOfRed = new Horse(RED_LEFT_POSITION_OF_HORSE, Team.RED);
        Piece horseRightOfRed = new Horse(RED_RIGHT_POSITION_OF_HORSE, Team.RED);
        Piece horseLeftOfGreen = new Horse(GREEN_LEFT_POSITION_OF_HORSE, Team.GREEN);
        Piece horseRightOfGreen = new Horse(GREEN_RIGHT_POSITION_OF_HORSE, Team.GREEN);

        Piece elephantLeftOfRed = new Elephant(RED_LEFT_POSITION_OF_ELEPHANT, Team.RED);
        Piece elephantRightOfRed = new Elephant(RED_RIGHT_POSITION_OF_ELEPHANT, Team.RED);
        Piece elephantLeftOfGreen = new Elephant(GREEN_LEFT_POSITION_OF_ELEPHANT, Team.GREEN);
        Piece elephantRightOfGreen = new Elephant(GREEN_RIGHT_POSITION_OF_ELEPHANT, Team.GREEN);

        Piece chariotLeftOfRed = new Chariot(RED_LEFT_POSITION_OF_CHARIOT, Team.RED);
        Piece chariotRightOfRed = new Chariot(RED_RIGHT_POSITION_OF_CHARIOT, Team.RED);
        Piece chariotLeftOfGreen = new Chariot(GREEN_LEFT_POSITION_OF_CHARIOT, Team.GREEN);
        Piece chariotRightOfGreen = new Chariot(GREEN_RIGHT_POSITION_OF_CHARIOT, Team.GREEN);

        Piece cannonLeftOfRed = new Chariot(RED_LEFT_POSITION_OF_CANNON, Team.RED);
        Piece cannonRightOfRed = new Chariot(RED_RIGHT_POSITION_OF_CANNON, Team.RED);
        Piece cannonLeftOfGreen = new Chariot(GREEN_LEFT_POSITION_OF_CANNON, Team.GREEN);
        Piece cannonRightOfGreen = new Chariot(GREEN_RIGHT_POSITION_OF_CANNON, Team.GREEN);

        Piece firstJol = new Jol(JOL_FIRST);
        Piece secondJol = new Jol(JOL_SECOND);
        Piece thirdJol = new Jol(JOL_THIRD);
        Piece fourthJol = new Jol(JOL_FOURTH);
        Piece fifthJol = new Jol(JOL_FIFTH);

        Piece firstByeong = new Byeong(BYEONG_FIRST);
        Piece secondByeong = new Byeong(BYEONG_SECOND);
        Piece thirdByeong = new Byeong(BYEONG_THIRD);
        Piece fourthByeong = new Byeong(BYEONG_FOURH);
        Piece fifthByeong = new Byeong(BYEONG_FIFTH);

        return List.of(
            generalOfRed, generalOfGreen,
            guardLeftOfRed, guardRightOfRed, guardLeftOfGreen, guardRightOfGreen,
            horseLeftOfRed, horseRightOfRed, horseLeftOfGreen, horseRightOfGreen,
            elephantLeftOfRed, elephantRightOfRed, elephantLeftOfGreen, elephantRightOfGreen,
            chariotLeftOfRed, chariotRightOfRed, chariotLeftOfGreen, chariotRightOfGreen,
            cannonLeftOfRed, cannonRightOfRed, cannonLeftOfGreen, cannonRightOfGreen,
            firstJol, secondJol, thirdJol, fourthJol, fifthJol,
            firstByeong, secondByeong, thirdByeong, fourthByeong, fifthByeong);

    }

}

