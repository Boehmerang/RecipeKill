package Murder.RecipeKill;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigManager 
{
	 private static ArrayList lines = new ArrayList();
	  private File path;

	  public ConfigManager(File path)
	  {
	    this.path = path;
	  }

	  public void readConfig() {
	    BufferedReader reader = getReader();
	    if (reader == null) {
	      writeConfig();
	      return;
	    }
	    try
	    {
	      String line;
	      while ((line = reader.readLine()) != null)
	        if ((!line.trim().startsWith("#")) && (!line.isEmpty())) {
	          line = line.trim();
	          lines.add(line);
	          RecipeManager.addID(line);
	        }
	      reader.close();
	    } catch (IOException e) {
	      e.printStackTrace(System.err);
	    }
	  }

	  public static List getLines() {
	    return Collections.unmodifiableList(lines);
	  }

	  private BufferedReader getReader() {
	    try {
	      FileInputStream fis = new FileInputStream(this.path);
	      DataInputStream dis = new DataInputStream(fis);
	      return new BufferedReader(new InputStreamReader(dis)); } catch (FileNotFoundException e) {
	    }
	    return null;
	  }

	  private void writeConfig()
	  {
	    try {
	      FileWriter fw = new FileWriter(this.path, true);
	      BufferedWriter writer = new BufferedWriter(fw);

	      writer.write("# Use the following format for the Blocked items <id>:<metadata>\n");
	      writer.write("# Move to a new line for each particualar Item\n");
	      writer.write("# Block all metadata versions of blocks (ex. 98:0-98:3 by not\n");
	      writer.write("# including a the metadata slot");

	      writer.close();
	    } catch (Exception e) {
	      e.printStackTrace(System.err);
	    }
	  }
}
