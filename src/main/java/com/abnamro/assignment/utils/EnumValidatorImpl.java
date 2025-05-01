package com.abnamro.assignment.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		try {
			OrderStatus.valueOf(value.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
