package janggi.view.output;

import janggi.domain.team.TeamType;
import janggi.dto.BoardSpots;

public interface OutputView {

    void printMessage(String message);

    void printSameLine(String message);

    void printNewLine();

    void printStartMessage();

    void printBoard(BoardSpots boardSpots);

    void printTurnNotice(TeamType currentTurnTeam);

    void printAskPiecePosition();

    void printAskMovePosition(String nickname);

    void printResult(BoardSpots boardSpots, TeamType winner, int winnerScore);
}
