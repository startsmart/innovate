package error.classifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import dataprocessor.exception.DataStoreException;
import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IDataStore;
import dataprocessor.interfaces.IPattern;
import dataprocessor.interfaces.IQuery;
import dataprocessor.interfaces.IResult;

/**
 * @author Sanjeev S. [527395]
 */
public class FileDataStore implements IDataStore<FileStoreVO>
{
	private FileStoreVO storeVO;

	public FileDataStore(FileStoreVO storeVO)
	{
		this.storeVO = storeVO;
	}

	@Override
	public FileStoreVO getDataStoreDetailsVO()
	{
		return this.storeVO;
	}

	@Override
	public void persistModel(IDataModel model) throws DataStoreException
	{

		File record = new File(this.storeVO.getFileStorePath() + this.storeVO.getDataModelRecordPrefix() + System.currentTimeMillis());
		store(record, model);
	}

	@Override
	public void persistPattern(IPattern pattern) throws DataStoreException
	{
		File record = new File(this.storeVO.getFileStorePath() + this.storeVO.getPatternRecordPrefix() + System.currentTimeMillis());
		store(record, pattern);
	}

	@Override
	public IResult<?> get(IQuery<?> query) throws DataStoreException
	{
		//TODO
		return null;
	}

	private void store(File f, Serializable data) throws DataStoreException
	{
		File p = f.getParentFile();
		if (!p.exists())
		{
			p.mkdirs();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)))
		{
			oos.writeObject(data);
		}
		catch(Exception e)
		{
			throw new DataStoreException(e);
		}
	}
}
