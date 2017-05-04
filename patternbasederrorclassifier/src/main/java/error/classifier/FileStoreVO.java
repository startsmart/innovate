package error.classifier;

/**
 * @author Sanjeev S.
 */
public class FileStoreVO
{
	private String modelStorePath;
	private String patternStorePath;
	private String dataModelRecordPrefix;
	private String patternRecordPrefix;
	private String dataReadMarker;
	private String dataModelExtension;
	private String patternExtension;

	/**
	 * @return the fileStorePath
	 */
	public String getModelStorePath()
	{
		return modelStorePath;
	}

	/**
	 * @param fileStorePath the fileStorePath to set
	 */
	public void setModelStorePath(String modelStorePath)
	{
		this.modelStorePath = modelStorePath;
	}



	/**
	 * @return the patternStorePath
	 */
	public String getPatternStorePath()
	{
		return patternStorePath;
	}

	/**
	 * @param patternStorePath the patternStorePath to set
	 */
	public void setPatternStorePath(String patternStorePath)
	{
		this.patternStorePath = patternStorePath;
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

	/**
	 * @return the dataModelExtension
	 */
	public String getDataModelExtension()
	{
		return dataModelExtension;
	}

	/**
	 * @param dataModelExtension the dataModelExtension to set
	 */
	public void setDataModelExtension(String dataModelExtension)
	{
		this.dataModelExtension = dataModelExtension;
	}

	/**
	 * @return the patternExtension
	 */
	public String getPatternExtension()
	{
		return patternExtension;
	}

	/**
	 * @param patternExtension the patternExtension to set
	 */
	public void setPatternExtension(String patternExtension)
	{
		this.patternExtension = patternExtension;
	}


}
