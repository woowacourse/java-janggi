import domain.Coordinate;
import domain.Piece;
import domain.Team;
import domain.board.Board;
import domain.board.BoardBuilder;
import domain.board.maSangStrategy.MaSangMaSang;
import domain.board.maSangStrategy.MaSangSangMa;
import domain.board.maSangStrategy.MaSangStrategy;
import domain.board.maSangStrategy.SangMaMaSang;
import domain.board.maSangStrategy.SangMaSangMa;
import java.util.Map;
import java.util.Set;
import repository.JanggiRepository;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Game {

    private final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
        1, new MaSangSangMa(),
        2, new MaSangMaSang(),
        3, new SangMaSangMa(),
        4, new SangMaMaSang()
    );

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiRepository janggiRepository;

    public Game(
        final InputView inputView,
        final OutputView outputView,
        final JanggiRepository janggiRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiRepository = janggiRepository;
    }

    public void start() {
        Board board = loadBoard();
        outputView.printBoard(board.getPieces());

        processGame(board);
    }

    private Board loadBoard() {
        Set<Piece> existingPieces = janggiRepository.findAll();
        if (existingPieces.isEmpty() || !inputView.readRenewGame()) {
            janggiRepository.clear();
            janggiRepository.setTurn(Team.HAN);
            Board board = createBoard();
            board.getPieces().values().forEach(janggiRepository::save);
            return board;
        }

        outputView.printContinueGame();
        return new Board(existingPieces);
    }

    private void processGame(final Board board) {
        while (true) {
            Team team = janggiRepository.getTurn();
            useTurn(board, team);
        }
    }

    private void useTurn(final Board board, final Team team) {
        try {
            CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(team);
            checkDepartureIsMyPiece(team, board, coordinatesPair.departure());

            movePiece(board, coordinatesPair);
            passTurn(team);

            outputView.printBoard(board.getPieces());
        } catch (IllegalArgumentException e) {
            outputView.printException(e);
        }
    }

    private void movePiece(final Board board, final CoordinatesPair coordinatesPair) {
        final var departure = coordinatesPair.departure();
        final var arrival = coordinatesPair.arrival();

        board.move(departure, arrival);

        janggiRepository.deleteByCoordinate(arrival);
        janggiRepository.update(departure, arrival);
    }

    private void passTurn(final Team currentTeam) {
        if (currentTeam == Team.HAN) {
            janggiRepository.setTurn(Team.CHO);
            return;
        }
        janggiRepository.setTurn(Team.HAN);
    }

    private Board createBoard() {
        int hanTableSetting = inputView.readTableSetting(Team.HAN);
        int choTableSetting = inputView.readTableSetting(Team.CHO);
        return createBoard(hanTableSetting, choTableSetting);
    }

    private Board createBoard(final int hanTableSetting, final int choTableSetting) {
        final var hanBoardStrategy = boardCreateStrategy.get(hanTableSetting);
        final var choBoardStrategy = boardCreateStrategy.get(choTableSetting);

        return new BoardBuilder()
            .initTeam(Team.HAN, hanBoardStrategy)
            .initTeam(Team.CHO, choBoardStrategy)
            .build();
    }

    private void checkDepartureIsMyPiece(Team team, final Board board, final Coordinate departure) {
        boolean selectsMyTeam = board.findAt(departure)
            .map(p -> p.isTeam(team))
            .orElse(true);
        if (!selectsMyTeam) {
            throw new IllegalArgumentException("같은 팀 기물만 선택할 수 있습니다.");
        }
    }
}
