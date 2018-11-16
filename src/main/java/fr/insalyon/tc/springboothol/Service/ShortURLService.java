package fr.insalyon.tc.springboothol.Service;

import fr.insalyon.tc.springboothol.Entity.ShortURL;
import fr.insalyon.tc.springboothol.Misc.ShortCodeNotFoundException;
import fr.insalyon.tc.springboothol.Repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShortURLService {

    @Autowired
    private ShortURLRepository shortURLRepository;

    public ShortURL shortenURL(URI uri) {
        ShortURL shortUrl = new ShortURL(uri, UUID.randomUUID().toString().substring(0, 4));
        shortURLRepository.save(shortUrl);
        return shortUrl;
    }

    public ShortURL expandShortCode(String shortCode) throws ShortCodeNotFoundException {
        Optional<ShortURL> shortURL = shortURLRepository.findByShortCode(shortCode);
        return shortURL.orElseThrow(() -> new ShortCodeNotFoundException(shortCode));
    }

}