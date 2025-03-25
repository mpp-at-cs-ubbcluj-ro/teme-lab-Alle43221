import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.ComputerRepairRequestRepository;
import repository.ComputerRepairedFormRepository;
import repository.file.ComputerRepairRequestFileRepository;
import repository.file.ComputerRepairedFormFileRepository;
import repository.jdbc.ComputerRepairRequestJdbcRepository;
import repository.jdbc.ComputerRepairedFormJdbcRepository;
import services.ComputerRepairServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {

    @Bean
    public Properties getProps() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config")); // Ensure the file exists in your classpath
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file: bd.config", e);
        }
        return props;
    }

    @Bean
    public ComputerRepairRequestRepository requestsRepoFile() {
        return new ComputerRepairRequestFileRepository("ComputerRequests.txt");
    }

    @Bean
    public ComputerRepairRequestRepository requestsRepoJdbc() {
        return new ComputerRepairRequestJdbcRepository(getProps());
    }

    @Bean
    public ComputerRepairedFormRepository formsRepoFile() {
        return new ComputerRepairedFormFileRepository("RepairedForms.txt", requestsRepoFile());
    }

    @Bean
    public ComputerRepairedFormRepository formsRepoJdbc() {
        return new ComputerRepairedFormJdbcRepository(getProps());
    }

    @Bean
    public ComputerRepairServices shopService() {
        return new ComputerRepairServices(requestsRepoFile(), formsRepoFile());
    }
}
