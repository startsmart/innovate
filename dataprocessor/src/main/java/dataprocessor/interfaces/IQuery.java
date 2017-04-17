package dataprocessor.interfaces;

/**
 * @author Sanjeev S. [527395]
 */
@FunctionalInterface
public interface IQuery<T>
{
	public T getQuery();
}
