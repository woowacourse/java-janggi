package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.PieceSelection;
import janggi.domain.game.Player;
import janggi.domain.game.PlayerResultDTO;
import janggi.domain.game.Players;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.board.BoardDTO;
import janggi.domain.game.PlayerDTO;
import janggi.domain.repository.JanggiRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class JanggiGame {

    private final OutputView outputView;
    private final InputView inputView;
    private final Board board;
    private final JanggiRepository repository;
    private final Long gameId;

    public JanggiGame(OutputView outputView, InputView inputView, JanggiRepository repository, Board board,
                      Long gameId) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.repository = repository;
        this.board = board;
        this.gameId = gameId;
    }

    public void play(Players players) {
        printBoard();

        while (true) {
            Player current = players.getCurrentPlayer();
            PlayerDTO currentPlayerDTO = PlayerDTO.from(current);
            printPlayerTurnNotice(currentPlayerDTO);

            playerTurn(currentPlayerDTO);

            if (board.isGameOver()) {
                handleGameOver(currentPlayerDTO, players);
                break;
            }

            players.switchTurn();
            repository.updateGameStatus(this.gameId, board, players.getTurn());
        }
    }

    private void printBoard() {
        outputView.printBoardSettingNotice();
        outputView.printBoardStatus(BoardDTO.from(board));
    }

    private void playerTurn(PlayerDTO currentPlayer) {
        Side currentSide = currentPlayer.side();
        PieceSelection pieceSelection = selectMovablePiece(currentSide);
        movePiece(pieceSelection.selected(), pieceSelection.destinations());
    }

    private void handleGameOver(PlayerDTO winner, Players players) {
        repository.finishGame(this.gameId);
        outputView.printWinnerNotice(winner.side(), winner.name());

        List<PlayerResultDTO> playerResults = new ArrayList<>();
        for (Player player : players) {
            double score = board.calculateScore(player.getSide());
            playerResults.add(new PlayerResultDTO(player.getName(), player.getSide(), score));
        }
        outputView.printTotalScores(playerResults);
    }

    private PieceSelection selectMovablePiece(Side currentSide) {
        return retry(() -> {
            outputView.printSelectPiecePosition();
            Position position = readTargetPosition();
            List<Position> destinations = board.calculateDestinations(position, currentSide);

            return new PieceSelection(position, destinations);
        });
    }

    private void movePiece(Position selected, List<Position> destinations) {
        outputView.printBoardStatus(BoardDTO.from(board), selected, new HashSet<>(destinations));
        Position target = selectValidTarget(destinations);
        board.movePiece(selected, target);
        outputView.printBoardStatus(BoardDTO.from(board), target);
    }

    private Position selectValidTarget(List<Position> destinations) {
        return retry(() -> {
            outputView.printSelectTargetPosition();
            Position inputTarget = readTargetPosition();

            if (!destinations.contains(inputTarget)) {
                throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다. 다시 선택하세요.");
            }

            return inputTarget;
        });
    }

    private Position readTargetPosition() {
        return retry(() -> {
            outputView.printMovePositionRowNotice();
            int row = inputView.readTargetRow();
            outputView.printMovePositionColumnNotice();
            int column = inputView.readTargetColumn();

            return new Position(row, column);
        });
    }

    private void printPlayerTurnNotice(PlayerDTO currentPlayer) {
        outputView.printPlayerTurnNotice(currentPlayer.name(), currentPlayer.side().getDisplayName());
    }

    private <T> T retry(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryOnce(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryOnce(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return Optional.empty();
        }
    }
}
