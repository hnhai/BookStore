package com.framgia.bookStore;

import com.framgia.bookStore.repository.impl.GenericJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = GenericJpaRepository.class, basePackages = "com.framgia.bookStore.repository")
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = "com.framgia.bookStore.entity")
@EnableTransactionManagement
@EnableJms
public class BookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

}

