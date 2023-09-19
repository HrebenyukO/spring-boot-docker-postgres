package com.kaluzny.demo.service;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.exception.ThereIsNoSuchAutoException;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class AutomobileServiceBean implements AutomobileService {

    private final AutomobileRepository repository;
    private final JmsTemplate jmsTemplate;

    @Override
    public Automobile saveAutomobile(Automobile automobile) {
        Automobile savedAuto = null;
        try (Connection connection = Objects.requireNonNull
                (jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("AutoTopic");
            savedAuto = repository.save(automobile);
            jmsTemplate.convertAndSend(autoTopic, savedAuto);
            return automobile;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Automobile> getAllAutomobiles() {
        log.info("\u001B[32m" + "getAllAutomobiles() begin: " + "\u001B[0m");
        return repository.findAllExists()
                .stream()
                .toList();
    }

    @Override
    public Automobile getAutomobileById(Long id) {
        log.info("\u001B[32m" + "getAutomobileById() begin: " + "\u001B[0m");
        return repository.findById(id)
                .orElseThrow(ThereIsNoSuchAutoException::new);
    }

    @Override
    public List<Automobile> findAutomobileByName(String name) {
        List<Automobile> findAutoByName = null;
        try (Connection connection = Objects.requireNonNull
                (jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("RST");
            findAutoByName = repository.findByName(name);
            jmsTemplate.convertAndSend(autoTopic, findAutoByName);
            return findAutoByName;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Automobile refreshAutomobile(Long id, Automobile automobile) {
        Automobile updatedAuto = repository.updateAutomobile(automobile.getName(), automobile.getColor());
        return updatedAuto;
    }

    @Override
    public void removeAutomobileById(Long id) {
        try (Connection connection = Objects.requireNonNull
                (jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("RST");
            repository.removedAuto(id);
            jmsTemplate.convertAndSend(autoTopic, id);

        } catch (JMSException e) {
            e.printStackTrace();

        }
    }
    @Override
    public void removeAllAutomobiles() {
        repository.deleteAll();
    }
    @Override
    public List<Automobile> findAutomobileColor(String color) {
        List<Automobile> automobilesByColor = null;
        try (Connection connection = Objects.requireNonNull
                (jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("AutoRia");
            automobilesByColor = repository.findByColor(color);
            jmsTemplate.convertAndSend(autoTopic, automobilesByColor);
            return automobilesByColor;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
   public List<Automobile> findAutomobileByNameAndColor( String name,String color) {
        List<Automobile> automobilesByNameAndColor = null;
        try (Connection connection = Objects.requireNonNull
                (jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("AutoRia");
            automobilesByNameAndColor = repository.findByNameAndColor(name,color);
            Topic autoTopic2 = connection.createSession().createTopic("RST");
            jmsTemplate.convertAndSend(autoTopic, automobilesByNameAndColor);
            jmsTemplate.convertAndSend(autoTopic2, automobilesByNameAndColor);
            return automobilesByNameAndColor;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }
}
