package com.gachon.hadoop.mtp.mahoutrecomm;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserRecommend {
	// User Based Recommend
	public static void main(String[] args) throws IOException, TasteException {
		
		/* Generate Data Model */
		DataModel model = new FileDataModel(new File("src/collab_individ.csv"));
		
		/* Create Similarity model  */
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		
		/* Get neighborhood satisfying threshold 0.1 */
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		
		/* Generate recommender of User-Based */
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		int target_id = 2;
		
		System.out.println("Recommendation for user " + target_id + ":");
		
		/* Recommend 3 items for user with ID 2 */
		List<RecommendedItem> recommendations = recommender.recommend(target_id, 3);
		for (RecommendedItem recommendation : recommendations) {
			System.out.println("\t" + recommendation);
		}
	}

}
