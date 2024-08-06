package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.UrlNotFoundException;
import com.example.shortenurl.exceptions.UserNotFoundException;
import com.example.shortenurl.models.ShortenedUrl;
import com.example.shortenurl.models.UrlAccessLog;
import com.example.shortenurl.models.User;
import com.example.shortenurl.models.UserPlan;
import com.example.shortenurl.repositories.ShortenedUrlRepository;
import com.example.shortenurl.repositories.UrlAccessLogRepository;
import com.example.shortenurl.repositories.UserRepository;
import com.example.shortenurl.utils.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    UrlAccessLogRepository urlAccessLogRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        String shortUrl = ShortUrlGenerator.generateShortUrl();

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(url);
        shortenedUrl.setShortUrl(shortUrl);
        shortenedUrl.setUser(user);
        shortenedUrl.setExpiresAt(getExpiresAt(user.getUserPlan()));

        shortenedUrlRepository.save(shortenedUrl);

        return shortenedUrl;
    }

    @Override
    public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException {
        ShortenedUrl shortenedUrl = shortenedUrlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException("Url not found"));

        if (shortenedUrl.getExpiresAt() < Calendar.getInstance().getTimeInMillis())
            throw new RuntimeException("Link expired");

        UrlAccessLog urlAccessLog = new UrlAccessLog();
        urlAccessLog.setShortenedUrl(shortenedUrl);
        urlAccessLog.setAccessedAt(Calendar.getInstance().getTimeInMillis());
        urlAccessLogRepository.save(urlAccessLog);

        return shortenedUrl.getOriginalUrl();
    }

    private long getExpiresAt(UserPlan plan) {
        Calendar calendar = Calendar.getInstance();

        switch (plan) {
            case FREE -> calendar.add(Calendar.DAY_OF_YEAR, 1);
            case TEAM -> calendar.add(Calendar.DAY_OF_YEAR, 7);
            case BUSINESS -> calendar.add(Calendar.DAY_OF_YEAR, 30);
            case ENTERPRISE -> calendar.add(Calendar.DAY_OF_YEAR, 365);
        }

        return calendar.getTimeInMillis();
    }
}
