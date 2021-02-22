package com.zallpy.dataprocessing.services;

import com.zallpy.dataprocessing.entities.Insult;
import com.zallpy.dataprocessing.repositories.InsultRepository;
import org.springframework.stereotype.Service;

@Service
public class InsultService {

    private final InsultRepository insultRepository;

    public InsultService(InsultRepository insultRepository) {
        this.insultRepository = insultRepository;
    }

    public Insult save(String insult) {

        return insultRepository
        .findByName(insult)
        .orElseGet(() -> insultRepository.save(insult));
    }
}
