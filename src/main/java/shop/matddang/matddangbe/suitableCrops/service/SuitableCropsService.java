package shop.matddang.matddangbe.suitableCrops.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.repository.SuitableCropsRepository;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SuitableCropsService {
    private final SuitableCropsRepository suitableCropsRepository;

    public Set<SuitableCrops> findAllBySaleId(Long saleId) {
        return suitableCropsRepository.findAllBySaleId(saleId);
    }
}
