package janggi.view.output;

import janggi.dto.BoardSpots;

public interface OutputView {

    void printMessage(String message);

    void printSameLine(String message);

    void printNewLine();

    void printStartMessage();

    void printBoard(BoardSpots boardSpots);

    void printTurnNotice(String currentTurnTeamName);

    void printAskPiecePosition();

    void printAskMovePosition(String nickname);
}
