package biz.advance_it_group.taxiride_backend.authentification.events;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.context.ApplicationEvent;

public class OnUserAccountChangeEvent extends ApplicationEvent {

	private Users user;
	private String action;
	private String actionStatus;

	public OnUserAccountChangeEvent(Users user, String action, String actionStatus) {
		super(user);
		this.user = user;
		this.action = action;
		this.actionStatus = actionStatus;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
}
