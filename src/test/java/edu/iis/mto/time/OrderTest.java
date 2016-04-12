package edu.iis.mto.time;

import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    final long ONE_DAY_IN_MILLIS = Duration.standardDays(1).getMillis();
    final long TWO_DAYS_AGO = DateTimeUtils.currentTimeMillis() - ONE_DAY_IN_MILLIS * 2;

    @Before
    public void setUp() {
        // Fix time if changed
        DateTimeUtils.setCurrentMillisFixed(System.currentTimeMillis());
    }

    @Test (expected = OrderExpiredException.class)
    public void confirmMethodShouldThrowOrderExpiredException() {
        Order order = new Order();

        order.submit();

        DateTimeUtils.setCurrentMillisFixed(TWO_DAYS_AGO);

        order.confirm();
    }


    @Test
    public void confirmMethodShouldFinishCorrectly() {
        Order order = new Order();

        order.submit();

        order.confirm();

        assertEquals(order.getOrderState(), Order.State.SUBMITTED);
    }
}
