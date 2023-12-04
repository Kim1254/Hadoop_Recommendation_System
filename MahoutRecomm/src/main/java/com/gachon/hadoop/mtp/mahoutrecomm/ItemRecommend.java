package com.gachon.hadoop.mtp.mahoutrecomm;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommend {
	// Item Based Recommend
	public static void main(String[] args) {
			
		DataModel model;
		
		try {
			/* Generate Data Model */
			model = new FileDataModel(new File("src/content_individ.csv"));
			
			/* Generate Similarity Model */
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			
			/* Select Recommender; ItemBased */
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			int x = 1;
			/* Give Recommendation */
			for(LongPrimitiveIterator items = model.getItemIDs(); items.hasNext();) {
				
				long itemID = items.nextLong(); /* Current item ID */
				System.out.println("Recommendation for item " + itemID + ":");
				
				/* Obtain top 5 similar items for the current ID */
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemID, 5);
				
				/* Print out similar item */
				for (RecommendedItem recommendation : recommendations) {
					System.out.println("\t" + itemID + ", " + recommendation.getItemID() + ", " + recommendation.getValue());
				}
				x++;
				
				if (x > 3) { /* for 3 times */
					System.exit(0);
				}
			}
			
		} catch(IOException | TasteException e) {
			e.printStackTrace();
		}
		
	}

}
