# Read out data files
#     - movies_metadata.csv
movies_df=LOAD'/movies/movies_metadata.csv'using org.apache.pig.piggybank.storage.CSVExcelStorage(',') AS
(adult:chararray, belongs_to_collection:chararray, budget:double, genres:chararray, 
homepage:chararray,id:int, imdb_id:chararray, original_language:chararray, original_title:chararray, 
overview:chararray,popularity:double, poster_path:chararray, production_companies:chararray,
production_countries:chararray,release_data:chararray, revenue:double, runtime:double, 
spoken_languages:chararray, status:chararray,tagline:chararray, title:chararray, video:chararray, 
vote_average:double, vote_count:double);

# Extract required lines
filtered_data = FOREACH movies_df GENERATE id, genres, vote_average, vote_count, popularity, title, overview;
