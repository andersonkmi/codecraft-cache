package org.codecraftlabs.sqs.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PropertiesFileReaderTest {
    private PropertiesFileReader propertiesFileReader;

    @BeforeEach
    void setup() {
        this.propertiesFileReader = new PropertiesFileReader();
    }

    @Test
    void return_empty_optional_when_not_loaded() {
        Optional<String> result = propertiesFileReader.getProperties("someKey");
        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void return_empty_optional_when_files_is_invalid() {
        propertiesFileReader.loadProperties("fake.properties");
        Optional<String> result = propertiesFileReader.getProperties("someKey");
        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void return_valid_value_when_loading_file() {
        propertiesFileReader.loadProperties("src/test/resources/config.properties");
        Optional<String> result = propertiesFileReader.getProperties("sqsUrl");
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo("test.sqs.com");
    }
}
