package com.message.demo.service.messageDetail;

import com.message.demo.service.details.MessageDetailsServiceImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MessageDetailsTest {

    private MessageDetailsServiceImpl messageDetailsService = new MessageDetailsServiceImpl();

    @Test
    public void isPlandrome() {
        assertThat(messageDetailsService.isPalindrome("aaa")).isTrue();
        assertThat(messageDetailsService.isPalindrome("abcda")).isFalse();
        assertThat(messageDetailsService.isPalindrome("a")).isTrue();
        assertThat(messageDetailsService.isPalindrome("ac")).isFalse();
        assertThat(messageDetailsService.isPalindrome("dada")).isFalse();
        assertThat(messageDetailsService.isPalindrome("dad")).isTrue();
        assertThat(messageDetailsService.isPalindrome("a")).isTrue();
        assertThat(messageDetailsService.isPalindrome("")).isFalse();
        assertThat(messageDetailsService.isPalindrome(" ")).isTrue();
    }

}
