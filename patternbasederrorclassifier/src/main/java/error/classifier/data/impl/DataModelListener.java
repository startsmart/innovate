/**
 *
 */
package error.classifier.data.impl;

import java.util.List;

import loggerapi.Logger;
import dataprocessor.exception.DataStoreException;
import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IDataStore;
import error.classifier.FileStoreVO;
import error.classifier.IDataListener;

/**
 * @author Sanjeev S.
 */
public class DataModelListener implements IDataListener<List<IDataModel>>
{
	private IDataStore<FileStoreVO> dataStore;

	public DataModelListener(IDataStore<FileStoreVO> dataStore)
	{
		this.dataStore = dataStore;
	}

	@Override
	public void listen(List<IDataModel> dataList)
	{
		try
		{
			for(IDataModel data : dataList)
			{
				this.dataStore.persistModel(data);
			}
		}
		catch (DataStoreException e)
		{
			Logger.getLogger().error("Unable to persist Datamodel", e);
		}
	}

}
