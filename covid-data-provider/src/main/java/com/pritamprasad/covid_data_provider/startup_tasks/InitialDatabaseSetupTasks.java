package com.pritamprasad.covid_data_provider.startup_tasks;

import com.pritamprasad.covid_data_provider.security.models.RoleEntity;
import com.pritamprasad.covid_data_provider.security.models.UserRole;
import com.pritamprasad.covid_data_provider.security.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.pritamprasad.covid_data_provider.security.models.UserRole.ADMIN;
import static com.pritamprasad.covid_data_provider.security.models.UserRole.USER;

@Component
public class InitialDatabaseSetupTasks implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(InitialDatabaseSetupTasks.class);

    private RoleRepository roleRepository;

    @Autowired
    public InitialDatabaseSetupTasks(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createRoleIfnotExists(USER);
        createRoleIfnotExists(ADMIN);
    }

    private void createRoleIfnotExists(final UserRole userRole) {
        final Optional<RoleEntity> role = roleRepository.findByRoleName(userRole);
        if (!role.isPresent()) {
            logger.debug("{} role don't exist, creating role...", userRole.name());
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleName(userRole);
            roleRepository.save(roleEntity);
        }
    }
}
