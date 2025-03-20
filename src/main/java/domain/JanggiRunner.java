package domain;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.strategy.HorseElephantSetupStrategy;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiRunner {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(){
        JanggiGame janggiGame = initializeGame();
        List<Piece> alivePieces = janggiGame.getAlivePieces();
        outputView.printBoard(alivePieces);

        startGame(janggiGame);
    }

    public JanggiGame initializeGame() {
        String firstPlayerName = inputView.getFirstPlayerName();
        String secondPlayerName = inputView.getSecondPlayerName();
        Usernames usernames = new Usernames(firstPlayerName,secondPlayerName);

        String startPlayerName = inputView.getStartPlayerName();

        Players players = Players.createFrom(usernames,startPlayerName);

        String firstPlayerOption = inputView.getSetupNumber(players.getChoPlayerName());
        String secondPlayerOption = inputView.getSetupNumber(players.getHanPlayerName());

        HorseElephantSetupStrategy firstPlayerStrategy = SetupOption.findSetupStrategy(firstPlayerOption);
        HorseElephantSetupStrategy secondPlayerStrategy = SetupOption.findSetupStrategy(secondPlayerOption);

        PieceFactory factory = new PieceFactory();

        return new JanggiGame(players, factory.createAllPieces(firstPlayerStrategy, secondPlayerStrategy));
    }

    public void startGame(JanggiGame janggiGame) {
        TeamType nowTurn = TeamType.CHO;

        while(!janggiGame.isFinished()){
            Player nowPlayer = janggiGame.findPlayerByTeam(nowTurn);
            Position startPosition = inputView.getStartPosition(nowPlayer);
            Position endPosition = inputView.getEndPosition(nowPlayer);

            janggiGame.movePiece(startPosition, endPosition, nowTurn);

            outputView.printBoard(janggiGame.getAlivePieces());
            nowTurn = findNextTurn(nowTurn);
        }
        Player winner = janggiGame.findWinner();

        outputView.printWinner(winner);
    }

    private TeamType findNextTurn(TeamType nowTurn){
        if(nowTurn == TeamType.CHO){
            return TeamType.HAN;
        }
        return TeamType.CHO;
    }
}
