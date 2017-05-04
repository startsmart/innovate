package dataprocessor.interfaces;

import dataprocessor.exception.DataStoreException;

/**
 * @author Sanjeev S.
 * @param <T> - Type of the DataStoreDetailsVO
 */
public interface IDataStore<T>
{
	public T getDataStoreDetailsVO();
	public void persistModel(IDataModel model) throws DataStoreException;
	public void persistPattern(IPattern pattern) throws DataStoreException;
	public IResult<?> get(IQuery<?> query)throws DataStoreException;
}
