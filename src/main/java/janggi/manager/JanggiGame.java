package janggi.manager;

import janggi.domain.Round;
import janggi.domain.movement.Position;
import janggi.dto.PositionDto;
import janggi.util.RecoveryUtil;
import janggi.view.Viewer;

public class JanggiGame {

    private final JanggiData janggiData;
    private final Viewer viewer;
    private final Round round;

    public JanggiGame(JanggiData janggiData, Viewer viewer, Round round) {
        this.janggiData = janggiData;
        this.viewer = viewer;
        this.round = round;
    }

    public void start() {
        repeatGameTurns();
    }

    private void repeatGameTurns() {
        while (round.hasBothGenerals()) {
            viewer.printBoard(round.getCurrentPieces());
            viewer.printPoints(round.getCurrentPoints());
            viewer.printTurnInfo(round.getCurrentTurn());

            RecoveryUtil.executeWithRetry(this::commenceTurn);
        }
    }

    private void commenceTurn() {
        Position selectedPosition = getSelectedPosition();
        Position targetPosition = getTargetPosition();

        round.commence(selectedPosition, targetPosition, janggiData::update);
    }

    private Position getSelectedPosition() {
        PositionDto positionDto = viewer.readPieceSelection();
        return Position.of(positionDto.row(), positionDto.column());
    }

    private Position getTargetPosition() {
        PositionDto positionDto = viewer.readMove();
        return Position.of(positionDto.row(), positionDto.column());
    }

    public void finish() {
        viewer.printBoard(round.getCurrentPieces());
        viewer.printWinner(round.getCurrentTurn());

        janggiData.resetDatabase();
    }
}
