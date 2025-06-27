package shop.matddang.matddangbe.suitableCrops.domain;

import java.io.Serializable;
import java.util.Objects;

public class SuitableCropsId implements Serializable {
    private Long saleId;
    private Long cropId;

    public SuitableCropsId() {}

    public SuitableCropsId(Long saleId, Long cropId) {
        this.saleId = saleId;
        this.cropId = cropId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuitableCropsId)) return false;
        SuitableCropsId that = (SuitableCropsId) o;
        return Objects.equals(saleId, that.saleId) &&
                Objects.equals(cropId, that.cropId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, cropId);
    }
}
