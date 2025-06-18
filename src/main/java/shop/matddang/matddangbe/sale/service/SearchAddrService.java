package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.SearchAddr;
import shop.matddang.matddangbe.sale.repository.SearchAddrRepository;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SearchAddrService {

    private final SearchAddrRepository searchAddrRepository;

    public void saveSearchKeyword(Long userId, String keyword) {

        List<SearchAddr> existingKeywords = searchAddrRepository.findByUserIdOrderBySearchTimeAsc(userId);
        List<SearchAddr> duplicateKeywords = searchAddrRepository.findByUserIdAndKeyword(userId,keyword);

        if (!duplicateKeywords.isEmpty()){
            SearchAddr existing = duplicateKeywords.get(0); // keyword는 1개만 저장되므로 첫 번째만 처리
            existing.setSearchTime(LocalDateTime.now());// 시간 업데이트
            searchAddrRepository.save(existing);
        }else{

            if (existingKeywords.size() >= 5) {
                // 가장 오래된 것 삭제
                SearchAddr oldest = existingKeywords.get(0);
                searchAddrRepository.delete(oldest);
            }

            // 새 검색어 저장
            searchAddrRepository.save(SearchAddr.builder()
                    .userId(userId)
                    .keyword(keyword)
                    .searchTime(LocalDateTime.now())
                    .build());
        }

    }

    public List<SearchAddr> getKeywordList(Long userId) {
        return searchAddrRepository.findByUserIdOrderBySearchTimeDesc(userId);
    }

}
