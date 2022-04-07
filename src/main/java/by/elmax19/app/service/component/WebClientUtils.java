package by.elmax19.app.service.component;

import by.elmax19.app.model.dto.UriParameterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class WebClientUtils {
    private final WebClient webClient;

    public <C> List<C> getListByParameters(String path, List<UriParameterDto> params, Class<C> entityClass) {
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
}
