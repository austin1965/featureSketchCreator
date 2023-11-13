package com.featuresketch.featuresketchcreator;

import com.featuresketch.featuresketchcreator.experimenting.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FeatureSketchCreatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureSketchCreatorApplication.class, args);
		FileParser fileParser = new FileParser();

		try {
			fileParser.parseFile();
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}

	}

}
