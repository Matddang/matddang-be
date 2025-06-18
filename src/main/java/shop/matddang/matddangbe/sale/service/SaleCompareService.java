package shop.matddang.matddangbe.sale.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.SaleCompare;
import shop.matddang.matddangbe.sale.domain.SearchAddr;
import shop.matddang.matddangbe.sale.repository.SaleCompareRepository;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class SaleCompareService {
    private final SaleCompareRepository saleCompareRepository;

    public void saveCompare(Long userId, Long saleId1, Long saleId2) {
        if (!saleCompareRepository.existsBySaleIds(saleId1, saleId2)) {
            // 서로 비교한 적 없는 매물만 -> 매물 비교 리스트에 추가
            saleCompareRepository.save(SaleCompare.builder()
                    .userId(userId)
                    .saleId1(saleId1)
                    .saleId2(saleId2)
                    .searchTime(LocalDateTime.now())
                    .build());

        }

    }

}
