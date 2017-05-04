package sslcertificateresolver.auto;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sslcertificateresolver.SCRReport;

/**
 * @author Sanjeev S.
 */
public class ConsoleUIContext implements ExecutionContext
{
    private static final String COMMA = ",";
    private static final String ENCOUNTERED = "Encountered ";
    private File trustStoreFile;
    private File inputFile;
    private char[] passCode;
    private Map<String, Object> dataStore = new HashMap<>();
    private Scanner scanner;

    @Override
    public Object getData(String dataKey)
    {
        if(dataKey == null || dataKey.isEmpty())
        {
            return null;
        }
        if("trustFile".equalsIgnoreCase(dataKey))
        {
            return trustStoreFile;
        }
        if("inputFile".equalsIgnoreCase(dataKey))
        {
            return inputFile;
        }
        if("passCode".equalsIgnoreCase(dataKey))
        {
            return passCode;
        }
        return dataStore.get(dataKey);
    }

    @Override
    public void interactToUser()
    {
        String switchKey = (String) getData("switchKey");
        if(switchKey != "-c" && hasConfiguration())
        {
            setDataFromStore();
        }
        else
        {
            setUserDataInStore();
        }
    }
    private void setUserDataInStore()
    {
        try(Scanner scannerRef = new Scanner(System.in))
        {
            scanner = scannerRef;
            trustStoreFile = readFileInput("Enter trust store file path: ");
            checkFilePermissions();
            passCode = getTrustStorePassCode();
            inputFile = readFileInput("Enter input file path: ");
        }

    }

    private boolean hasConfiguration()
    {
        // TODO Auto-generated method stub
        return false;
    }

    private void setDataFromStore()
    {
        // TODO Auto-generated method stub

    }

    private char[] getTrustStorePassCode()
    {
        System.out.println("Use default trust store passcode (Y/N): ");
        String userChoice = scanner.next();
        if(userChoice.startsWith("Y") || userChoice.startsWith("y"))
        {
           return "changeit".toCharArray();
        }
        else if(!userChoice.startsWith("N") && !userChoice.startsWith("n"))
        {
            System.out.println("Invlid choice! Choosing \"No\"...");
        }
        char[] passcode = null;
        boolean inValid = true;
        while(inValid)
        {
            java.io.Console console = System.console();
            if(console == null)
            {
                System.out.println("Enter passCode: ");
                String tempPassCode = scanner.next();
                passcode = tempPassCode.toCharArray();
            }
            else
            {
                passcode = console.readPassword("Enter passCode: ");
            }
            inValid = passcode == null || passcode.length < 1;
            if(inValid)
            {
                System.out.println("Invalid passcode. Passcode cannot be empty");
            }
        }
        return passcode;
    }

    private File readFileInput(String userCMD)
    {
        boolean isFile = false;
        File file = null;
        while(!isFile)
        {
            System.out.println(userCMD);
            String filePath = scanner.next();
            file = new File(filePath);
            isFile = file.isFile();
            if(!isFile)
            {
                System.out.println("File " + filePath + " not found.");
            }
        }
        return file;
    }

