package com.tutorcenter.common;

import org.springframework.security.core.context.SecurityContextHolder;

import com.tutorcenter.model.User;

public class Common {
    public static int getCurrentUserId() {
		if (SecurityContextHolder.getContext() == null) return 0;
		if (SecurityContextHolder.getContext().getAuthentication() == null) return 0;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof User) {
			User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (u == null)
				return 0;
			return u.getId();
		}
		return 0;
	}
}
