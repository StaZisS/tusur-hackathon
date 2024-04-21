package org.hits.backend.hackathon_tusur.rest.inventor;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.client.gpt.GptClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventor")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Придумщик", description = "Энпоинт подойдет для придумывания идей для подарка")
public class InventorController {
    private final GptClient gptClient;

    @GetMapping
    public String getOffers(@RequestParam String text) {
        return gptClient.generateText(text);
    }
}
