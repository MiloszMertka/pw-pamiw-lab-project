package com.example.api.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@Transactional
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private static final String SEED_FLAG = "--seed";
    private final EngineSeeder engineSeeder;
    private final EquipmentOptionSeeder equipmentOptionSeeder;
    private final CarSeeder carSeeder;
    private final UserSeeder userSeeder;

    @Override
    public void run(String... args) {
        if (Arrays.asList(args).contains(SEED_FLAG)) {
            engineSeeder.seed(10);
            equipmentOptionSeeder.seed(5);
            carSeeder.seed(10);
            userSeeder.seed(1);
        }
    }

}
