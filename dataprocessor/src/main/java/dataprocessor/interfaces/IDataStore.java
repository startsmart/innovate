package dataprocessor.interfaces;

public interface IDataStore<T>
{
	public T getDataStoreDetailsVO();
	public void persistModel(IDataModel model);
	public void persistPattern(IPattern pattern);
}
