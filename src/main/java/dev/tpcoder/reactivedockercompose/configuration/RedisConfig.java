package dev.tpcoder.reactivedockercompose.configuration;

import dev.tpcoder.reactivedockercompose.book.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {
    @Bean
    public ReactiveRedisTemplate<String, Book> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Book> serializer = new Jackson2JsonRedisSerializer<>(Book.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Book> builder =
                RedisSerializationContext.newSerializationContext(new Jackson2JsonRedisSerializer<>(String.class));
        RedisSerializationContext<String, Book> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
