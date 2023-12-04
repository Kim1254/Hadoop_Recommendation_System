# Read out data files
#     - movies_metadata.csv
#     - ratings_small.csv
movies_df=LOAD'/movies/movies_metadata.csv'using org.apache.pig.piggybank.storage.CSVExcelStorage(',') AS
(adult:chararray, belongs_to_collection:chararray, budget:double, genres:chararray, 
homepage:chararray,id:int, imdb_id:chararray, original_language:chararray, 
original_title:chararray, overview:chararray,popularity:double, poster_path:chararray, 
production_companies:chararray,production_countries:chararray,release_data:chararray, 
revenue:double, runtime:double, spoken_languages:chararray, status:chararray,tagline:chararray, 
title:chararray, video:chararray, vote_average:double, vote_count:double)

ratings=LOAD'/ratings/ratings_small.csv'USING org.apache.pig.piggybank.storage.CSVExcelStorage(',') AS 
(userId:int, movieId:int, rating:double, timestamp:long);

# Extract required lines and drop null rows
filtered_data = FOREACH movies_df GENERATE genres, id, title;
filtered_data = FILTER filtered_data BY genres IS NOT NULL AND id IS NOT NULL AND title IS NOT NULL;

# Match the column name of ratings_small with movieid
filtered_data = FOREACH filtered_data GENERATE genres, id AS movieId,title;

# Create a Pivot Table for aggregate the rating of each user
user_movie_ratings = FOREACH (GROUP ratings BY userId) 
{movie_ratings = FOREACH ratings GENERATE movieId, rating;
GENERATE group AS userId, movie_ratings AS movie_ratings;
};

# Fill null with 0
user_movie_ratings = FOREACH user_movie_rating
{ movie_ratings = FOREACH movie_ratings GENERATE movieId,
(rating is not null ? rating : 0.0) as rating; GENERATE userId, movie_ratings; }
