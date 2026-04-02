package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidarJurosBoletoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidarJurosBoleto {

    String message() default "Dados de juros do boleto invalidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
