package com.gachon.hadoop.mtp.mahoutrecomm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class StringReplacer_Content {
	public static void main(String[] args) throws IOException {

		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new FileReader("src/content_movies.csv"));
			bw = new BufferedWriter(new FileWriter("src/content_individ.csv"));

			String line;
			while ((line = br.readLine()) != null) {

				if (line.indexOf("[]") != -1)
					continue;

				if (line.charAt(0) == ';' || line.charAt(0) == ',')
					continue;

				String[] values = line.split("\"", -1);

				if (values.length < 2) // no genre data
					continue;

				String idstr = values[0].substring(0, values[0].length() - 1);
				
				if (idstr.contentEquals("474"))
					System.out.println(line);

				String genres = values[1];

				String others = line.substring(idstr.length() + genres.length() + 4);

				others = others.replaceAll("\"", "");

				values = others.split(",", -1);
				String vote_average = values[0];
				String vote_count = values[1];
				String popularity = values[2];

				if (vote_average.length() == 0 || popularity.length() == 0)
					continue;
				
				float va = Float.parseFloat(vote_average);
				float pop = Float.parseFloat(popularity);

				//String title = values[3];
				//String overview = others.substring(values[0].length() + values[1].length() + values[2].length() + values[3].length() + 4);

				genres = genres.replaceAll(" ", "");
				genres = genres.replace("[", "");
				genres = genres.replace("]", "");
				genres = genres.replace("},", "}");
				String[] genre_list = genres.split("}", -1);

				for (String genre : genre_list) {

					if (genre.length() == 0)
						break;
	
					String[] id_start = genre.split("\'id\':", -1);
					String[] id_end = id_start[1].split(",", -1);
					String id = id_end[0];

					bw.write(idstr + ',' + id + ',' + Math.round(pop) + ',' + vote_count + ',' + Math.round(va) + '\n');
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
			bw.close();
		}

	}
}
