package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.domain.SaleCompare;
import shop.matddang.matddangbe.sale.dto.SaleCompareResponseDto;
import shop.matddang.matddangbe.sale.dto.SaleDetailResponseDto;
import shop.matddang.matddangbe.sale.repository.SaleCompareRepository;
import shop.matddang.matddangbe.sale.repository.SaleRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class SaleCompareService {
    private final SaleCompareRepository saleCompareRepository;
    private final SaleRepository saleRepository;

    public SaleCompareResponseDto saveCompare(Long userId, Long saleId1, Long saleId2) {
        if (!saleCompareRepository.existsBySaleIds(saleId1, saleId2)) {
            // 서로 비교한 적 없는 매물만 -> 매물 비교 리스트에 추가
            saleCompareRepository.save(SaleCompare.builder()
                    .userId(userId)
                    .saleId1(saleId1)
                    .saleId2(saleId2)
                    .compareTime(LocalDate.now())
                    .build());

        }else{
            //비교한적 있다면 시간 업데이트
            SaleCompare compare = saleCompareRepository.findBySaleIds(saleId1, saleId2)
                    .orElseThrow(() -> new IllegalStateException(
                            "existsBySaleIds는 true인데 엔티티가 없습니다."));

            compare.setCompareTime(LocalDate.now());
            saleCompareRepository.save(compare);

        }
        // 3) 두 매물 정보 로드
        Optional<Sale> sale1 = saleRepository.findById(saleId1);
        Optional<Sale> sale2 = saleRepository.findById(saleId2);
        SaleDetailResponseDto saleDto1 = new SaleDetailResponseDto(
                List.of(sale1.get()),
                List.of()
        );
        SaleDetailResponseDto saleDto2 = new SaleDetailResponseDto(
                List.of(sale2.get()),
                List.of()
        );

        SaleCompareResponseDto response = new SaleCompareResponseDto(saleDto1, saleDto2, LocalDate.now());
        return response;

    }

    public List<SaleCompareResponseDto> getCompareHistory(Long userId) {
        List<SaleCompare> compares = saleCompareRepository.findByUserId(userId);
        if (compares.isEmpty()) {
            return List.of();
        }

        Set<Long> saleIdsNeeded = compares.stream()
                .flatMap(c -> Stream.of(c.getSaleId1(), c.getSaleId2()))
                .collect(Collectors.toSet());

        List<Sale> allSales = saleRepository.findBySaleIdIn(new ArrayList<>(saleIdsNeeded));
        Map<Long, Sale> saleMap = allSales.stream()
                .collect(Collectors.toMap(Sale::getSaleId, Function.identity()));

        List<SaleCompareResponseDto> responses = compares.stream()
                .map(c -> {
                    // 두 매물 정보 꺼내기
                    Sale sale1 = saleMap.get(c.getSaleId1());
                    Sale sale2 = saleMap.get(c.getSaleId2());

                    // SaleDetailResponseDto 구성 (유사매물은 없어도 됨)
                    SaleDetailResponseDto dto1 = new SaleDetailResponseDto(
                            List.of(sale1),
                            List.of()
                    );
                    SaleDetailResponseDto dto2 = new SaleDetailResponseDto(
                            List.of(sale2),
                            List.of()
                    );

                    return new SaleCompareResponseDto(dto1, dto2, c.getCompareTime());
                })
                .collect(Collectors.toList());

        return responses;
    }

}
