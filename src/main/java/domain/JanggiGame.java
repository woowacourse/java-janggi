package domain;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;
import domain.score.Score;
import domain.score.ScoreCalculator;
import view.command.MoveCommand;
import view.command.SangMaOrderCommand;

import java.util.Map;

public class JanggiGame {

    private static final Team START_TEAM = Team.CHO;

    private final Board board;
    private Team team;

    public JanggiGame(final SangMaOrderCommand hanSangMaOrderCommand, final SangMaOrderCommand choSangMaOrderCommand) {
        this.board = new BoardGenerator().generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
        this.team = START_TEAM;
    }

    public JanggiGame(final Map<Point, Piece> savedBoard, final Team savedTurn) {
        validateBoardAndTurn(savedBoard, savedTurn);
        this.board = new BoardGenerator().loadBoard(savedBoard);
        this.team = savedTurn;
    }

    private void validateBoardAndTurn(final Map<Point, Piece> savedBoard, final Team savedTurn) {
        if (savedBoard == null || savedBoard.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 저장된 보드 정보가 없습니다.");
        }

        if (savedTurn == null) {
            throw new IllegalArgumentException("[ERROR] 저장된 턴 정보가 없습니다.");
        }
    }

    public void movePiece(final MoveCommand moveCommand) {
        Node sourceNode = board.findNodeByPoint(moveCommand.source());
        Node destinationNode = board.findNodeByPoint(moveCommand.destination());
        if (!board.hasPieceTeamByNode(sourceNode, turnTeam())) {
            throw new IllegalArgumentException("[ERROR] 현재 턴의 기물을 이동해주세요.");
        }
        board.movePiece(sourceNode, destinationNode, board);
    }

    public boolean isStop() {
        return board.isOpponentWangDead(turnTeam());
    }

    public void changeTurn() {
        team = team.inverse();
    }

    public Map<Team, Score> calculateTotalScoreByTeam() {
        return board.calculateTotalScoreByTeam(new ScoreCalculator());
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

    public Map<Point, Piece> board() {
        return board.currentBoard();
    }

    public Team turnTeam() {
        return team;
    }
}
