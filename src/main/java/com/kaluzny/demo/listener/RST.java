package com.kaluzny.demo.listener;

import com.kaluzny.demo.domain.Automobile;
import org.springframework.jms.annotation.JmsListener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RST {

    @JmsListener(destination = "RST", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener1(List<Automobile> Automobile) {
        log.info("\u001B[32m" + "RST client: " + Automobile + "\u001B[0m");
    }


}
