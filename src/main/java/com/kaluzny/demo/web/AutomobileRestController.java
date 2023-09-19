package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.exception.AutoWasDeletedException;
import com.kaluzny.demo.exception.ThereIsNoSuchAutoException;
import com.kaluzny.demo.service.AutomobileServiceBean;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import jakarta.jms.Topic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AutomobileRestController  {

   // private final AutomobileRepository repository;
    private final AutomobileServiceBean automobileServiceBean;
    public static double getTiming(Instant start, Instant end) {
        return Duration.between(start, end).toMillis();
    }

  /*  @Transactional
    @PostConstruct
    public void init() {
        repository.save(new Automobile(1L, "Ford", "Green", LocalDateTime.now(), LocalDateTime.now(), true, false));
    }*/

    @PostMapping("/automobiles")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Automobile saveAutomobile(@Valid @RequestBody Automobile automobile) {
        return automobileServiceBean.saveAutomobile(automobile);
    }

    @GetMapping("/automobiles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSON')")
    public Collection<Automobile> getAllAutomobiles() {
        return automobileServiceBean.getAllAutomobiles();
    }

    @GetMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSON')")
    public Automobile getAutomobileById(@PathVariable Long id) {
        return automobileServiceBean.getAutomobileById(id);
    }

    @GetMapping(value = "/automobiles", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<Automobile> findAutomobileByName(@RequestParam(value = "name") String name) {
        return automobileServiceBean.findAutomobileByName(name);
    }

    @PreAuthorize("hasRole('CREATOR')")
    @PutMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Automobile refreshAutomobile(@PathVariable Long id, @RequestBody Automobile automobile) {
        return automobileServiceBean.refreshAutomobile(id, automobile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAutomobileById(@PathVariable Long id ) {
        automobileServiceBean.removeAutomobileById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/automobiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAutomobiles() {
        automobileServiceBean.removeAllAutomobiles();
    }

    @GetMapping(value = "/automobiles", params = {"color"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<Automobile> findAutomobileByColor(@RequestParam(value = "color") String color) {
        return automobileServiceBean.findAutomobileColor(color);
    }

    @GetMapping(value = "/automobiles", params = {"name", "color"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<Automobile> findAutomobileByNameAndColor(
            @RequestParam(value = "name") String name, @RequestParam(value = "color") String color) {
        return automobileServiceBean.findAutomobileByNameAndColor(name, color);
    }

}
