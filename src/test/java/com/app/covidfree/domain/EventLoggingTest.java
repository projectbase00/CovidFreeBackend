package com.app.covidfree.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.covidfree.web.rest.TestUtil;

public class EventLoggingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLogging.class);
        EventLogging eventLogging1 = new EventLogging();
        eventLogging1.setId(1L);
        EventLogging eventLogging2 = new EventLogging();
        eventLogging2.setId(eventLogging1.getId());
        assertThat(eventLogging1).isEqualTo(eventLogging2);
        eventLogging2.setId(2L);
        assertThat(eventLogging1).isNotEqualTo(eventLogging2);
        eventLogging1.setId(null);
        assertThat(eventLogging1).isNotEqualTo(eventLogging2);
    }
}
