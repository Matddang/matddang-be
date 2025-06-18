package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.SaleCompare;
import shop.matddang.matddangbe.sale.domain.SearchAddr;
import shop.matddang.matddangbe.sale.dto.SaleCompareResponseDto;
import shop.matddang.matddangbe.sale.repository.SaleCompareRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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
                    .compareTime(LocalDate.now())
                    .build());

        }

    }

    public List<SaleCompareResponseDto> getCompareHistory(Long userId) {
        List<SaleCompare> compares = saleCompareRepository.findByUserId(userId);

        return compares.stream()
                .map(c -> new SaleCompareResponseDto(
                        c.getSaleId1(),
                        c.getSaleId2(),
                        c.getCompareTime()
                ))
                .collect(Collectors.toList());

    }

}
