package janggi.controller;

import janggi.db.entity.GameEntity;
import janggi.db.repository.JanggiRepository;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFormation;
import janggi.domain.board.BoardInitiator;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.dto.BoardResponse;
import janggi.dto.GameResponse;
import janggi.dto.ScoreResponse;
import janggi.dto.TeamResponse;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final BoardInitiator boardInitiator = new BoardInitiator();

    private final JanggiRepository janggiRepository;
    private Long gameId;
    private Team turn;
    private Board board;
    private boolean isGameOver;

    public JanggiController(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public void run() {
        outputView.printStartOption();

        initializeGame(inputView.readOption());

        outputView.printBoard(BoardResponse.from(board));

        while (!isGameOver) {
            playGame(turn, board);
            isGameOver = board.isKingDead();
            turn = turn.next();
            gameId = janggiRepository.save(gameId, board, turn, isGameOver);
            outputView.printTeamScore(ScoreResponse.from(board.calculateScore()));
        }
        outputView.printWinner(TeamResponse.from(board.findWinner()));
    }

    private void initializeGame(int option) {
        if (option == 1) {
            startNewGame();
            return;
        }
        if (option == 2) {
            loadExistingGame();
            return;
        }
        throw new IllegalArgumentException("[ERROR] 옵션 중에서 선택하세요.");
    }

    private void loadExistingGame() {
        List<GameEntity> ongoingGames = janggiRepository.findOngoingGames();
        List<GameResponse> gameResponses = ongoingGames.stream()
                .map(GameResponse::from)
                .toList();
        outputView.printOngoingGames(gameResponses);

        gameId = inputView.readSelectGameId();
        GameEntity selectedGame = janggiRepository.findGameById(gameId);

        turn = selectedGame.getTurn();
        isGameOver = selectedGame.isFinished();

        board = janggiRepository.loadBoard(gameId);
    }

    private void startNewGame() {
        board = new Board();
        gameId = null;
        turn = Team.CHO;
        isGameOver = false;

        choiceBoardFormation(board);
        gameId = janggiRepository.save(gameId, board, turn, isGameOver);
    }

    private void choiceBoardFormation(Board board) {
        askBoardFormation(board, Team.HAN);
        askBoardFormation(board, Team.CHO);
    }

    private void askBoardFormation(Board board, Team team) {
        outputView.printBoardFormation(TeamResponse.from(team));
        int boardFormationChoice = inputView.readBoardFormationChoice();
        BoardFormation formation = BoardFormation.selectByChoice(boardFormationChoice);
        boardInitiator.initializeByFormation(board, formation, team);
    }

    private void playGame(Team team, Board board) {
        outputView.printTurnMessage(TeamResponse.from(team));

        Position movePiecePosition = askMovePiecePositionUntilValid(board, team);

        List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);

        outputView.printBoard(BoardResponse.of(board, availablePositions));

        Position movePosition = askMovePositionUntilValid(board, movePiecePosition);

        board.movePiece(movePiecePosition, movePosition);

        outputView.printBoard(BoardResponse.from(board));
    }

    private Position askMovePiecePositionUntilValid(Board board, Team team) {
        Position position = null;
        while (position == null) {
            position = getValidMovePiecePositionOrNull(board, team);
        }
        return position;
    }

    private Position getValidMovePiecePositionOrNull(Board board, Team team) {
        try {
            outputView.printMoveInfo();
            Position position = inputView.readPosition();
            board.validateMovePiecePosition(position, team);
            List<Position> positions = board.findAvailablePositions(position);
            board.validateAvailablePositions(positions);
            return position;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return null;
        }
    }

    private Position askMovePositionUntilValid(Board board, Position movePiecePosition) {
        Position position = null;
        while (position == null) {
            position = getValidMovePositionOrNull(board, movePiecePosition);
        }
        return position;
    }

    private Position getValidMovePositionOrNull(Board board, Position movePiecePosition) {
        try {
            outputView.printMoveChoiceInfo();
            Position position = inputView.readPosition();
            board.validateDestination(movePiecePosition, position);
            return position;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return null;
        }
    }
}
