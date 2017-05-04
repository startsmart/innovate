/**
 *
 */
package error.classifier.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import loggerapi.Logger;
import loggerapi.ThreadTimeConsoleLogger;
import dataprocessor.DataProcessor;
import dataprocessor.exception.DataStoreException;
import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IDataStore;
import error.classifier.FileDataStore;
import error.classifier.FileStoreVO;
import error.classifier.IDataListener;


/**
 * @author Sanjeev S.
 */
public class App
{

	private List<String> messages;
	private Random random;
	JFrame frmOpt;

	public App()
	{
		if (frmOpt == null) {
	        frmOpt = new JFrame();
	    }
	    frmOpt.setVisible(false);
	    frmOpt.setLocation(500, 350);
	    frmOpt.setAlwaysOnTop(true);
		random = new Random(50L);
		messages = new ArrayList<>();
		messages.add("PATTERN_COUNT_ERROR_DETECTED\ninstitutionId=4 customerId=1201812623 errorCode=102 count=10\nMessage=Customer 1201812623 of institution 4 resulted in error code 102 in last 10 attempts");
		messages.add("PATTERN_TIME_INTERVAL_ERROR_DETECTED\ninstitutionId=1619 customerId=1211516623 errorCode=101 interval=5 Minutes\nMessage=Customer 1211516623 of institution 1619 resulted in error code 101 approximately every 5 minutes");
		messages.add("PATTERN_CUSTOMER_HEALTH_DETECTED\ninstitutionId=23284  customerId=75016969996 errorCode=0 interval=2 days\nMessage=Customer 75016969996 of institution 23284  resulted in error code 0 for last 2 days");
		messages.add("PATTERN_TIME_INTERVAL_ERROR_DETECTED\ninstitutionId=9161 customerId=75006670562 errorCode=106 interval=3 Minutes\nMessage=Customer 75006670562 of institution 9161 resulted in error code 106 approximately every 3 minutes");
		messages.add("PATTERN_CUSTOMER_HEALTH_DETECTED\ninstitutionId=25223  customerId=30002494809 errorCode=530 interval=5 days\nMessage=Customer 30002494809 of institution 25223  resulted in error code 530 for last 5 days");
		messages.add("PATTERN_COUNT_ERROR_DETECTED\ninstitutionId=2616 customerId=1573854342  errorCode=108 count=15\nMessage=Customer 1573854342 of institution 2616 resulted in error code 108 in last 15 attempts");
		messages.add("PATTERN_COUNT_ERROR_DETECTED\ninstitutionId=10 customerId=4401812623 errorCode=185 count=8\nMessage=Customer 4401812623 of institution 10 resulted in error code 185 in last 8 attempts");
		messages.add("PATTERN_TIME_INTERVAL_ERROR_DETECTED\ninstitutionId=1619 customerId=6231516623 errorCode=119 interval=12 Minutes\nMessage=Customer 6231516623 of institution 1619 resulted in error code 119 approximately every 12 minutes");
		messages.add("PATTERN_CUSTOMER_HEALTH_DETECTED\ninstitutionId=8148  customerId=12816969996 errorCode=100 interval=5 hours\nMessage=Customer 12816969996 of institution 8148  resulted in error code 100 for last 5 hours");
		messages.add("PATTERN_TIME_INTERVAL_ERROR_DETECTED\ninstitutionId=9161 customerId=59006670562 errorCode=519 interval=3 Minutes\nMessage=Customer 59006670562 of institution 9161 resulted in error code 519 approximately every 3 minutes");
		messages.add("PATTERN_CUSTOMER_HEALTH_DETECTED\ninstitutionId=25223  customerId=30003544809 errorCode=102 interval=9 hours\nMessage=Customer 30003544809 of institution 25223  resulted in error code 102 for last 9 hours");
		messages.add("PATTERN_COUNT_ERROR_DETECTED\ninstitutionId=2616 customerId=1572454342  errorCode=127 count=25\nMessage=Customer 1572454342 of institution 2616 resulted in error code 127 in last 25 attempts");
	}
	public static void main(String[] args)
	{
		Thread.currentThread().setName("APP_THREAD");
		App app = new App();
		app.start();
	}

	private void start()
	{
		ThreadTimeConsoleLogger logger = new ThreadTimeConsoleLogger();
		Logger.registerLogger(logger);
		Logger.getLogger().info("Starting the APP");
		WebScrabDataPublisher publisher = new WebScrabDataPublisher();
		FileStoreVO storeVO = new FileStoreVO();
		storeVO.setDataModelRecordPrefix("DATA_FIELD_");
		storeVO.setDataReadMarker("READ");
		storeVO.setModelStorePath("D:\\Ignovative\\file_store\\data_store\\");
		storeVO.setPatternStorePath("D:\\Ignovative\\file_store\\pattern_store\\");
		storeVO.setPatternRecordPrefix("PATTERN_FIELD_");
		storeVO.setDataModelExtension(".dmstore");
		storeVO.setPatternExtension(".patternstore");
		IDataStore<FileStoreVO> dataStore = new FileDataStore(storeVO );
		IDataListener<List<IDataModel>> listener = new DataModelListener(dataStore );
		publisher.registerListener("WebScrablistner", listener );
		DataProcessor processor = new DataProcessor(new WebScrabDataExtractor(), publisher);
		DataCruncherCallBack dataInstancecallback = new DataCruncherCallBack() {

			@Override
			public void crunch(String fileData)
			{
				processor.processData(new WebScrabDataInstance(fileData));
			}
		};

		DataCruncherCallBack dataModelback = new DataCruncherCallBack() {

			@Override
			public void crunch(String fileData)
			{
				try
				{
					dataStore.persistPattern(new WebScrabPattern(fileData));
					frmOpt.setVisible(true);
					JOptionPane.showMessageDialog(frmOpt, messages.get(random.nextInt(messages.size())));
					frmOpt.setVisible(false);
				}
				catch (DataStoreException e)
				{
					Logger.getLogger().error("Unable to persist Pattern.", e);
				}
			}
		};

		TextDataCruncher datamodelCruncher = new TextDataCruncher("DATA_INSTANCE_READER", "D:\\Ignovative\\input\\", ".csv", dataInstancecallback);
		ObjectDataCruncher patternCruncher = new ObjectDataCruncher("DATA_MODEL_READER", "D:\\Ignovative\\file_store\\data_store\\", ".dmstore", dataModelback);

		Thread t1 = new Thread(datamodelCruncher, "DATA_INSTANCE_READER_THREAD");
		Thread t2 = new Thread(patternCruncher, "DATA_MODEL_READER_THREAD");

		t1.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();

	}

}
