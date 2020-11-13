package com.pritamprasad.covid_data_provider.startup_tasks;

import com.pritamprasad.covid_data_provider.security.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationArguments;

import static com.pritamprasad.covid_data_provider.security.models.UserRole.ADMIN;
import static com.pritamprasad.covid_data_provider.security.models.UserRole.USER;
import static java.util.Optional.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitialDatabaseSetupTasksTest {

    private InitialDatabaseSetupTasks initialDatabaseSetupTasks;

    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        initialDatabaseSetupTasks = new InitialDatabaseSetupTasks(roleRepository);
    }

    @Test
    public void initialDatabaseSetupTasksSuccessTest(){
        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(roleRepository.findByRoleName(USER)).thenReturn(empty());
        when(roleRepository.findByRoleName(ADMIN)).thenReturn(empty());
        try {
            initialDatabaseSetupTasks.run(arguments);
        } catch (Exception e) {
            Assertions.fail("initialDatabaseSetupTasksSuccessTest failed");
        }
    }
}
