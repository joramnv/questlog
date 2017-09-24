package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserPasswordRepository extends CrudRepository<UserPassword, Long> {

	UserPassword findByUser(@Param("user") User user);

}
