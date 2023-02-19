package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {
    private static final String TAG = "Util";

    /**
     * The directory in which all voices for the Dialer app are stored (in the app-specific
     * internal storage location).
     */
    public static final String VOICE_DIR = "voices";

    /**
     * The default voice to be used by the Dialer app.
     */
    public static final String DEFAULT_VOICE = "mamacita_us";

    /**
     * The file extension for the default voice files used by the Dialer app.
     */
    public static final String DEFAULT_VOICE_EXTENSION = "mp3";

    /**
     * The resource ids for the default voice (in res/raw).
     */
    public static final int[] DEFAULT_VOICE_RESOURCE_IDS = {
            R.raw.zero, R.raw.one, R.raw.two, R.raw.three,
            R.raw.four, R.raw.five, R.raw.six, R.raw.seven,
            R.raw.eight, R.raw.nine, R.raw.star, R.raw.pound
    };

    /**
     * The file names for each sound in a voice, mapped to its corresponding button title.
     */
    public static final Map<String, String> DEFAULT_VOICE_FILE_NAMES= new HashMap<>();

    static {
        DEFAULT_VOICE_FILE_NAMES.put("0", "zero.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("1", "one.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("2", "two.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("3", "three.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("4", "four.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("5", "five.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("6", "six.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("7", "seven.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("8", "eight.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("9", "nine.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("*", "star.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("#", "pound.mp3");
    }

    /**
     * Returns the absolute path to the directory on the filesystem where app-specific files
     * are stored.
     * @param context an application context
     * @return The path of the directory holding application files.
     */
    public static File getInternalStorageDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * Returns the absolute path to the directory containing the voice files for the
     * given voice name.
     * @param context an application context
     * @param voiceName the name of the voice to get the directory for
     * @return The path of the directory for holding the voice given by <code>voiceName</code>.
     */
    public static File getDirForVoice(Context context, String voiceName) {
        if (voiceName == null || voiceName.length() < 1) {
            voiceName = DEFAULT_VOICE;
        }

        return new File(getInternalStorageDir(context), VOICE_DIR + File.separator + voiceName);
    }

    /**
     * Returns the absolute path to the directory containing the default voice files.
     * @param context an application context
     * @return The path of the directory holding the default voice.
     */
    public static File getDirForDefaultVoice(Context context) {
        return getDirForVoice(context, DEFAULT_VOICE);
    }

    /**
     * Copies each resource in <code>DEFAULT_VOICE_RESOURCE_IDS</code> to the directory returned
     * by <code>getDirForDefaultVoice</code>.
     * @param context an application context
     * @return true if all files are copied, or false if an error occurs.
     */
    public static boolean copyDefaultVoiceToInternalStorage(Context context) {
        // Complete path to the dir of the default voice
        File voiceDir = getDirForDefaultVoice(context);

        if (!voiceDir.exists()) {
            if (!voiceDir.mkdirs()) {
                Log.e(TAG, "Could not create dir: " + voiceDir);
                return false;
            }
        }

        for (int resourceId: DEFAULT_VOICE_RESOURCE_IDS) {
            String filename = context.getResources().getResourceEntryName(resourceId) + "." + DEFAULT_VOICE_EXTENSION; // ex. "one.mp3"

            try (InputStream in = context.getResources().openRawResource(resourceId);
                    OutputStream out = new FileOutputStream(new File(voiceDir, filename))) {
                // Copy from in to out using a byte buffer
                byte[] buffer = new byte[2048];

                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error copying file: " + e.getMessage());
                return false;
            }
        }

        return true; // all files have been copied
    }
	
	 /**
	 * Unzips (decompresses) the contents of a source zip file to a destination directory.
	 * @param sourceFile a zip file to be unzipped to the destination directory
	 * @param destinationDir a directory in which the contents of the zip file are to be unzipped
	 * @return true if the zip file is successfully unzipped
	 */
	public static boolean unzip(File sourceFile, File destinationDir) {
		try {
			// Open the source ZIP file for reading
			ZipFile zipFile = new ZipFile(sourceFile);

			// Get content of ZIP file (as an enumeration of ZipEntry)
			Enumeration<? extends ZipEntry> e = zipFile.entries();

			// As long as there are more entries to handle
			while (e.hasMoreElements()) {
				// Get the next entry
				ZipEntry entry = (ZipEntry) e.nextElement();

				File destinationFile = new File(destinationDir, entry.getName());

				// Create destination folders (if any)
				File destinationParent = destinationFile.getParentFile();

				if (destinationParent != null) {
					destinationParent.mkdirs();
				}

				// Do the unzipping, but only if the entry is a file
				if (!entry.isDirectory()) {
					// A BufferReader to read the entry from the zip file
					BufferedInputStream in = new BufferedInputStream(
							zipFile.getInputStream(entry));

					// A BufferedOutputStream to save the entry to destination
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(destinationFile));

					// Create a buffer to avoid writing byte for byte
					byte[] buffer = new byte[4096];
					int bytesWrite;

					// Writes an array of bytes from the zip to the destination
					while ((bytesWrite = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesWrite);
					}

					// Close streams used
					out.flush();
					out.close();
					in.close();
				}
			}

			// Close the zip file
			zipFile.close();
		}
		catch (IOException e) {
			Log.e(TAG, "Error unzipping " + sourceFile + ": " + e.toString());
			return false;
		}

		return true;
	}

    /**
     * Check if the default voice files from res/raw already exists in the directory returned
     * by <code>getDirForDefaultVoice</code>.
     * @param context an application context
     * @return true if the default voice exists in internal app-specific storage, false if not.
     */
    public static boolean defaultVoiceExist(Context context) {
		// TODO: Add check if default voice dir already exist in the internal app storage

//        File dir = getDirForDefaultVoice(context);
//        String zero = "zero."+Util.DEFAULT_VOICE_EXTENSION;
//        File file = new File(dir, zero);
//        if (file.exists()){
//            return true;
//        }
//        return false;

        //NY TEST 3

        // Get the directory of the default voice
        File defaultVoiceDir = getDirForDefaultVoice(context);

        // Iterate over the file names in DEFAULT_VOICE_FILE_NAMES
        for (String filename : DEFAULT_VOICE_FILE_NAMES.values()) {
            // Construct the file path
            File file = new File(defaultVoiceDir, filename);

            // Check if the file exists
            if (!file.exists()) {
                // Handle the case where the file does not exist
                // For example, you can show a message to the user
                Toast.makeText(context, filename + " does not exist", Toast.LENGTH_SHORT).show();

                // Exit the loop and return false
                return false;
            }
        }

        // If all files exist, return true
        Toast.makeText(context, "all files exist", Toast.LENGTH_SHORT).show();
        return true;

        //NY TEST2
        // Get the default voice directory
//        File dir = getDirForDefaultVoice(context);
//
//        // Loop through the default voice file names
//        for (String fileName : DEFAULT_VOICE_FILE_NAMES.values()) {
//            // Get the file path for the current file name
//            String filePath = dir.getAbsolutePath() + File.separator + fileName;
//
//            // Check if the file exists, return false if not
//            if (new File(filePath).exists()) {
//                Toast.makeText(context, fileName + " does not exist", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//
//        // All files exist, return true
//        return true;


//        //NY TEST Kontrollerar varje fil, den aktuella kontrollerar bara första filen
//        // Loop over each filename in the DEFAULT_VOICE_FILE_NAMES map
//        for (String filename : DEFAULT_VOICE_FILE_NAMES.values()) {
//
//            // Create a new File object with the current filename and directory path
//            File file = new File(getDirForDefaultVoice(context), filename);
//
//            // Check if the file exists
//            if (!file.exists()) {
//                Toast.makeText(context, filename + " does not exist", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        return true;
//        // Compare with loop not working
//        File dir = getDirForDefaultVoice(context);
//        String zero = "zero."+Util.DEFAULT_VOICE_EXTENSION;
//        String one = "one."+Util.DEFAULT_VOICE_EXTENSION;
//        String two = "two."+Util.DEFAULT_VOICE_EXTENSION;
//        String three = "three."+Util.DEFAULT_VOICE_EXTENSION;
//        String four = "four."+Util.DEFAULT_VOICE_EXTENSION;
//        String five = "five."+Util.DEFAULT_VOICE_EXTENSION;
//        String six = "six."+Util.DEFAULT_VOICE_EXTENSION;
//        String seven = "seven."+Util.DEFAULT_VOICE_EXTENSION;
//        String eight = "eight."+Util.DEFAULT_VOICE_EXTENSION;
//        String nine = "nine."+Util.DEFAULT_VOICE_EXTENSION;
//        String star = "star."+Util.DEFAULT_VOICE_EXTENSION;
//        String pound = "pound."+Util.DEFAULT_VOICE_EXTENSION;
//        //File file = new File(dir, filename);
//
//        for (String name : Objects.requireNonNull(dir.list())) {
//
//            if (name.equals(zero) || name.equals(one) || name.equals(two) || name.equals(three)
//                || name.equals(four) || name.equals(five) || name.equals(six) || name.equals(seven)
//                || name.equals(eight) || name.equals(nine) || name.equals(pound) || name.equals(star)){
//                return true;
//            }
//
            /////////////////////////////////////////////////////
//            if (name.equals(zero)) {
//                return true;
//            }
//            if (name.equals(one)) {
//                return true;
//            }
//            if (name.equals(two)) {
//                return true;
//            }
//            if (name.equals(three)) {
//                return true;
//            }
//            if (name.equals(four)) {
//                return true;
//            }
//            if (name.equals(five)) {
//                return true;
//            }
//            if (name.equals(six)) {
//                return true;
//            }
//            if (name.equals(seven)) {
//                return true;
//            }
//            if (name.equals(eight)) {
//                return true;
//            }
//            if (name.equals(nine)) {
//                return true;
//            }
//            if (name.equals(star)) {
//                return true;
//            }
//            if (name.equals(pound)) {
//                return true;
//            }

        //}
///////////////////////////////////////////////////////////////////////////
        //return false;


/////////////////////////////////////////////////////////////////////////////
        //For each name
//        for (String name : DEFAULT_VOICE_FILE_NAMES.values())
//            if (name == "zero.mp3"){
//                return true;
//            }
            //Log.d("Utils","If statement SIZE"+name);


    //return true;
//        Iterator<Map.Entry<String,String>> itr = DEFAULT_VOICE_EXTENSION.iterator();
//        while()
//
//        int A = DEFAULT_VOICE_EXTENSION.length();

        //ArrayList<String> s = (ArrayList<String>) DEFAULT_VOICE_FILE_NAMES;
//        //Map<String, String> s = DEFAULT_VOICE_FILE_NAMES;
//        //String s = String.valueOf(DEFAULT_VOICE_FILE_NAMES);
//        Map<String, String> s = DEFAULT_VOICE_FILE_NAMES;
//
//        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
//
//        for (int i = 0; i < s.hashCode(); i++)  {
//            Log.d("Utils","If statement SIZE" + s.get(i));
//        }
//
//
//        for(String i : cars) {
//            Log.d("Utils","If statement" + i);
//        }
//        //s.forEach((n) -> n);





//        // Get the sounds from internal storage //TODO:Känns fel :P
//        File path = Util.getDirForDefaultVoice(context);
//        File zero= new File(path + "/zero." + Util.DEFAULT_VOICE_EXTENSION);
//        try {
//            if (zero.exists()){
//                return true;
//            }
//            else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;


//        File filepath = getDirForDefaultVoice(context);
//        //File yourfile = new File(String.valueOf(filepath));
//
//        for (int i = 0; i<filepath.length(); i++) {
//            if (filepath.exists())
//                return true;
//
//        }
//        return false;
//        if (filepath.exists())
//            return true;
//        else
//            return false;




//        DEFAULT_VOICE.length();
//        File v = getDirForVoice(context, DEFAULT_VOICE);
//        for (int i =0; i<DEFAULT_VOICE_RESOURCE_IDS.length; i++) {
//            if (DEFAULT_VOICE_RESOURCE_IDS[i] == 2131755018) {
//                v.hashCode();
//                return true;//2131755018
//                //getDirForVoice
//
//            }
////            if (getDirForVoice(context, DEFAULT_VOICE) == DEFAULT_VOICE_RESOURCE_IDS[i]) {
////
////            }
//            Log.d("Mainactivity","If statement");
//
//        }
//        if (DEFAULT_VOICE.equals(getDirForDefaultVoice(context))) {
//            return true;
//        }
//        return false;
    }

}
