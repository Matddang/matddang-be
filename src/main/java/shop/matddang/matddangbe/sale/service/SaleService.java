package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> searchSales(SaleRequestDto requestDto) {

        // `SaleRequestDto`에 맞는 필드를 직접 전달
        return saleRepository.searchBySaleFilter(
                requestDto.getBuy(), // buy 값
                requestDto.getRent(), // rent 값
                requestDto.getMinPrice(), // 최소 가격
                requestDto.getMaxPrice(), // 최대 가격
                requestDto.getMinArea(), // 최소 면적
                requestDto.getMaxArea(), // 최대 면적
                requestDto.getLandCategory_d(), // 답
                requestDto.getLandCategory_j(), // 전
                requestDto.getLandCategory_g() // 과수원
        );
    }
}
