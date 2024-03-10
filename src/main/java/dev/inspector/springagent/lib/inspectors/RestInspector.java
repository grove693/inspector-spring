package dev.inspector.springagent.lib.inspectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestInspector extends AbstractInspector {

    public RestInspector(@Value("${inspector.ingestion-key}")String ingestionKey, @Value("${inspector.ingestion-url}")String ingestionUrl) {
        super(ingestionKey, ingestionUrl);
    }

}
