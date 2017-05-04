package error.classifier.data.impl;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface DataCruncherCallBack
{
	public void crunch(String fileData);
}
