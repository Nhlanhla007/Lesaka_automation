package Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pratyush Choudhary
 */
public class Logger {
	public static final String LOG_FILE = "log\\temp_log.txt";
	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd  yyyy; KK:mm:ss aa");
	private static BufferedWriter bw = null;
	
	/**
	 * @param str The text to be written to the log.
	 */
	public static synchronized  void log(String str) {
		str = sdf.format(new Date()) + " - " + str;
		System.out.println(str);
		try {
			bw = new BufferedWriter(new FileWriter(LOG_FILE, true));
			bw.write(str);
			bw.write("\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

