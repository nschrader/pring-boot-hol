package fr.insalyon.tc.springboothol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.UUID;

@Component
public class RepositorySample implements ApplicationRunner {

    @Autowired
    private ShortURLRepository shortURLRepository;

    private final Logger logger = LoggerFactory.getLogger(RepositorySample.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] urls = {"google.com", "yahoo.com", "ebay.com", "amazon.com"};
        for (String u: urls) {
            ShortURL shortUrl = new ShortURL(new URI(u), UUID.randomUUID().toString().substring(0, 4));
            shortURLRepository.save(shortUrl);
        }

        logger.info(shortURLRepository.findAll().toString());
    }

}