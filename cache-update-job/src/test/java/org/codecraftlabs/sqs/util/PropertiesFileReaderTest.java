package org.codecraftlabs.sqs.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PropertiesFileReaderTest {
    @Test
    void return_empty_optional_when_files_is_invalid() {
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader("fake.properties");
        Optional<String> result = propertiesFileReader.getProperties("someKey");
        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void return_valid_value_when_loading_file() {
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader("src/test/resources/config.properties");
        Optional<String> result = propertiesFileReader.getProperties("sqsUrl");
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get()).isEqualTo("test.sqs.com");
    }

    @Test
    void return_empty_optional_when_key_is_invalid() {
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader("src/test/resources/config.properties");
        Optional<String> result = propertiesFileReader.getProperties("keyNotPresent");
        Assertions.assertThat(result.isEmpty()).isTrue();
    }
}
