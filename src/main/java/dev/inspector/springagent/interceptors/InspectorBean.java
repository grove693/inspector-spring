package dev.inspector.springagent.interceptors;

import dev.inspector.agent.executor.Inspector;
import dev.inspector.agent.model.Config;
import dev.inspector.agent.model.Segment;
import dev.inspector.agent.model.Transaction;
import dev.inspector.agent.utility.JsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static dev.inspector.agent.App.waitMillis;

@Component
public class InspectorBean {

    private Inspector inspector;
    private Transaction transaction;

    @Autowired
    public InspectorBean(@Value("${inspector.ingestion-key}")String ingestionKey) {
        Config config = new Config(ingestionKey);
        Inspector inspector = new Inspector(config);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> onShutdown(inspector)));
        this.inspector = inspector;
    }

    private void onShutdown(Inspector inspector) {
        System.out.println("qui c'è il flush finale");
        inspector.flush();
    }

    public void createSegment(String segmentType, String segmentLabel) {
        Segment segmentRef = inspector.addSegment((segment) -> {
            waitMillis(1000L);
            String ptr = null;
            if (((String) ptr).equals("exception")) {
                System.out.println(1234);
            }

            return segment;
        }, segmentType, segmentLabel, false);
        segmentRef.addContext("view1", (new JsonBuilder()).put("test", "test2").build());
    }

    public void createTransaction(String transactionName) {
        transaction = inspector.startTransaction(transactionName);
    }

    public void flushTransaction(String contextLabel) {
        transaction.setResult("SUCCESS");
        transaction.addContext(contextLabel, (new JsonBuilder()).put("contextkey", "contextvalue").build());
        inspector.flush();
    }

    public Inspector getInspector() {
        return inspector;
    }
}
