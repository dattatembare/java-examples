package examples.rest;


import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class ResponseErrorHandler extends DefaultResponseErrorHandler {

  private static final Logger log = LoggerFactory.getLogger(ResponseErrorHandler.class);

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    //conversion logic for decoding conversion
    InputStream arrayInputStream = response.getBody();
    Scanner scanner = new Scanner(arrayInputStream);
    scanner.useDelimiter("\\Z");
    String errorMessage = "";
    if (scanner.hasNext()) {
      errorMessage = scanner.next();
    }
    log.info("Actual response from IFE Gateway: {}", errorMessage);
    throw new RestClientException(errorMessage);
  }
}

