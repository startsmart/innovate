package error.classifier;

/**
 * @author 527395
 */
@FunctionalInterface
public interface IDataListener<T>
{
	public void listen(T data);
}
