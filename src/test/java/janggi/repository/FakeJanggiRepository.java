package janggi.repository;

import janggi.domain.Janggi;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeJanggiRepository implements JanggiRepository {
    private final Map<Long, Janggi> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Janggi janggi) {
        sequence++;
        store.put(sequence, janggi);
        return sequence;
    }

    @Override
    public void update(Long id, Janggi janggi) {
        store.put(id, janggi);
    }

    @Override
    public Optional<Janggi> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Janggi> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}