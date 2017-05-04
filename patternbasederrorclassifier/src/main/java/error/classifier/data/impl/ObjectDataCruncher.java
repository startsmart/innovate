package error.classifier.data.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import loggerapi.Logger;

/**
 * @author Sanjeev S.
 */
public class ObjectDataCruncher extends TextDataCruncher {

	public ObjectDataCruncher(String name, String path,
			String fileTypeExtension, DataCruncherCallBack callback)
	{
		super(name, path, fileTypeExtension, callback);
	}

	@Override
	protected String readFile(File f)
	{
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
		{
			return String.valueOf(ois.readObject());
		}
		catch (Exception e)
		{
			Logger.getLogger().error("Unable to move read files.", e);
		}
		return null;
	}
}
