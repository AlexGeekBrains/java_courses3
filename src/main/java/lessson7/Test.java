package lessson7;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Test {
    /**
     * @ priority - укажите приоритет запуска теста, где 1 минимальный приоритет, 10 максимальный.
     * значение по умолчанию 1.
     */
    int priority() default 1;
}
