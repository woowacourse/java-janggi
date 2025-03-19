package janggi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.PositionSide;
import janggi.piece.Soldier;
import janggi.piece.TeamType;

public class Janggi {
    private final Board board;

    public Janggi(final Board board) {
        this.board = board;
    }

    public void initializeBoard(
            PositionSide blueLeftHorsePosition,
            PositionSide blueRightHorsePosition,
            PositionSide redLeftHorsePosition,
            PositionSide redRightHorsePosition

    ) {
        initializeGeneral();
        initializeGuard();
        initializeChariot();
        initializeCannon();
        initializeSoldier();
        initializeHorse(blueLeftHorsePosition, blueRightHorsePosition);
        initializeElephant(redLeftHorsePosition, redRightHorsePosition);
    }

    private void initializeGeneral() {
        putInBoard(General.createWithInitialPositions(TeamType.BLUE));
        putInBoard(General.createWithInitialPositions(TeamType.RED));
    }

    private void initializeGuard() {
        putInBoard(Guard.createWithInitialPositions(TeamType.BLUE));
        putInBoard(Guard.createWithInitialPositions(TeamType.RED));
    }

    private void initializeChariot() {
        putInBoard(Chariot.createWithInitialPositions(TeamType.BLUE));
        putInBoard(Chariot.createWithInitialPositions(TeamType.RED));
    }

    private void initializeCannon() {
        putInBoard(Cannon.createWithInitialPositions(TeamType.BLUE));
        putInBoard(Cannon.createWithInitialPositions(TeamType.RED));
    }

    private void initializeSoldier() {
        putInBoard(Soldier.createWithInitialPositions(TeamType.BLUE));
        putInBoard(Soldier.createWithInitialPositions(TeamType.RED));
    }

    private void initializeHorse(PositionSide leftHorsePosition, PositionSide rightHorsePosition) {
        putInBoard(Horse.createWithInitialPositions(TeamType.BLUE, leftHorsePosition, rightHorsePosition));
        putInBoard(Horse.createWithInitialPositions(TeamType.RED, leftHorsePosition, rightHorsePosition));
    }

    private void initializeElephant(PositionSide leftElephantPosition, PositionSide rightElephantPosition) {
        putInBoard(Elephant.createWithInitialPositions(TeamType.BLUE, leftElephantPosition, rightElephantPosition));
        putInBoard(Elephant.createWithInitialPositions(TeamType.RED, leftElephantPosition, rightElephantPosition));
    }

    private void putInBoard(List<Piece> pieces) {
        for(Piece piece : pieces) {
            board.putPiece(piece.getPosition(), piece);
        }
    }

}
