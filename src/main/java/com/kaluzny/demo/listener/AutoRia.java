package com.kaluzny.demo.listener;

import com.kaluzny.demo.domain.Automobile;
import org.springframework.jms.annotation.JmsListener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AutoRia {


    @JmsListener(destination = "AutoRia", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener1(List<Automobile> Automobiles) {
        log.info("\u001B[32m" + "AutoRia client 1: " + Automobiles + "\u001B[0m");
    }

    @JmsListener(destination = "AutoRia", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener2(List<Automobile> Automobiles) {
        log.info("\u001B[32m" + "AutoRia client 1: " + Automobiles + "\u001B[0m");
    }


}
