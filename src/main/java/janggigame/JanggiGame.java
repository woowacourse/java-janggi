package janggigame;

import domain.board.Board;
import repository.BoardRepository;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.JanggiGameResultResponseDto;
import repository.JanggiGameRepository;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private static final int JANGGUN_COUNT = 5;

    private final Map<Side, Integer> jangGunCount = new EnumMap<>(Side.class);
    private final JanggiGameRepository janggiGameRepository;
    private final BoardRepository boardRepository;

    public JanggiGame(JanggiGameRepository janggiGameRepository, BoardRepository boardRepository) {
        this.janggiGameRepository = janggiGameRepository;
        this.boardRepository = boardRepository;
    }

    public void run() {
        GameMetaData gameMetaData = loadOrCreateNewGame();
        restoreJangGunCount(gameMetaData);
        Board board = loadOrInitBoard(gameMetaData);
        processByStatus(board, gameMetaData);
    }

    private GameMetaData loadOrCreateNewGame() {
        OutputView.printLoadGame();
        return janggiGameRepository.findLatestUnfinishedGame()
                .orElseGet(this::createNewGame);
    }

    private GameMetaData createNewGame() {
        OutputView.printCreateNewGame();
        return janggiGameRepository.save(GameMetaData.newGame());
    }

    private void restoreJangGunCount(GameMetaData gameMetaData) {
        jangGunCount.put(Side.CHO, gameMetaData.choJangGunCount());
        jangGunCount.put(Side.HAN, gameMetaData.hanJangGunCount());
    }

    private Board loadOrInitBoard(GameMetaData gameMetaData) {
        return boardRepository.findById(gameMetaData.id())
                .orElseGet(Board::new);
    }

    private void processByStatus(Board board, GameMetaData gameMetaData) {
        if (gameMetaData.status() == JanggiGameStatus.WAITING_HAN_PLACEMENT) {
            handleWaitingHanPlacement(board, gameMetaData);
            return;
        }
        if (gameMetaData.status() == JanggiGameStatus.WAITING_CHO_PLACEMENT) {
            handleWaitingChoPlacement(board, gameMetaData);
            return;
        }
        handleInProgress(board, gameMetaData);
    }

    private void handleWaitingHanPlacement(Board board, GameMetaData gameMetaData) {
        selectSide();
        initPlacement(Side.HAN, board);
        boardRepository.savePlacementById(board, gameMetaData.id());
        updateGameStatus(gameMetaData, JanggiGameStatus.WAITING_CHO_PLACEMENT);
        handleWaitingChoPlacement(board, gameMetaData);
    }

    private void handleWaitingChoPlacement(Board board, GameMetaData gameMetaData) {
        initPlacement(Side.CHO, board);
        boardRepository.savePlacementById(board, gameMetaData.id());
        updateGameStatus(gameMetaData, JanggiGameStatus.IN_PROGRESS);
        handleInProgress(board, gameMetaData);
    }

    private void handleInProgress(Board board, GameMetaData gameMetaData) {
        gameStart(board, gameMetaData);
        ScoreBoard scoreBoard = ScoreBoard.from(board);
        showResult(board, scoreBoard);
        updateGameStatus(gameMetaData, JanggiGameStatus.FINISHED);
    }

    private void selectSide() {
        while (true) {
            try {
                String input = InputView.inputSideChoice();
                int sideCode = Parser.parseToSideCode(input);
                Side side = generateSide(sideCode);
                OutputView.printSideChoiceResult(side);
                return;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);

        return sides.get(sideCode - 1);
    }

    private void initPlacement(Side side, Board board) {
        while (true) {
            try {
                String input = inputPlacementCode(side);
                int code = Parser.parseToPlacementCode(input);
                board.placePieces(side, Placement.from(code));
                printBoard(board);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String inputPlacementCode(Side side) {
        if (side == Side.HAN) return InputView.inputHanPlacementCode();
        return InputView.inputChoPlacementCode();
    }

    private void gameStart(Board board, GameMetaData gameMetaData) {
        Side currentTurnSide = gameMetaData.currentTurn();
        printBoard(board);
        while (!isGameOver(board, currentTurnSide)) {
            try {
                OutputView.printSide(currentTurnSide);
                pieceMoveProcess(board, currentTurnSide, gameMetaData);
                printBoard(board);

                currentTurnSide = changeSide(currentTurnSide);
                jangGunCountProcess(board, gameMetaData, currentTurnSide);
                changeTurnProcess(gameMetaData, currentTurnSide);
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void pieceMoveProcess(Board board, Side currentTurnSide, GameMetaData gameMetaData) {
        Position from = selectFromPosition();
        Position to = selectToPosition();
        boolean hasEnemyPieceAtTo = board.isBlocked(to) && !board.findBy(to).isSameSide(currentTurnSide);
        board.move(from, to, currentTurnSide);

        if (hasEnemyPieceAtTo) {
            boardRepository.deletePiecePositionById(to, gameMetaData.id());
        }
        boardRepository.updatePiecePositionById(from, to, gameMetaData.id());
    }

    private void jangGunCountProcess(Board board, GameMetaData gameMetaData, Side currentTurnSide) {
        updateJangGunCount(board, currentTurnSide);
        janggiGameRepository.updateJangGunCount(jangGunCount, gameMetaData);
    }

    private void changeTurnProcess(GameMetaData gameMetaData, Side currentTurnSide) {
        janggiGameRepository.updateTurn(currentTurnSide, gameMetaData);
    }

    private void updateGameStatus(GameMetaData gameMetaData, JanggiGameStatus newStatus) {
        janggiGameRepository.updateGameStatus(gameMetaData, newStatus);
    }

    private boolean isGameOver(Board board, Side currentTurnSide) {
        return isBigJang() || board.isEmptyGeneral(currentTurnSide);
    }

    private boolean isBigJang() {
        return jangGunCount.values().stream()
                .anyMatch(count -> count == JANGGUN_COUNT);
    }

    private void updateJangGunCount(Board board, Side currentTurnSide) {
        if (board.isJangGun(currentTurnSide)) {
            OutputView.printIsJangGun();
            jangGunCount.put(currentTurnSide, jangGunCount.getOrDefault(currentTurnSide, 0) + 1);
            return;
        }
        jangGunCount.put(currentTurnSide, 0);
    }

    private Position selectFromPosition() {
        String input = InputView.inputFromPosition();
        return Parser.parseToPosition(input);
    }

    private Position selectToPosition() {
        String input = InputView.inputToPosition();
        return Parser.parseToPosition(input);
    }

    private void printBoard(Board board) {
        BoardResponseDto nowBoardState = BoardResponseDto.from(board);
        OutputView.printBoard(nowBoardState);
    }

    private Side changeSide(Side currentTurnSide) {
        if (currentTurnSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }

    private void showResult(Board board, ScoreBoard scoreBoard) {
        if (board.isEmptyGeneral(Side.CHO)) {
            OutputView.printWinSide(Side.HAN);
            return;
        }
        if (board.isEmptyGeneral(Side.HAN)) {
            OutputView.printWinSide(Side.CHO);
            return;
        }
        OutputView.printScoreBothSide(JanggiGameResultResponseDto.from(scoreBoard));
    }
}
