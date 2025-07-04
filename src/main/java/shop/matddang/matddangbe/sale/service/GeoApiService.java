package shop.matddang.matddangbe.sale.service;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.repository.SaleRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class GeoApiService {
    private final SaleRepository saleRepository;

    private final RestTemplate restTemplate;

    @Value("${external.api.vworld.url}")
    private String apiUrl;

    @Value("${external.api.vworld.key}")
    private String apiKey;

    public GeoApiService(RestTemplate restTemplate, SaleService saleService, SaleRepository saleRepository) {
        this.restTemplate = restTemplate;
        this.saleRepository = saleRepository;
    }

    public Double[] getGeoCoordFromAddress(String address) {
        String searchType = "road"; // 기본 타입
        String epsg = "epsg:4326";

        for (int i = 0; i < 2; i++) {
            try {
                String urlStr = String.format(
                        "https://api.vworld.kr/req/address?service=address&request=getCoord&format=json&crs=%s&key=%s&type=%s&address=%s",
                        epsg,
                        apiKey,
                        searchType,
                        URLEncoder.encode(address, StandardCharsets.UTF_8)
                );

                URL url = new URL(urlStr);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(reader);
                    if (!(obj instanceof net.minidev.json.JSONObject)) {
                        throw new RuntimeException("Invalid response format");
                    }

                    net.minidev.json.JSONObject jsob = (net.minidev.json.JSONObject) obj;
                    net.minidev.json.JSONObject jsrs = (net.minidev.json.JSONObject) jsob.get("response");

                    if (!jsrs.containsKey("result")) {
                        throw new RuntimeException("No result found");
                    }

                    net.minidev.json.JSONObject jsResult = (net.minidev.json.JSONObject) jsrs.get("result");
                    net.minidev.json.JSONObject point = (net.minidev.json.JSONObject) jsResult.get("point");

                    Double x = Double.parseDouble(point.getAsString("x"));
                    Double y = Double.parseDouble(point.getAsString("y"));

                    return new Double[]{x, y};
                }

            } catch (IOException | ParseException | RuntimeException e) {
                if (i == 0) {
                    // 첫 번째 시도 실패 시 타입을 'parcel'로 바꾸고 재시도
                    searchType = "parcel";
                } else {
                    return new Double[]{(double)0, (double) 0};
                }
            }
        }
        return new Double[]{(double)0, (double) 0};
    }

    // 주소 기반 위도, 경도 추출
    public void LocationSaveService(List<Sale> saleList) {
        for (Sale sale : saleList) {
            if (sale.getWgsX() == null || sale.getWgsX() == 0L) {
                Double[] coords = getGeoCoordFromAddress(sale.getSaleAddr());

                if (coords != null && coords.length == 2) {
                    sale.setWgsX(coords[0]);
                    sale.setWgsY(coords[1]);
                } else {
                    // 예외 처리로 0으로 설정
                    sale.setWgsX((double) 0);
                    sale.setWgsY((double) 0);
                }
                saleRepository.save(sale);
            }
        }
    }
}