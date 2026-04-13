package janggi.domain;

import janggi.domain.game.GameRepository;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRepository;
import janggi.domain.team.Chu;
import janggi.domain.team.Han;
import janggi.domain.team.TeamType;
import janggi.domain.turn.Turn;
import janggi.domain.turn.TurnRepository;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JanggiGameService {

    private final GameRepository gameRepository;
    private final TurnRepository turnRepository;
    private final PieceRepository pieceRepository;

    public JanggiGameService(GameRepository gameRepository, TurnRepository turnRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.turnRepository = turnRepository;
        this.pieceRepository = pieceRepository;
    }

    public JanggiGame initializeJanggiGame(Consumer<List<GameDto>> printResumeNotice, Runnable printResumeGameNotice, Supplier<String> readLine) {
        List<JanggiGame> inProgressGames = gameRepository.findInProgressGames();
        if (!inProgressGames.isEmpty()) {
            List<GameDto> inProgressGameDtos = inProgressGames.stream()
                    .map(GameDto::from)
                    .toList();
            printResumeNotice.accept(inProgressGameDtos);
            String inputResumeCommand = readLine.get();
            ResumeCommand resumeCommand = new ResumeCommand(inputResumeCommand);
            if (resumeCommand.isResume()) {
                printResumeGameNotice.run();
                String inputGameId = readLine.get();
                return loadPreviousJanggiGame(GameDto.convertToIntId(inputGameId));
            }
        }
        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        gameRepository.save(janggiGame);
        return janggiGame;
    }

    public void move(JanggiGame janggiGame, Position start, Position end) {
        Turn movedTurn = janggiGame.move(start, end);
        turnRepository.save(movedTurn, janggiGame.getId());
        janggiGame.addNewTurn(movedTurn);
        List<PieceDto> pieceDtos = getPieceDtos(movedTurn);
        pieceRepository.saveAll(pieceDtos);
    }

    public void updateGameStatusFinished(JanggiGame janggiGame) {
        gameRepository.updateGameStatusFinished(janggiGame);
    }

    private JanggiGame loadPreviousJanggiGame(Long gameId) {
        JanggiGame janggiGame = gameRepository.findById(gameId);
        TurnDto turnDto = turnRepository.findLastTurnByGameId(janggiGame.getId());

        Map<Position, Piece> allPieces = pieceRepository.findAllPiecesByTurnId(turnDto.id());

        Map<Position, Piece> chuPieces = getPiecesByTeamType(allPieces, TeamType.CHU);
        Map<Position, Piece> hanPieces = getPiecesByTeamType(allPieces, TeamType.HAN);

        Chu chu = Chu.loadLastChu(chuPieces);
        Han han = Han.loadLastHan(hanPieces);

        Board board = Board.loadPreviousBoard(chu, han);

        Turn turn = Turn.loadPreviousTurn(turnDto.id(), turnDto.currentTurnTeam(), board, turnDto.turnStatus());

        return JanggiGame.loadPreviousJanggiGame(janggiGame, turn);
    }

    private Map<Position, Piece> getPiecesByTeamType(Map<Position, Piece> allPieces, TeamType teamType) {
        return allPieces.entrySet().stream()
                .filter(entry -> entry.getValue().getTeamType() == teamType)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    private List<PieceDto> getPieceDtos(Turn movedTurn) {
        Map<Position, Piece> allPieces = movedTurn.allPieces();
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> pieceEntry : allPieces.entrySet()) {
            Position position = pieceEntry.getKey();
            Piece piece = pieceEntry.getValue();
            pieceDtos.add(PieceDto.from(movedTurn.getId(), position, piece));
        }
        return pieceDtos;
    }
}
