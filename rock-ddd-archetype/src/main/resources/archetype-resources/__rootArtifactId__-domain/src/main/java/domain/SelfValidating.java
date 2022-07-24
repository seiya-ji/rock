#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain;

import javax.validation.*;
import java.util.Set;

/**
 * 模型验证器
 * @author 太乙
 * @version : SelfValidating.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
public abstract class SelfValidating<T> {
    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
