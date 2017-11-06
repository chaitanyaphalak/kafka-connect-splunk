package com.splunk.hecclient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by kchen on 10/31/17.
 */
public class HecChannelTest {
    @Test
    public void getterSetter() {
        IndexerInf indexer = new IndexerMock();
        HecChannel ch = new HecChannel(indexer);
        IndexerInf idx = ch.getIndexer();
        Assert.assertTrue(idx == indexer);

        String id = ch.getId();
        Assert.assertNotNull(id);
        Assert.assertFalse(id.isEmpty());

        Assert.assertEquals(id, ch.toString());
        Assert.assertNotNull(ch.hashCode());
    }

    @Test
    public void setTracking() {
        IndexerInf indexer = new IndexerMock();
        HecChannel ch = new HecChannel(indexer);

        // enable channel tracking
        ch.setTracking(true);

        // we do it again to cover more branch
        ch.setTracking(true);
        EventBatch batch = new JsonEventBatch();
        Event event = new JsonEvent("ni", "hao");
        batch.add(event);
        ch.send(batch);
        Assert.assertEquals(ch.getId(), event.getFields().get("hec-channel"));

        // disable channel tracking
        ch.setTracking(false);
        // we do it again to cover more branch
        ch.setTracking(false);
        batch = new JsonEventBatch();
        event = new JsonEvent("ni", "hao");
        batch.add(event);
        ch.send(batch);
        Assert.assertNull(event.getFields());
    }

    @Test
    public void send() {
        IndexerMock indexer = new IndexerMock();
        HecChannel ch = new HecChannel(indexer);

        // enable channel tracking
        EventBatch batch = new JsonEventBatch();
        Event event = new JsonEvent("ni", "hao");
        batch.add(event);
        ch.send(batch);

        List<EventBatch> batches = indexer.getBatches();
        Assert.assertEquals(1, batches.size());
        Assert.assertEquals(1, batches.get(0).getEvents().size());
        Assert.assertEquals("ni", batches.get(0).getEvents().get(0).getEvent());
        Assert.assertEquals("hao", batches.get(0).getEvents().get(0).getTied());
    }

    @Test
    public void executeHttpRequest() {
        HttpUriRequest req = new HttpPost();
        IndexerMock indexer = new IndexerMock();
        HecChannel ch = new HecChannel(indexer);
        String res = ch.executeHttpRequest(req);
        Assert.assertEquals(null, res);
        List<HttpUriRequest> reqs = indexer.getRequests();
        Assert.assertEquals(1, reqs.size());
        Assert.assertEquals(req, reqs.get(0));
    }

    @Test
    public void eq() {
        HecChannel lhsCh = new HecChannel(null);
        HecChannel rhsCh = new HecChannel(null);
        Assert.assertFalse(lhsCh.equals(rhsCh));

        Object copy = lhsCh;
        Assert.assertTrue(lhsCh.equals(copy));

        Assert.assertFalse(lhsCh.equals(null));
        Assert.assertFalse(lhsCh.equals(lhsCh.getId()));
    }
}
