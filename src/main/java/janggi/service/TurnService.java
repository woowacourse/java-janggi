package janggi.service;

import janggi.domain.Turn;
import janggi.repository.TurnRepository;

public class TurnService {

    private final TurnRepository turnRepository;

    public TurnService(final TurnRepository turnRepository) {
        this.turnRepository = turnRepository;
    }

    public Long add(final Turn turn) {
        return turnRepository.add(turn);
    }

    public Turn find() {
        return turnRepository.find();
    }

    public void updateTurn(final Turn turn) {
        turnRepository.update(turn);
    }

    public void delete() {
        turnRepository.delete();
    }


}
