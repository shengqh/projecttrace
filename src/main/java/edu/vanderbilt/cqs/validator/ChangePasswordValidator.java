package edu.vanderbilt.cqs.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.form.ChangePasswordForm;

@Component
public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm form = (ChangePasswordForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword",
				"required.currentpassword", "Your current password required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
				"required.newpassword", "New password required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmpassword", "Confirm password required.");

		String oldPassword = Utils.md5(form.getCurrentPassword());

		if (!oldPassword.equals(form.getOldPassword())) {
			errors.rejectValue("currentPassword", "invalid.currentpassword",
					"Current password doesn't match to your password.");
		}

		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "invalid.confirmpassword",
					"Confirm password doesn't equals to new password.");
		}
	}
}
