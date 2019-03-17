package com.message.demo.service.details;

import org.springframework.stereotype.Service;

@Service
public class MessageDetailsServiceImpl implements MessageDetailsService {

    @Override
    public boolean isPalindrome(String message) {
        if (message == null || message.length() < 1) return false;
        int n = message.length();

        // center is between two chars
        // or
        // center is on a char
        int mid = n / 2;
        int lo = mid;
        int hi = n % 2 == 0 ? mid + 1 : mid;
        while (lo >= 0 && hi < n && message.charAt(lo) == message.charAt(hi)) {
            lo--;
            hi++;
        }
        return hi - lo - 1 == n;
    }

}
