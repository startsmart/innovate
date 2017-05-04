package error.classifier;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface IDataListener<T>
{
	public void listen(T data);
}
