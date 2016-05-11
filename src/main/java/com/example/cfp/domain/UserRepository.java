package com.example.cfp.domain;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByTwitter(String twitter);

	User findByGithub(String github);

	Collection<User> findByName(String lastName);

}
