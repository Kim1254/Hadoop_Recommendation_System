/*
Read out data files
    - movies_metadata.csv
    - ratings_small.csv
*/
CREATE TABLE movies_df (
	adult STRING,
	belongs_to_collection STRING,
	budget DOUBLE,
	genres STRING,
	homepage STRING,
	id INT,
	imdb_id STRING,
	original_language STRING,
	original_title STRING,
	overview STRING,
	popularity DOUBLE,
	poster_path STRING,
	production_companies STRING,
	production_countries STRING,
	release_data STRING,
	revenue DOUBLE,
	runtime INT,
	spoken_languages STRING,
	status STRING,
	tagline STRING,
	title STRING,
	video STRING,
	vote_average DOUBLE,
	vote_count DOUBLE
)

ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
	"separatorChar" = ",",
	"quoteChar" = "\""
) load data local inpath '/home/hadoop/movies_metadata.csv' into table movies_df;

CREATE TABLE ratings (
	userId INT,
	movieId INT,
	rating DOUBLE,
	my_timestamp DOUBLE
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
	"separatorChar" = ",",
	"quoteChar" = "\""
);
LOAD DATA LOCAL INPATH '/home/hadoop/ratings_small.csv' INTO TABLE ratings;

-- Create view with extracted row from movie table
create view filtered_data as select genres, id, title from movies_df;

-- Remove rows containing null data
CREATE VIEW temp_data AS
SELECT genres, id, title
FROM movies_df
WHERE genres IS NOT NULL AND id IS NOT NULL AND title IS NOT NULL;

-- Convert id into movieid (match the column name)
CREATE VIEW filtered_data AS
SELECT genres, id AS movieId, title
FROM temp_data;

-- Create a Pivot Table for aggregate the rating of each user
CREATE VIEW user_movie_ratings AS
SELECT userId, COLLECT_LIST(STRUCT(movieId, rating)) AS movie_ratings
FROM ratings
GROUP BY userId;
