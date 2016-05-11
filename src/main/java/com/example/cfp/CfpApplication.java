package com.example.cfp;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(CfpProperties.class)
@EnableCaching
public class CfpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfpApplication.class, args);
	}

	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
		return cm -> {
			cm.createCache("github.commits", initConfiguration(Duration.TEN_MINUTES));
			cm.createCache("github.polishCommit", initConfiguration(Duration.ONE_HOUR));
			cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
		};
	}

	private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
		return new MutableConfiguration<>()
				.setStoreByValue(false)
				.setStatisticsEnabled(true)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
	}

}
