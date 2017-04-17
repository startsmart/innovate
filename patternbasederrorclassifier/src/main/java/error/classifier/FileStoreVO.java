package error.classifier;

/**
 * @author Sanjeev S. [527395]
 */
public class FileStoreVO
{
	private String fileStorePath;
	private String dataModelRecordPrefix;
	private String patternRecordPrefix;
	private String dataReadMarker;

	/**
	 * @return the fileStorePath
	 */
	public String getFileStorePath()
	{
		return fileStorePath;
	}

	/**
	 * @param fileStorePath the fileStorePath to set
	 */
	public void setFileStorePath(String fileStorePath)
	{
		this.fileStorePath = fileStorePath;
	}

	/**
	 * @return the dataModelRecordPrefix
	 */
	public String getDataModelRecordPrefix()
	{
		return dataModelRecordPrefix;
	}

	/**
	 * @param dataModelRecordPrefix the dataModelRecordPrefix to set
	 */
	public void setDataModelRecordPrefix(String dataModelRecordPrefix)
	{
		this.dataModelRecordPrefix = dataModelRecordPrefix;
	}

	/**
	 * @return the patternRecordPrefix
	 */
	public String getPatternRecordPrefix()
	{
		return patternRecordPrefix;
	}

	/**
	 * @param patternRecordPrefix the patternRecordPrefix to set
	 */
	public void setPatternRecordPrefix(String patternRecordPrefix)
	{
		this.patternRecordPrefix = patternRecordPrefix;
	}

	/**
	 * @return the dataReadMarker
	 */
	public String getDataReadMarker()
	{
		return dataReadMarker;
	}

	/**
	 * @param dataReadMarker the dataReadMarker to set
	 */
	public void setDataReadMarker(String dataReadMarker)
	{
		this.dataReadMarker = dataReadMarker;
	}
}
