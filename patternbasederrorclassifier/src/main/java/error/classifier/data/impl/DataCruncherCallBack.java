package error.classifier.data.impl;

@FunctionalInterface
public interface DataCruncherCallBack
{
	public void crunch(String fileData);
}
