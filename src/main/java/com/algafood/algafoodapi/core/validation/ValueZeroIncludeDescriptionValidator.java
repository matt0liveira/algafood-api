package com.algafood.algafoodapi.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object> {

	private String valueField;
	private String descriptionField;
	private String descriptionRequired;
	
	@Override
	public void initialize(ValueZeroIncludeDescription constraint) {
		this.valueField = constraint.valueField();
		this.descriptionField = constraint.descriptionField();
		this.descriptionRequired = constraint.descriptionRequired();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valueField)
					.getReadMethod().invoke(objetoValidacao);
			
			String description = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descriptionField)
					.getReadMethod().invoke(objetoValidacao);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && description != null) {
				valido = description.toLowerCase().contains(this.descriptionRequired.toLowerCase());
			}
			
			return valido;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}