package webonise.logvisualizer.utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateTimeUtilTest {

    private String stringTime;
    private long expectedTimeInMilliSeconds;

    @Before
    public void setUp() throws Exception {
        stringTime = "07-29 09:58:34.126 PM I/MainActivity: location:(18.510916725279202,73" +
                ".7773856089833) alt:50.2 heading:-31.8 satCount:10.0 velocity:(1.9,-1.2,0.0)";
        expectedTimeInMilliSeconds = 18116914126l;
    }

    @Test
    public void testGetTimeInMillisecondForValidData() throws Exception {
        long actualTimeInMilliseconds = DateTimeUtil.getTimeInMillisecond(stringTime);
        Assert.assertEquals(expectedTimeInMilliSeconds, actualTimeInMilliseconds);
    }

    @Test
    public void testGetTimeInMillisecondForNull() throws Exception {
        long actualTimeInMilliseconds = DateTimeUtil.getTimeInMillisecond(null);
        Assert.assertEquals(0, actualTimeInMilliseconds);
    }
}