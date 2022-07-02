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
    public boolean isValid(Object objValidation, ConstraintValidatorContext arg1) {

        boolean valid = true;
        
        try {

            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objValidation.getClass(), valueField)
                .getReadMethod()
                .invoke(objValidation);

            String description = (String) BeanUtils.getPropertyDescriptor(objValidation.getClass(), descriptionField)
                .getReadMethod()
                .invoke(objValidation);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.descriptionRequired.toLowerCase());
            } 
            return valid;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
