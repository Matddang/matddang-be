package shop.matddang.matddangbe.sale.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.matddang.matddangbe.sale.service.SaleService;

@Tag(name="Post", description = "매물 로드")
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class SaleApiController {
    private final SaleService saleService;

}
