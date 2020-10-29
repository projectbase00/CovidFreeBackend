package com.app.covidfree.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.covidfree.web.rest.TestUtil;

public class OtpCodesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtpCodes.class);
        OtpCodes otpCodes1 = new OtpCodes();
        otpCodes1.setId(1L);
        OtpCodes otpCodes2 = new OtpCodes();
        otpCodes2.setId(otpCodes1.getId());
        assertThat(otpCodes1).isEqualTo(otpCodes2);
        otpCodes2.setId(2L);
        assertThat(otpCodes1).isNotEqualTo(otpCodes2);
        otpCodes1.setId(null);
        assertThat(otpCodes1).isNotEqualTo(otpCodes2);
    }
}
