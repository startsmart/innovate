package utility.seperatedvalue;

/**
 * @author Sanjeev S.
 */
public class TabSeparatedValue extends SeparatedValue
{
	private static final String PART_FILE_NAME = "_part.tsv";

	public TabSeparatedValue()
	{
		super("\t", "\n");
	}

	public String getTSV()
	{
		return getValue();
	}

	@Override
	public String getPartFileName()
	{
		return PART_FILE_NAME;
	}
}
