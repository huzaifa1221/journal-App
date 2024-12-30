package com.springboot.journalApp.Service;

import com.springboot.journalApp.api.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService
{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String api;

    @Value("${weather.api.key}")
    private String api_key;

    public WeatherResponse report(String city)
    {
        String finalAPI = api.replace("API_KEY",api_key).replace("CITY",city);
        ResponseEntity<WeatherResponse> response =restTemplate.exchange(finalAPI,HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
