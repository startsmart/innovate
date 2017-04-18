package error.classifier.data.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

import loggerapi.Logger;

/**
 * @author 527395
 */
public class TextDataCruncher implements Runnable
{
	private String name;
	private String path;
	private String fileTypeExtension;
	private DataCruncherCallBack callback;
	private File readDirectory;

	public TextDataCruncher(String name, String path, String fileTypeExtension,
			DataCruncherCallBack callback) {
		super();
		this.name = name;
		this.path = path;
		this.fileTypeExtension = fileTypeExtension;
		this.callback = callback;
		readDirectory = new File(this.path + File.separator + "read" + File.separator);
		if(!readDirectory.exists())
		{
			try
			{
				readDirectory.mkdirs();
			}
			catch (Exception e)
			{
				Logger.getLogger().error("Unable to create read directory.", e);
			}
		}
	}



	@Override
	public void run()
	{
		try
		{
			Logger.getLogger().info("Starting " + this.name);
			crunch();
			Logger.getLogger().info("" + this.name + " completed Execution.");
		}
		catch (Exception e)
		{
			Logger.getLogger().error("Exception occured in  " + this.name, e);
		}
	}



	private void crunch()
	{

		String trueVal = "true";
		File srcFodler = new File(this.path);
		if(!srcFodler.exists())
		{
			srcFodler.mkdirs();
		}
		while(Boolean.parseBoolean(trueVal))
		{
			pause();
			File[] allFiles = srcFodler.listFiles((dir,fname) -> fname.toUpperCase().endsWith(fileTypeExtension.toUpperCase()));
			if(allFiles != null && allFiles.length > 0)
			{
				for(File f : allFiles)
				{
					this.callback.crunch(readFile(f));
					Logger.getLogger().info("File listner " + this.name + " Read Data instance - " + f.getAbsolutePath() + ". Moving it read repo");
					moveToRead(f);
				}
			}
		}
	}


	protected String readFile(File f)
	{
		try
		{
			return Files.readAllLines(f.toPath()).stream().collect(Collectors.joining(System.lineSeparator()));
		}
		catch (IOException e)
		{
			Logger.getLogger().error("Unable to read file " + f.getAbsolutePath(), e);
		}
		return null;
	}

	protected void moveToRead(File f)
	{
		try
		{
			File dest = new File(readDirectory.getAbsolutePath() + File.separator + f.getName());
			Files.move(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e)
		{
			Logger.getLogger().error("Unable to move read files.", e);
		}
	}

	private void pause()
	{
		try
		{
			Logger.getLogger().info("File listner " + this.name + " is waiting for 10 Sec.");
			Thread.sleep(10000L);
			Logger.getLogger().info("File listner " + this.name + " wait completed.");
		}
		catch (InterruptedException e)
		{
			Logger.getLogger().error("Thread Interrupted", e);
		}

	}

}
