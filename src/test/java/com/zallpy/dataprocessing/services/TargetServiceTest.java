package com.zallpy.dataprocessing.services;

import com.zallpy.dataprocessing.entities.Target;
import com.zallpy.dataprocessing.repositories.TargetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TargetServiceTest {

    @MockBean
    TargetRepository targetRepository;

    private TargetService targetService;

    @BeforeEach
    void setup() {
        this.targetService = new TargetService(targetRepository);
    }

    @Test
    void shouldReturnExistingRecordWhenFound() {

        var name = "John Doe";
        var target = new Target(1L, name);

        when(targetRepository.findByName(name))
        .thenReturn(Optional.of(target));

        assertThat(targetService.save(name))
        .isEqualTo(target);

        verify(targetRepository, times(0)).save(name);
    }

    @Test
    void shouldSaveWhenRecordIsNotFound() {

        var name = "John Doe";
        var target = new Target(1L, name);

        when(targetRepository.findByName(name))
        .thenReturn(Optional.empty());

        when(targetRepository.save(name))
        .thenReturn(target);

        assertThat(targetService.save(name))
        .isEqualTo(target);

        verify(targetRepository, times(1)).save(name);
    }
}