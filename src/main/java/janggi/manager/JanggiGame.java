package janggi.manager;

import janggi.domain.Position;
import janggi.domain.Round;
import janggi.dto.PositionDto;
import janggi.util.RecoveryUtil;
import janggi.view.Viewer;

public class JanggiGame {

    private final Viewer viewer;
    private final Round round;

    public JanggiGame(Viewer viewer, Round round) {
        this.viewer = viewer;
        this.round = round;
    }

    public void start() {
        repeatGameTurns();

        viewer.printBoard(round.getCurrentPieces());
        viewer.winner(round.getCurrentTurn());
    }

    private void repeatGameTurns() {
        while (round.hasBothGenerals()) {
            viewer.printBoard(round.getCurrentPieces());
            viewer.printTurnInfo(round.getCurrentTurn());

            RecoveryUtil.executeWithRetry(this::commenceTurn);
        }
    }

    private void commenceTurn() {
        Position selectedPosition = getSelectedPosition();
        Position targetPosition = getTargetPosition();

        round.commence(selectedPosition, targetPosition);
    }

    private Position getSelectedPosition() {
        PositionDto positionDto = viewer.readPieceSelection();
        return Position.of(positionDto.row(), positionDto.column());
    }

    private Position getTargetPosition() {
        PositionDto positionDto = viewer.readMove();
        return Position.of(positionDto.row(), positionDto.column());
    }
}
