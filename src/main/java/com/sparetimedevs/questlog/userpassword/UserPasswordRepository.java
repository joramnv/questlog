package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.user.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserPasswordRepository extends CrudRepository<UserPassword, Long> {

	Optional<UserPassword> findByUser(@Param("USER") User user);

	Optional<UserPassword> findByUserEmailAddress(@Param("USER_EMAIL_ADDRESS") String userEmailAddress);

}
