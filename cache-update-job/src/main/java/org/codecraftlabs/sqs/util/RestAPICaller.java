package org.codecraftlabs.sqs.util;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.sqs.service.CacheEntry;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;

public class RestAPICaller {
    private static final Logger logger = LogManager.getLogger(RestAPICaller.class);
    private final JobConfiguration jobConfiguration;

    public RestAPICaller(@Nonnull JobConfiguration jobConfiguration) {
        this.jobConfiguration = jobConfiguration;
    }

    public void submitUpdates(@Nonnull String url, @Nonnull CacheEntry cacheEntry) {
        Optional<Set<String>> servers = jobConfiguration.getCacheNodes();
        if (servers.isEmpty()) {
            return;
        }

        for (String server : servers.get()) {
            logger.info("Calling API on server '" + server + "'");
            String endpoint = server + "/" + url;
            submitUpdate(endpoint, new Gson().toJson(cacheEntry));
        }
    }

    public void submitDeletes(@Nonnull String url, @Nonnull String key) {
        Optional<Set<String>> servers = jobConfiguration.getCacheNodes();
        if (servers.isEmpty()) {
            return;
        }

        for (String server : servers.get()) {
            String endpoint = server + "/" + url + "/" + key;
            submitDelete(endpoint);
        }
    }

    private void submitUpdate(@Nonnull String endpoint, @Nonnull String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("skip-synchronization", "true")
                .header("Content-Type", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString(body))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            logger.error("Fail to submit request", exception);
        }
    }

    private void submitDelete(@Nonnull String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("skip-synchronization", "true")
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            logger.error("Fail to submit request", exception);
        }
    }
}
