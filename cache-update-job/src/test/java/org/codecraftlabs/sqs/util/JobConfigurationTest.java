package org.codecraftlabs.sqs.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobConfigurationTest {
    @InjectMocks
    private JobConfiguration jobConfiguration;

    @Mock
    private PropertiesFileReader propertiesFileReaderMock;

    @Test
    void shouldRetrieveSQSUrlOk() {
        when(propertiesFileReaderMock.getProperties("sqsUrl")).thenReturn(Optional.of("http://sqs.aws.com"));
        assertThat(jobConfiguration.getQueueUrl().isPresent()).isTrue();
        assertThat(jobConfiguration.getQueueUrl().get()).isEqualTo("http://sqs.aws.com");
    }

    @Test
    void shouldRetrieveCacheNodesOk() {
        when(propertiesFileReaderMock.getProperties("cacheNodes")).thenReturn(Optional.of("server1, server2, server3"));
        assertThat(jobConfiguration.getCacheNodes().isPresent()).isTrue();
        assertThat(jobConfiguration.getCacheNodes().get()).isEqualTo(Set.of("server1", "server2", "server3"));
    }
}
