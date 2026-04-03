package controller;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.game.Game;
import domain.team.Team;
import dto.InputMoveDto;
import dto.Move;
import parser.MoveInputParser;
import parser.MoveMapper;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Formation hanFormation = Formation.valueOf(inputView.inputHanWingSetup());
        Formation choFormation = Formation.valueOf(inputView.inputChoWingSetup());
        JanggiGenerator janggiGenerator = new JanggiGenerator(hanFormation, choFormation);

        Game game = new Game(new JanggiBoard(janggiGenerator));

        while (game.isRunning()) {
            try {
                outputView.printCurrentBoardStatus(game.boardStatus());
                final Team turn = game.currentTurn();
                outputView.printCurrentTurn(turn);
                InputMoveDto inputMoveDto = MoveInputParser.parse(inputView.inputMovePiecePoint(),
                        inputView.inputDestinationPoint());
                Move move = MoveMapper.toMove(inputMoveDto);
                game.processTurn(move.getFrom(), move.getTo());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        final Team winnerTeam = game.currentTurn().nextTurn();
        outputView.printWinnerTeam(winnerTeam);
    }
}
