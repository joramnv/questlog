package com.sparetimedevs.questlog.quest;

import com.sparetimedevs.questlog.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Quest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "USER_EMAIL_ADDRESS", referencedColumnName = "EMAIL_ADDRESS", nullable = false)
	private User user;

	private String name;
	private String description;
	private long achievementPoint;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAchievementPoint() {
		return achievementPoint;
	}

	public void setAchievementPoint(long achievementPoint) {
		this.achievementPoint = achievementPoint;
	}
}
