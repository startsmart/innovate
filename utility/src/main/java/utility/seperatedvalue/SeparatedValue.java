package utility.seperatedvalue;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sanjeev S.
 */
public class SeparatedValue
{
	private static final String PART_FILE_NAME = "_part.txt";

	private StringBuilder contentBuilder;
	private StringBuilder partContent;
	private int partCount = 0;
	private List<List<Object>> dataStore;
	private List<Object> headerRow;
	private String valueSeperator;
	private String lineSeperator;

	public SeparatedValue(String valueSeperatorParam, String lineSeperatorParam)
	{
		dataStore = new ArrayList<List<Object>>();
		contentBuilder = new StringBuilder();
		partContent =  new StringBuilder();
		valueSeperator = valueSeperatorParam;
		lineSeperator =  lineSeperatorParam;
	}

	public synchronized void addEmptyLine()
	{
		append(lineSeperator);
		dataStore.add(new ArrayList<Object>());
	}

	public synchronized void addHeader(List<Object> data)
	{
		if(data != null)
		{
			StringBuilder content = new StringBuilder();
			boolean lineStart = true;
			headerRow = new ArrayList<Object>();
			for(Object o : data)
			{
				if(lineStart)
				{
					lineStart = false;
				}
				else
				{
					content.append(valueSeperator);
				}
				content.append(o);
				headerRow.add(o);
			}
			content.append(lineSeperator);
			String prevContent = contentBuilder.toString();
			if(prevContent.isEmpty())
			{
				append(content);
			}
			else
			{
				contentBuilder = content.append(prevContent);
			}
		}
	}
	public synchronized void addHeader(Object... data)
	{
		if(data != null)
		{
			addHeader(Arrays.asList(data));
		}
	}

	public synchronized void addLine(List<Object> data)
	{

		if(data != null)
		{
			boolean lineStart = true;
			List<Object> colValue = new ArrayList<Object>();
			for(Object o : data)
			{
				if(lineStart)
				{
					lineStart = false;
				}
				else
				{
					append(valueSeperator);
				}
				append(o);
				colValue.add(o);
			}
			append(lineSeperator);
			dataStore.add(colValue);
		}

	}

	public synchronized void addLine(Object... data)
	{
		if(data != null)
		{
			addLine(Arrays.asList(data));
		}
	}

	private void append(Object o)
	{
		contentBuilder.append(o);
		partContent.append(o);
	}

	public List<Object> getHeaderRow()
	{
		return headerRow;
	}

	public boolean hasHeader()
	{
		return headerRow != null;
	}

	public List<Object> getRowValue(int index)
	{
		return dataStore.get(index);
	}

	public Object getCellValue(int rowIndex, int colIndex)
	{
		return dataStore.get(rowIndex).get(colIndex);
	}

	public String getValue()
	{
		return contentBuilder.toString();
	}

	public String getPartFileName()
	{
		return PART_FILE_NAME;
	}

	@Override
	public String toString() {
		return getValue();
	}

	public synchronized void flush(String filePath, String name_prefix) throws Exception
	{
		partCount++;
		Files.write(new File(filePath + File.separator + name_prefix + partCount + getPartFileName()).toPath(), partContent.toString().getBytes());
		partContent =  new StringBuilder();
	}



}
