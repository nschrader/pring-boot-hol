package fr.insalyon.tc.springboothol.Controller;


import fr.insalyon.tc.springboothol.Entity.ShortURL;
import fr.insalyon.tc.springboothol.Misc.ShortCodeNotFoundException;
import fr.insalyon.tc.springboothol.Service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
public class ShortURLController {

    @Autowired
    private ShortURLService shortURLService;


    @PostMapping("/")
    public ResponseEntity shortenURL(@RequestParam URI uri) {
        ShortURL shortURL = shortURLService.shortenURL(uri);
        URI shortenedURL = fromMethodCall(on(this.getClass()).redirectShortCode(shortURL.getShortCode())).build().toUri();
        return ResponseEntity.created(shortenedURL).build();
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity redirectShortCode(@PathVariable String shortCode) {
        try {
            ShortURL shortURL = shortURLService.expandShortCode(shortCode);
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(shortURL.getUri()).build();
        } catch (ShortCodeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{shortCode}/info")
    @ResponseBody
    public ShortURL infoShortCode(@PathVariable String shortCode) throws ShortCodeNotFoundException {
        return shortURLService.expandShortCode(shortCode);
    }

    @ExceptionHandler(ShortCodeNotFoundException.class)
    public ResponseEntity handleException() {
        return ResponseEntity.notFound().build();
    }

}