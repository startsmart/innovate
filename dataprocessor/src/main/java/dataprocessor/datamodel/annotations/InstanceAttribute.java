package dataprocessor.datamodel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks getter methods of field in data modal which are attributes of an data instance.
 * @author Sanjeev S. [527395]
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InstanceAttribute
{
	String name();
	DataType type() default DataType.STRING;
}
