/*
Read out data files
    - movies_metadata.csv
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
)load data local inpath '/home/hadoop/movies_metadata.csv' into table movies_df;

-- Extract required lines
CREATE VIEW filtered_data AS
SELECT id, genres, vote_average, vote_count, popularity, title, overview
FROM movies_df;filtered_data = FOREACH movies_df GENERATE id, genres, vote_average, vote_count, popularity, title, overview;
