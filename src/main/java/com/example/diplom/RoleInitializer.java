package com.example.diplom;
import com.example.diplom.model.Role;
import com.example.diplom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByName("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
