package com.DT.journal.service;

import com.DT.journal.api.response.WeatherResponse;
import com.DT.journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);
        if (weatherResponse !=null){
            return weatherResponse;
        }else {
            String finalAPI = appCache.App_Cache.get("weather_api")
                    .replace("<apiKey>", apiKey)
                    .replace("<city>", city);
            ResponseEntity<WeatherResponse> weather =
                    restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = weather.getBody();
            if (body!=null){
                redisService.set("Weather_of_" + city, body,5l);
            }
            return body;
        }
    }
}
