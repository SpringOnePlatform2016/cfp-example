package com.example.cfp.domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(FlywayAutoConfiguration.class)
public class UserTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findByGithub() {
		User user = new User("brian@example.com", "Brian Clozel");
		user.setGithub("bclozel");
		this.userRepository.save(user);

		User brian = this.userRepository.findByGithub("bclozel");
		assertThat(brian).isNotNull();
		assertThat(brian.getName()).isEqualTo("Brian Clozel");
	}

	@Test
	public void findByTwitter() {
		User user = new User("juergen@example.com", "Jürgen Höller");
		user.setTwitter("springjuergen");
		this.userRepository.save(user);

		User juergen = this.userRepository.findByTwitter("springjuergen");
		assertThat(juergen).isNotNull();
		assertThat(juergen.getName()).isEqualTo("Jürgen Höller");
	}

}
