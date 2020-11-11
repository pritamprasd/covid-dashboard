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
        final Optional<RoleEntity> user = roleRepository.findByRoleName(UserRole.USER);
        if(!user.isPresent()){
            logger.debug("USER role don't exist, creating USER role");
            RoleEntity userRoleEntity = new RoleEntity();
            userRoleEntity.setRoleName(UserRole.USER);
            roleRepository.save(userRoleEntity);
        }
        final Optional<RoleEntity> admin = roleRepository.findByRoleName(UserRole.ADMIN);
        if(!admin.isPresent()){
            logger.debug("ADMIN role don't exist, creating ADMIN role");
            RoleEntity adminRoleEntity = new RoleEntity();
            adminRoleEntity.setRoleName(UserRole.ADMIN);
            roleRepository.save(adminRoleEntity);
        }
    }
}
