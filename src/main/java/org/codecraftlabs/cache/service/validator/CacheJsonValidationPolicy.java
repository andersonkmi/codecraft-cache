package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;

class CacheJsonValidationPolicy implements CacheEntryValidationPolicy {
    @Override
    public void apply(@Nonnull CacheEntry cacheEntry) {
        String value = cacheEntry.getValue();
        try {
            new JSONObject(value);
        } catch (JSONException exception) {
            throw new InvalidCacheEntryException("JSON value is malformed");
        }
    }
}
