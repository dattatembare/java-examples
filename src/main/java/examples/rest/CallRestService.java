package examples.rest;

import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CallRestService {

  private static final Logger log = LoggerFactory.getLogger(CallRestService.class);
  protected RestTemplate restTemplate = new RestTemplate();

  public String callRestService(String info, Map<String, Object> moreInfo)
      throws IOException {
    String response = null;

    restTemplate.setErrorHandler(new ResponseErrorHandler());

    //Marshalling Organization object
    String orgXmlStr = "<xml-element></xml-element>";

    //Pull the host url from properties file.
    String host = "https://datta-tembare.com";
    String url = host + "/rest-service/v1/payload/generate/?";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("masteringIntent", "someValue")
        .queryParam("providerPermId", 123456)
        .queryParam("defaultSourceType", "value");

    //set Header for post request, pass api_key
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
    headers.set("X-TR-API-APP-ID", "sec-key");
    HttpEntity<String> request = new HttpEntity<String>(orgXmlStr, headers);

    //Call IFE Gateway service
    log.info("Calling Rest service: {} and for Org xml: {}", url, orgXmlStr);
    try {
      response = restTemplate
          .postForObject(builder.build().encode().toUri(), request, String.class);

    } catch (RestClientException e) {
      response = e.getMessage();
    }
    return response;
  }
}
