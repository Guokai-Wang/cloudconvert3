package io.ecardify.cloudconvert3;
import java.io.File;
public class CloudConverter 
{
	// private File inputFile, outputFile;
	public CloudConverter()
	{
	}
	
	public void convert(File inputFile, File outputFile)
	{
        try
        {
        // Create service object
        	CloudConvertService service = new CloudConvertService("<api key>");

// Create conversion process
            ConvertProcess process = service.startProcess("jpg", "png");

// Perform conversion
 /* process.startConversion(new File("test.jpg")); */
            process.startConversion(inputFile);

// Wait for result
            ProcessStatus status;
            waitLoop: while (true) 
            {
            	status = process.getStatus();
    
            	switch (status.step) 
            	{
            	case FINISHED: break waitLoop;
                case ERROR: throw new RuntimeException(status.message);
            	}
    
    // Be gentle
            	Thread.sleep(200);
            }

// Download result
            service.download(status.output.url, new File("output.png"));

// Clean up
            process.delete();
        }
        catch(Exception exception) {exception.printStackTrace();}
	}
}
