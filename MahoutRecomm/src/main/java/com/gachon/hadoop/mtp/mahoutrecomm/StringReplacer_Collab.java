package com.gachon.hadoop.mtp.mahoutrecomm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StringReplacer_Collab {
	   public static void main(String[] args) throws IOException {
	      
	      BufferedReader br = new BufferedReader(new FileReader("src/collab_ratings.csv"));
	      BufferedWriter bw = new BufferedWriter(new FileWriter("src/collab_individ.csv"));
	      
	      String line;
	      while ((line = br.readLine()) != null) {
	         String[] values = line.split(",", -1);
	         String idstr = values[0];
	         String ratings = line.substring(idstr.length() + 1);
	         
	         if (idstr.length() == 0)
	        	 continue;

	         ratings = ratings.replace("\"{", "");
	         ratings = ratings.replace("}\"", "");
	         ratings = ratings.replace("),", ")");
	         
	         String[] rating = ratings.split("\\)", -1);
	         for (String rat : rating) {
	        	 
	        	 if (rat.length() == 0)
	        		 break;

	        	 rat = rat.replace("(", "");
		         String[] value = rat.split(",", -1);
		         bw.write(idstr + ',' + value[0] + ',' + value[1] + '\n');
	         }
	      }
	      
	      br.close();
	      bw.close();
	      
	   }
}
