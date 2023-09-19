package com.kaluzny.demo.service;

import com.kaluzny.demo.domain.Automobile;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AutomobileService {

    List<Automobile> findAutomobileColor(String color);

    Automobile saveAutomobile(Automobile automobile);

    Collection<Automobile> getAllAutomobiles();

    Automobile getAutomobileById(Long id);

    List<Automobile> findAutomobileByName(String name);

    Automobile refreshAutomobile(Long id,Automobile automobile);

    void removeAutomobileById(Long id);

    void removeAllAutomobiles();

    List<Automobile> findAutomobileByNameAndColor(String name,String color);






}
