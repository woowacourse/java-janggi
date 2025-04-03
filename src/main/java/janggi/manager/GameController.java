package janggi.manager;

import janggi.domain.JanggiGame;
import janggi.domain.movement.Position;
import janggi.dto.PositionDto;
import janggi.util.RecoveryUtil;
import janggi.view.Viewer;

public class GameController {

    private final DataController dataController;
    private final Viewer viewer;
    private final JanggiGame janggiGame;

    public GameController(DataController dataController, Viewer viewer, JanggiGame janggiGame) {
        this.dataController = dataController;
        this.viewer = viewer;
        this.janggiGame = janggiGame;
    }

    public void start() {
        repeatGameTurns();
    }

    private void repeatGameTurns() {
        while (janggiGame.hasBothGenerals()) {
            viewer.printBoard(janggiGame.getCurrentPieces());
            viewer.printPoints(janggiGame.getCurrentPoints());
            viewer.printTurnInfo(janggiGame.getCurrentTurn());

            RecoveryUtil.executeWithRetry(this::commenceTurn);
        }
    }

    private void commenceTurn() {
        Position selectedPosition = getSelectedPosition();
        Position targetPosition = getTargetPosition();

        janggiGame.commence(selectedPosition, targetPosition);

        updateDatabase(selectedPosition, targetPosition);
    }

    private Position getSelectedPosition() {
        PositionDto positionDto = viewer.readPieceSelection();
        return Position.of(positionDto.row(), positionDto.column());
    }

    private Position getTargetPosition() {
        PositionDto positionDto = viewer.readMove();
        return Position.of(positionDto.row(), positionDto.column());
    }

    private void updateDatabase(Position oldPosition, Position newPosition) {
        dataController.update(oldPosition, newPosition, janggiGame.getCurrentTurn());
    }

    public void finish() {
        viewer.printBoard(janggiGame.getCurrentPieces());
        viewer.printWinner(janggiGame.getCurrentTurn());

        dataController.deleteGameData();
    }
}
