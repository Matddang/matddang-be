package shop.matddang.matddangbe.crop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.crop.domain.Crop;
import shop.matddang.matddangbe.crop.repository.CropRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CropService {

    private final CropRepository cropRepository;


    public List<Crop> findAllByCropIdIn(List<Long> cropIds) {
        return cropRepository.findAllByCropIdIn(cropIds);
    }


}
