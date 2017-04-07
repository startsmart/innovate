package dataprocessor.datamodel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks getter methods of field that is a Instance occurrence time for a data modal.
 * Any data modal can have only one Instance time field
 * @author Sanjeev S.[527395]
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InstanceTime
{
	DataType type() default DataType.CALENDAR;
}
