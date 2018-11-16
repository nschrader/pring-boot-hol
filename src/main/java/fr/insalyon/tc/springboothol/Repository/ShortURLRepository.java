package fr.insalyon.tc.springboothol.Repository;

import fr.insalyon.tc.springboothol.Entity.ShortURL;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortURLRepository extends CrudRepository<ShortURL, String> {

    Optional<ShortURL> findByShortCode(String shortCode);

}