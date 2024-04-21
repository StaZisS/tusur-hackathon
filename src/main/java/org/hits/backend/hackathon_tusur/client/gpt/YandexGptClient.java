package org.hits.backend.hackathon_tusur.client.gpt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class YandexGptClient implements GptClient {
    private static final String GPT_PROMPT = "Необходимо в ответ на увлечения человеком различными занятиями, хобби, выдать список товаров, которые могли бы ему понравится в качестве подарка на день рождения.";

    private final WebClient webClient;
    private final String modelUri;

    @Override
    public String generateText(String text) {
        var response = webClient.post()
                .bodyValue(createRequest(text))
                .retrieve()
                .bodyToMono(GptResponse.class)
                .block();
        return response.result().alternatives()[0].message().text();
    }

    private GptRequest createRequest(String text) {
        return new GptRequest(
                modelUri,
                new GptRequest.MessageRequestDto[]{
                        new GptRequest.MessageRequestDto(GPT_PROMPT, "system"),
                        new GptRequest.MessageRequestDto(text, "user")
                },
                new GptRequest.CompletionOptionsRequestDto(
                        false,
                        "500",
                        "0.4"
                )
        );
    }
}
