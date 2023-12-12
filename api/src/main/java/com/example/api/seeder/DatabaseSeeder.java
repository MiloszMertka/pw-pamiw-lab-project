package com.example.api.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final EngineSeeder engineSeeder;
    private final EquipmentOptionSeeder equipmentOptionSeeder;
    private final CarSeeder carSeeder;
    private final UserSeeder userSeeder;

    @Override
    public void run(String... args) {
        engineSeeder.seed(15);
        equipmentOptionSeeder.seed(5);
        carSeeder.seed(15);
        userSeeder.seed(1);
    }

}
