package org.hits.backend.hackathon_tusur.rest.affiliate;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.rest.admin.AffiliateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/affiliate")
@Tag(name = "Affiliate", description = "Test controller for working with affiliate")
public class AffiliateController {
    private final AffiliateService affiliateService;

    @GetMapping
    public List<AffiliateResponse> getAffiliates(@RequestParam("affiliate_name") String affiliateName) {
        var response = affiliateService.getAffiliatesByName(affiliateName);
        return response.stream()
                .map(this::mapToAffiliateResponse)
                .toList();
    }

    private AffiliateResponse mapToAffiliateResponse(AffiliateDto dto) {
        return new AffiliateResponse(
                dto.id(),
                dto.name(),
                dto.address()
        );
    }
}
