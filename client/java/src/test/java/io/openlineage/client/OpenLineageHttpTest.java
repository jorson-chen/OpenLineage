package io.openlineage.client;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.URI;
import java.net.URL;
import java.util.Map;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link OpenLineageHttp}. */
public class OpenLineageHttpTest {
  @Test
  public void testHttp_overrideUrlWithQueryParams() throws Exception {
    final URI uri =
        new URIBuilder("http://localhost:5000/api/v1/test")
            .addParameter("param0", "value0")
            .addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
    final URL expected = uri.toURL();

    // URL with query params appended on each HTTP request
    final URI uriWithQueryParams =
        new URIBuilder("http://localhost:5000")
            .addParameter("param0", "value0")
            .addParameter("param1", "value1")
            .build();
    final URL urlWithQueryParams = uriWithQueryParams.toURL();
    final OpenLineageHttp http = OpenLineageHttp.create(urlWithQueryParams, null);

    // URL with query params combined
    final Map<String, Object> httpQueryParams = Map.of("param2", "value2");
    final URL actual = http.url("/test", httpQueryParams);

    assertThat(actual).isEqualTo(expected);
  }
}