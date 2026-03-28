package janggi.controller;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.command.SetupCommand;
import janggi.domain.piece.Piece;
import janggi.domain.setup.SetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.dto.BoardDto;
import janggi.utils.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    public JanggiController() {
    }

    private Team setupRedTeam() {
        OutputView.printSetupGuide(TeamType.RED);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final SetupPolicy setupPolicy = setupCommand.toPolicy();
        return new RedTeam(setupPolicy);
    }

    private Team setupBlueTeam() {
        OutputView.printSetupGuide(TeamType.BLUE);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final SetupPolicy setupPolicy = setupCommand.toPolicy();
        return new BlueTeam(setupPolicy);
    }

    public void run() {
        final Team redTeam = setupRedTeam();
        final Team blueTeam = setupBlueTeam();
        final Board board = BoardGenerator.generate(redTeam, blueTeam);
        final TurnManager turnManager = new TurnManager(List.of(redTeam, blueTeam));
        OutputView.printBoard(BoardDto.from(board, List.of()));
        progress(turnManager, board);
    }

    private SetupCommand readSetupCommand() {
        int commandNumber = InputView.readSetupCommand();
        return SetupCommand.pick(commandNumber);
    }

    private void progress(final TurnManager turnManager, final Board board) {
        final BoardMediator boardMediator = new BoardMediatorImpl(board);
        while (!board.isGameOver()) {
            final Team currentTeam = turnManager.getCurrentTeam();
            OutputView.printTurnStatus(currentTeam);
            final Position positionOfMovingPiece = RetryExecutor.retry(
                this::readPositionOfMovingPiece, currentTeam, boardMediator);
            final Piece pieceToMove = boardMediator.getPieceInPosition(positionOfMovingPiece);
            final List<Position> movablePositions = pieceToMove.calculateMovablePositions(
                positionOfMovingPiece, boardMediator);
            OutputView.printBoard(BoardDto.from(board, movablePositions));
            final Position targetPosition = readTargetPosition(movablePositions);
            board.movePiece(positionOfMovingPiece, targetPosition);
            OutputView.printBoard(BoardDto.from(board, List.of()));
            turnManager.progressToNext();
        }
    }

    private Position readPositionOfMovingPiece(final Team team, final BoardMediator boardMediator) {
        final String rawPosition = InputView.readPositionOfMovingPiece();
        final Position positionOfMovingPiece = Position.from(rawPosition);
        if (!boardMediator.existsInPosition(positionOfMovingPiece)) {
            throw new IllegalArgumentException("입력된 위치에 기물이 존재하지 않습니다.");
        }
        if (!team.hasPiece(boardMediator.getPieceInPosition(positionOfMovingPiece))) {
            throw new IllegalArgumentException("입력된 위치에 있는 기물은 팀 기물이 아닙니다.");
        }

        return positionOfMovingPiece;
    }

    private Position readTargetPosition(final List<Position> movablePositions) {
        final String rawPosition = InputView.readTargetPosition();
        final Position targetPosition = Position.from(rawPosition);
        if (!movablePositions.contains(targetPosition)) {
            throw new IllegalArgumentException("입력된 위치에는 이동할 수 없습니다.");
        }

        return targetPosition;
    }
}
