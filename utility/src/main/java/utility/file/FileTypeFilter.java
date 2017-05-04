package utility.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Sanjeev S.
 */
public class FileTypeFilter implements FilenameFilter
{

	private String acceptedExtn = null;
	/**
	 * Skip folders from filtering if false else otherwise;
	 */
	private boolean filterFolders = false;

	public FileTypeFilter()
	{
		this("");
	}

	public FileTypeFilter(String acceptedExtnParam)
	{
		acceptedExtn = acceptedExtnParam;
	}

	public FileTypeFilter(String acceptedExtnParam, boolean filterFoldersParam)
	{
		acceptedExtn = acceptedExtnParam;
		filterFolders = filterFoldersParam;
	}

	public boolean accept(File dir, String name)
	{
		if(!filterFolders)
		{
			File f = new File(dir + File.separator + name);
			if(f.isDirectory())
			{
				return true;
			}
		}
		return acceptedExtn == null || acceptedExtn.isEmpty() || name.endsWith(acceptedExtn);
	}
}
