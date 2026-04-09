package controller;

import domain.board.BoardState;
import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.game.Game;
import domain.game.MoveCommand;
import domain.team.Team;
import dto.InputMoveDto;
import dto.IntersectionsDto;
import mapper.BoardOutputMapper;
import mapper.MoveMapper;
import parser.MoveInputParser;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardOutputMapper boardOutputMapper;

    public JanggiController(InputView inputView, OutputView outputView, BoardOutputMapper boardOutputMapper) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardOutputMapper = boardOutputMapper;
    }

    public void run() {
        Formation hanFormation = Formation.valueOf(inputView.inputHanWingSetup());
        Formation choFormation = Formation.valueOf(inputView.inputChoWingSetup());
        JanggiGenerator janggiGenerator = new JanggiGenerator(hanFormation, choFormation);

        final Game game = new Game(new JanggiBoard(janggiGenerator));

        while (game.isRunning()) {
            processTurn(game);
        }
        Team winnerTeam = game.currentTurn().nextTurn();
        outputView.printWinnerTeam(winnerTeam);
    }

    private void processTurn(Game game) {
        try {
            BoardState boardState = game.getBoardState();
            IntersectionsDto intersectionsDto = boardOutputMapper.toDto(boardState);
            outputView.printCurrentBoardStatus(intersectionsDto);

            Team turn = game.currentTurn();
            outputView.printCurrentTurn(turn);
            outputView.printCurrentScore(game.currentScore());

            InputMoveDto inputMoveDto = getInputMove();
            MoveCommand move = MoveMapper.toMove(inputMoveDto);
            game.processTurn(move);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private InputMoveDto getInputMove() {
        String movePoint = inputView.inputMovePiecePoint();
        String destinationPoint = inputView.inputDestinationPoint();
        return MoveInputParser.parse(movePoint, destinationPoint);
    }
}
