package web.hair.api_api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("web.hair.api_api.domain")
@EnableJpaRepositories("web.hair.api_api.repos")
@EnableTransactionManagement
public class DomainConfig {
}
