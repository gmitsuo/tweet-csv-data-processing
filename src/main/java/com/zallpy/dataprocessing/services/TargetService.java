package com.zallpy.dataprocessing.services;

import com.zallpy.dataprocessing.entities.Target;
import com.zallpy.dataprocessing.repositories.TargetRepository;
import org.springframework.stereotype.Service;

@Service
public class TargetService {

    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public Target save(String target) {

        return targetRepository
        .findByName(target)
        .orElseGet(() -> targetRepository.save(target));
    }
}
