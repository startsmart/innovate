package utility.seperatedvalue;

/**
 * @author Sanjeev S.
 */
public class CommaSeparatedValue extends SeparatedValue
{
	private static final String PART_FILE_NAME = "_part.csv";

	public CommaSeparatedValue()
	{
		super(",", "\n");
	}

	public String getCSV()
	{
		return getValue();
	}

	@Override
	public String getPartFileName()
	{
		return PART_FILE_NAME;
	}
}
