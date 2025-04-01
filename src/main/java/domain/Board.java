package domain;

import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Map;

public record Board(
        Map<Player, Pieces> gamePlayers
) {

    public Board {
        validatePlayerSize(gamePlayers);
    }

    public Piece moveAndCapture(final Player current, final Position start, final Position target) {
        Pieces player = gamePlayers.get(current);
        Pieces opponent = getOppositePieces(current);

        List<Position> paths = player.getPiecePaths(start, target);

        validatePlayerPieceCapture(target, player);
        MoveInfos moveInfos = createMoveInfos(paths);

        Piece moved = player.movePiece(start, target, moveInfos);
        PieceCategory removed = opponent.removePieceIfExists(target);
        current.increaseScore(removed.getScore());
        return moved;
    }

    public boolean isGameFinished() {
        long kingCount = gamePlayers.values().stream()
                .filter(Pieces::existKing)
                .count();
        return kingCount != 2;
    }

    public GameResult getGameResult() {
        return new GameResult(gamePlayers);
    }

    private void validatePlayerSize(final Map<Player, Pieces> gamePlayers) {
        if (gamePlayers.size() != 2) {
            throw new IllegalArgumentException("게임 플레이어는 두 명이어야 합니다.");
        }
    }

    private Pieces getOppositePieces(final Player current) {
        Player opposite = gamePlayers.keySet()
                .stream()
                .filter(player -> !player.equals(current))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 상대 플레이어가 없습니다."));
        return gamePlayers.get(opposite);
    }

    private void validatePlayerPieceCapture(final Position target, final Pieces current) {
        if (current.existByPosition(target)) {
            throw new IllegalArgumentException("도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    private MoveInfos createMoveInfos(final List<Position> paths) {
        List<MoveInfo> moveInfos = paths.stream()
                .map(path -> new MoveInfo(path, getPieceCategoryAtPosition(path)))
                .toList();
        return new MoveInfos(moveInfos);
    }

    private PieceCategory getPieceCategoryAtPosition(final Position position) {
        return gamePlayers.values().stream()
                .filter(pieces -> pieces.existByPosition(position))
                .map(pieces -> pieces.getCategoryAtPosition(position))
                .findFirst()
                .orElse(PieceCategory.NONE);
    }

    public Player getHanPlayer() {
        return gamePlayers.keySet()
                .stream()
                .filter(player -> player.getTeam() == Team.HAN)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 한 플레이어가 없습니다."));
    }

    public Player getChoPlayer() {
        return gamePlayers.keySet()
                .stream()
                .filter(player -> player.getTeam() == Team.CHO)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 초 플레이어가 없습니다."));
    }
}
