package edu.vanderbilt.cqs.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.UserForm;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm form = (UserForm) target;

		User user = form.getUser();

		if (user.getId() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.email",
					"required.email", "User email required.");
		}
	}
}
