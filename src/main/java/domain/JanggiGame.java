package domain;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.piece.Team;
import domain.score.Score;
import domain.score.ScoreCalculator;
import view.MoveCommand;
import view.SangMaOrderCommand;

import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;
    private final ScoreCalculator scoreCalculator;

    public JanggiGame(final BoardGenerator boardGenerator,
                      final SangMaOrderCommand hanSangMaOrderCommand,
                      final SangMaOrderCommand choSangMaOrderCommand,
                      final Turn turn,
                      final ScoreCalculator scoreCalculator) {
        this.board = boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
        this.turn = turn;
        this.scoreCalculator = scoreCalculator;
    }

    public void movePiece(MoveCommand moveCommand) {
        Node sourceNode = board.findNodeByPoint(moveCommand.source());
        Node destinationNode = board.findNodeByPoint(moveCommand.destination());
        if (!board.hasPieceTeamByNode(sourceNode, turnTeam())) {
            throw new IllegalArgumentException("[ERROR] 이번 턴은 " + turnTeam().title() + "나라입니다.");
        }
        board.movePiece(sourceNode, destinationNode, board);
    }

    public boolean isStop() {
        return board.isOpponentWangDead(turnTeam());
    }

    public void changeTurn() {
        turn.changeTurn();
    }

    public Map<Team, Score> calculateTotalScoreByTeam() {
        return board.calculateTotalScoreByTeam(scoreCalculator);
    }

    public Team findWinTeam() {
        Map<Team, Score> totalScoreByTeam = calculateTotalScoreByTeam();
        Score choScore = totalScoreByTeam.get(Team.CHO);
        Score hanScore = totalScoreByTeam.get(Team.HAN);
        if (choScore.greaterThan(hanScore)) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public Board board() {
        return board;
    }

    public Team turnTeam() {
        return turn.team();
    }
}
