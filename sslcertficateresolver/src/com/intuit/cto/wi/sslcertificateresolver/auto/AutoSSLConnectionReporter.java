package com.intuit.cto.wi.sslcertificateresolver.auto;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.intuit.cto.wi.sslcertificateresolver.Logger;
import com.intuit.cto.wi.sslcertificateresolver.SSLCertificateResolver;


/**
 * @author Sanjeev S. [527395]
 */
public class AutoSSLConnectionReporter
{
    private static final String VERSION = "1.0.0-Auto";

    private AutoSSLConnectionReporter()
    {
    }

    public static void main(String[] args)
    {
        String switchKey = "";//-h, -i
        if (args.length >= 1)
        {
            switchKey = args[0];
        }
        if("-h".equals(switchKey) || !"-c".equals(switchKey))
        {
            showHelp();
        }
        else
        {
           run(switchKey);
        }
    }

    private static void run(String switchKey)
    {
        IInputReader input = InputReaderFactory.getInputReader("csv");
        ExecutionContext context = input.prepareExecutionContext();
        context.setData("switchKey", switchKey);
        Set<CertificateFailureVO> voList = null;
        try
        {
            voList = input.getInput(context);
        }
        catch(Exception e)
        {
            context.setData("Execution exception", e);
        }
        if(voList == null || voList.isEmpty())
        {
            context.setData("Execution exception case", "No Input data");
        }
        else
        {
            File trustFile = (File) context.getData("trustFile");
            char[] passCode = (char[]) context.getData("passCode");
            if(passCode == null)
            {
                passCode = "changeit".toCharArray();
            }
            List<ContextReport> resultList = new ArrayList<>();
            for(CertificateFailureVO vo : voList)
            {
                ContextReport report = new ContextReport();
                report.setFailureVO(vo);
                try
                {
                    SSLCertificateResolver r = new SSLCertificateResolver(vo.getUrlFailing(), trustFile, passCode);
                    report.setReport(r.resolve());
                    Logger.getLogger().info(report.getReport());
                }
                catch (MalformedURLException | FileNotFoundException e) {
                    report.setExecutionException(e);
                }
                resultList.add(report);
            }
            context.setData("Result", resultList);
            context.respondUser();
        }


    }

    private static void showHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append("sslcertificateresolver v" + VERSION + " USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar [mode] [option] [input]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[mode] - Mode in which the tool to run - MANUAL, AUTO, MERGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[option] ");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("-h : Display this help text");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("-i: Input switch which takes a Input mode as input.\n\t[input] - csv : Input is read from an CSV file exported from Splunk.\n\t [input] - splunk Extract input from splunk directly (not supported in v1.0.0-Auto)");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("Display the Help text. Example " + System.lineSeparator() + "java -jar sslcertificateresolver.jar AUTO -h ");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("Run with input csv. Example " + System.lineSeparator() + "java -jar sslcertificateresolver.jar AUTO -i csv");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("Run in default mode. Example " + System.lineSeparator() + "java -jar sslcertificateresolver.jar");
        Logger.getLogger().info(str);


    }
}
