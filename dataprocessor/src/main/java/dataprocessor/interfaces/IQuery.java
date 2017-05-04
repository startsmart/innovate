package dataprocessor.interfaces;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface IQuery<T>
{
	public T getQuery();
}
