package dataprocessor.datamodel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks getter methods of field that forms context for a data model.
 * @author Sanjeev S.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Context
{
	String name();
	DataType type() default DataType.STRING;
}