    private void checkFilePermissions()
    {
        while(!trustStoreFile.canWrite())
        {
            System.out.println("File " + trustStoreFile.getAbsolutePath() + " is readonly.\n Make the file writable and press 'C' to continue or press 'E' to exit");
            String userChoice = scanner.next();
            if(userChoice.startsWith("E") || userChoice.startsWith("e"))
            {
                throw new RuntimeException("User Interrupted");
            }
            else if(!(userChoice.startsWith("C") || userChoice.startsWith("c")))
            {
                System.out.println("Invalid choice! Choosing \"Continue\"...");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void respondUser()
    {
        Throwable e =  (Throwable) getData("Execution exception");
        if(e != null)
        {
            System.err.println("An exception occured causing the process to fail...");
            e.printStackTrace();
            return;

        }
        String noIP = (String) getData("Execution exception case");
        if(noIP != null && !noIP.isEmpty())
        {
            System.err.println("An exception occured causing the process to fail. Cause - " + noIP);
            return;
        }
        List<ContextReport> resultList = (List<ContextReport>) getData("Result");
        publishResult(resultList);
    }

    private void publishResult(List<ContextReport> resultList)
    {
        StringBuilder result = new StringBuilder();
        result.append("Script Name");
        result.append(COMMA);
        result.append("FIID");
        result.append(COMMA);
        result.append("URL");
        result.append(COMMA);
        result.append("Cause");
        result.append(COMMA);
        result.append("Resolution");
        result.append(COMMA);
        result.append("Comments");
        result.append(COMMA);
        result.append("Suggestion");
        result.append(COMMA);
        result.append("Status");
        result.append(System.lineSeparator());
        for(ContextReport report : resultList)
        {
            result.append(report.getFailureVO().getScriptName());
            result.append(COMMA);
            result.append(report.getFailureVO().getFiId());
            result.append(COMMA);
            result.append(report.getFailureVO().getUrlFailing());
            result.append(COMMA);
            result.append(report.getFailureVO().getCause());
            result.append(COMMA);
            SCRReport scr = report.getReport();
            String resolution = null;
            if(scr.getAddedCertificatesCount() > 0)
            {
                resolution = "Added " + scr.getAddedCertificatesCount() + " certificates from " + scr.getDiscoveredCertificatesCount() + " discovered";
            }
            result.append(resolution);
            result.append(COMMA);
            String comments = getComments(scr, report.getExecutionException());
            result.append(comments);
            result.append(COMMA);
            result.append(getSuggestion(scr));
            result.append(COMMA);
            String status = comments == null || comments.contains(ENCOUNTERED) ? "Not Fixed" : "Fixed";
            result.append(status);
            result.append(System.lineSeparator());
        }
        saveResult(result);
    }

    private void saveResult(StringBuilder result)
    {
        try
        {
            String directoryPath = "D:\\ssl_certificate_report\\";
            File directory = new File(directoryPath);
            if(!directory.exists())
            {
                directory.mkdirs();
            }
            File file = new File(directoryPath + "report_" + System.currentTimeMillis() + ".csv");
            Files.write(file.toPath(), result.toString().getBytes());
            System.out.println("SSL Certificate Resolver Report generated. " + file.getAbsolutePath());
        }
        catch(Exception e)
        {
            System.err.println("Unable to generate Report.");
            e.printStackTrace(System.err);
        }
    }


    private String getComments(SCRReport scr, Throwable executionException) {

        String comments;
        if(scr.hasException())
        {
            comments = ENCOUNTERED + scr.getOtherException();
        }
        else if(scr.hasSSLException())
        {
            comments = ENCOUNTERED + scr.getSslException();
        }
        else if(executionException != null)
        {
            comments = ENCOUNTERED + executionException;
        }
        else
        {
            comments = "Added Certificates " + scr.getAddedCertificatesCount() + " from discovered certificates" + scr.getDiscoveredCertificatesCount() + " into the trust store";
        }
        return comments;

    }

    private String getSuggestion(SCRReport scr)
    {
        String suggestion = null;
       if(scr.hasException())
       {
           if(scr.getOtherException().toString().contains("Certificate") ||
                   (scr.getOtherException().getCause() != null && scr.getOtherException().getCause().toString().contains("Certificate")))
           {
               suggestion = "Encountered Certificate Exception cannot be resolved by adding certificate to trust store. Try Skipping certificate validation or look for Invalid Certificate Not yet valid exception";
           }
       }
       if(scr.hasSSLException())
       {
           suggestion = "Identify and resolve the SSL exception";
       }
       return suggestion;
    }

    @Override
    public void setData(String dataKey, Object data)
    {
        dataStore.put(dataKey, data);
    }

}
