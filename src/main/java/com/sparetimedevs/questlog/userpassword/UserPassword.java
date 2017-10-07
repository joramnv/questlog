package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USER_PASSWORD")
public class UserPassword implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "USER_EMAIL_ADDRESS", referencedColumnName = "EMAIL_ADDRESS", nullable = false)
	private User user;

	@Column(nullable = false)
	private String password;

	public UserPassword() {
	}

	public UserPassword(User user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
