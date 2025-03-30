package janggi.controller;

import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardBuilder;
import janggi.domain.board.maSangStrategy.MaSangMaSang;
import janggi.domain.board.maSangStrategy.MaSangSangMa;
import janggi.domain.board.maSangStrategy.MaSangStrategy;
import janggi.domain.board.maSangStrategy.SangMaMaSang;
import janggi.domain.board.maSangStrategy.SangMaSangMa;
import janggi.repository.GameRepository;
import janggi.repository.MemoryGameRepository;
import janggi.repository.Repository;
import janggi.service.GameService;
import janggi.service.PlayingTurn;
import janggi.view.BoardInitiliazeView;
import java.util.Map;

public class ApplicationConfigurer {

    private final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
        1, new MaSangSangMa(),
        2, new MaSangMaSang(),
        3, new SangMaSangMa(),
        4, new SangMaMaSang()
    );

    private final BoardInitiliazeView boardInitiliazeView;

    public ApplicationConfigurer(final BoardInitiliazeView view) {
        this.boardInitiliazeView = view;
    }

    public GameService configureGameService() {
        Repository repository = new GameRepository();
        if (repository.isConnectable()) {
            configureRemoteRepository(repository);
            return new GameService(repository);
        }

        boardInitiliazeView.printConnectionFailed();
        return new GameService(new MemoryGameRepository(createBoard()));
    }

    private void configureRemoteRepository(Repository repository) {
        final var continuePreviousGame = boardInitiliazeView.readRenewGame();
        if (continuePreviousGame) {
            boardInitiliazeView.printContinueGame();
            return;
        }

        repository.clear();
        repository.updateTurn(new PlayingTurn());

        Board board = createBoard();
        board.getPieces()
            .values()
            .forEach(repository::save);
    }

    private Board createBoard() {
        int hanTableSetting = boardInitiliazeView.readTableSetting(Team.HAN);
        int choTableSetting = boardInitiliazeView.readTableSetting(Team.CHO);
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
}
