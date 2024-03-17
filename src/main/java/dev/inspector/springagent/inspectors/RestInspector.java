package dev.inspector.springagent.inspectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestInspector extends AbstractInspector {

    public RestInspector(@Value("${inspector.ingestion-key}")String ingestionKey, @Value("${inspector.time-to-flush}")String timeToFlush) {
        super(ingestionKey, timeToFlush);
    }

}