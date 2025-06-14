package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;
import shop.matddang.matddangbe.sale.repository.SuitableCropsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SuitableCropsRepository suitableCropsRepository;  // 생성자 주입으로 변경

    public List<Sale> findAllById(Iterable<Long> saleIdList) {
        return saleRepository.findAllById(saleIdList);
    }

    public List<Sale> searchSales(SaleRequestDto requestDto) {

        return saleRepository.searchBySaleFilter(
                requestDto.getBuy(), // 매매
                requestDto.getRent(), // 임대
                requestDto.getMinPrice(), // 최소 가격
                requestDto.getMaxPrice(), // 최대 가격
                requestDto.getMinArea(), // 최소 면적
                requestDto.getMaxArea(), // 최대 면적
                requestDto.getLandCategory_d(), // 답
                requestDto.getLandCategory_j(), // 전
                requestDto.getLandCategory_g() // 과수원
        );
    }

    public List<SuitableCrops> findBySaleIdInAndCropIdIn(List<Long> saleIds, List<Long> cropIds) {
        return suitableCropsRepository.findBySaleIdInAndCropIdIn(saleIds, cropIds);
    }

}
