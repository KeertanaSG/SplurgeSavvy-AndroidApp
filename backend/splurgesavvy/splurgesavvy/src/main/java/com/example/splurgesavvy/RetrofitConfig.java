package com.example.splurgesavvy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }
}
