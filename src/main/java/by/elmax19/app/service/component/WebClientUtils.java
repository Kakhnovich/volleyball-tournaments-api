package by.elmax19.app.service.component;

import by.elmax19.app.model.dto.UriParameterDto;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@Component
public class WebClientUtils {
    private WebClient webClient;

    public <T> List<T> getListByParameters(String baseUrl, String path, List<UriParameterDto> params, Class<T> entityClass) {
        initWebClient(baseUrl);
        Function<UriBuilder, URI> uriFunction = uriBuilder -> {
            UriBuilder uri = uriBuilder
                    .path(path);
            params.forEach(param -> uri.queryParam(param.getName(), param.getValue()));
            return uri.build();
        };

        return webClient.get()
                .uri(uriFunction)
                .retrieve()
                .bodyToFlux(entityClass)
                .collectList()
                .block();
    }

    private void initWebClient(String baseUrl) {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
