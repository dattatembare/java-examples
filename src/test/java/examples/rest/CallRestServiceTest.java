package examples.rest;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CallRestServiceTest {

  private final static String PCFPZAWYA = "PCFPZAWYA";
  private RestTemplate restTemplate = null;
  private MockRestServiceServer mockServer = null;
  //private OrganizationEx orgEx = null;
  private CallRestService restService = null;
  private String url = null;
  private UriComponentsBuilder builder;
  private HttpEntity<String> request = null;
  private final String success = "<IfeGatewayResponse>\n"
      + "  <statusCode>200</statusCode>\n"
      + "  <message>Success</message>\n"
      + "  <ifePayloadXml>\n"
      + "    <EntityPayload>\n"
      + "          <ORG_ALIAS_NAMES>\n"
      + "                <ORG_ALIAS_NAME>TRDeals_AKA_NONENG_1347028504</ORG_ALIAS_NAME>\n"
      + "                <ORG_ALIAS_EFF_FROM_DATE>10/02/2014</ORG_ALIAS_EFF_FROM_DATE>\n"
      + "                <ORG_ALIAS_LANG_CULTURE_CD>en-ZA</ORG_ALIAS_LANG_CULTURE_CD>\n"
      + "                <ORG_ALIAS_NAME_LANG_CD>en</ORG_ALIAS_NAME_LANG_CD>\n"
      + "                <ORG_ALIAS_NAME_LANG_SCR_CD>Latn</ORG_ALIAS_NAME_LANG_SCR_CD>\n"
      + "                <ORG_ALIAS_NORM_NAME></ORG_ALIAS_NORM_NAME>\n"
      + "                <ORG_ALIAS_ENG_NAME>TRDeals_AKA_Test_1347028504</ORG_ALIAS_ENG_NAME>\n"
      + "                <ORG_ALIAS_ENG_NORM_NAME>TRDealsAKA_NormName</ORG_ALIAS_ENG_NORM_NAME>\n"
      + "                <ORG_ALIAS_NAME_SRC></ORG_ALIAS_NAME_SRC>\n"
      + "                <ORG_ALIAS_NAME_SRC_TYPE>25</ORG_ALIAS_NAME_SRC_TYPE>\n"
      + "                <IFE_ROWID>-271661</IFE_ROWID>\n"
      + "                <ActionType>T</ActionType>\n"
      + "          </ORG_ALIAS_NAMES>\n"
      + "          <Attributes>\n"
      + "                <ORG_OFFICIAL_NAME>TRDeals_LNG_Test_1939169184</ORG_OFFICIAL_NAME>\n"
      + "                <ORG_OFF_NAME_EFF_FROM_DATE>10/04/2017</ORG_OFF_NAME_EFF_FROM_DATE>\n"
      + "                <ORG_OFFNAME_EFF_FROM_EST_FLAG></ORG_OFFNAME_EFF_FROM_EST_FLAG>\n"
      + "                <ORG_OFF_NAME_EFF_DATE_FLAG>3</ORG_OFF_NAME_EFF_DATE_FLAG>\n"
      + "                <ORG_OFFICIAL_NAME_LANG_CD>en</ORG_OFFICIAL_NAME_LANG_CD>\n"
      + "                <ORG_OFFICIAL_LANG_CULTURE_CD>en-US</ORG_OFFICIAL_LANG_CULTURE_CD>\n"
      + "                <ORG_OFFICIAL_NAME_LANG_SCR_CD>Latn</ORG_OFFICIAL_NAME_LANG_SCR_CD>\n"
      + "                <ORG_OFFICIAL_TRANS_NAME></ORG_OFFICIAL_TRANS_NAME>\n"
      + "                <ORG_OFFICIAL_NORM_NAME>TRDeals_NormName</ORG_OFFICIAL_NORM_NAME>\n"
      + "                <ORG_OFFICIAL_ENG_NAME></ORG_OFFICIAL_ENG_NAME>\n"
      + "                <ORG_OFFICIAL_ENG_NORM_NAME></ORG_OFFICIAL_ENG_NORM_NAME>\n"
      + "                <ORG_OFFICIAL_NAME_SRC></ORG_OFFICIAL_NAME_SRC>\n"
      + "                <ORG_OFFICIAL_NAME_SRC_TYPE>25</ORG_OFFICIAL_NAME_SRC_TYPE>\n"
      + "                <ORG_SHORT_ENG_NAME>TRDeals_SHT_NAME</ORG_SHORT_ENG_NAME>\n"
      + "                <SHORT_ENG_NAME_EFF_FROM_DATE>10/04/2017</SHORT_ENG_NAME_EFF_FROM_DATE>\n"
      + "                <ORG_SHTNAME_EFF_FROM_EST_FLAG></ORG_SHTNAME_EFF_FROM_EST_FLAG>\n"
      + "                <ORG_REG_NUMBER></ORG_REG_NUMBER>\n"
      + "                <ORG_REG_NUMBER_SRC></ORG_REG_NUMBER_SRC>\n"
      + "                <ORG_REG_NUMBER_SRC_TYPE></ORG_REG_NUMBER_SRC_TYPE>\n"
      + "                <SHORT_ENG_NAME_EFF_DATE_FLAG>3</SHORT_ENG_NAME_EFF_DATE_FLAG>\n"
      + "                <ORG_TYPE_CD>8</ORG_TYPE_CD>\n"
      + "                <ORG_TYPE_CD_EFF_FROM_DATE>03/08/2017</ORG_TYPE_CD_EFF_FROM_DATE>\n"
      + "                <ORG_TYPE_CD_EFF_FROM_EST_FLAG>4</ORG_TYPE_CD_EFF_FROM_EST_FLAG>\n"
      + "                <ORG_TYPE_SRC></ORG_TYPE_SRC>\n"
      + "                <ORG_TYPE_SRC_TYPE>25</ORG_TYPE_SRC_TYPE>\n"
      + "                <ORG_SUB_TYPE_CD>21</ORG_SUB_TYPE_CD>\n"
      + "                <ORG_SUBTYPECD_EFF_FROM_DATE>03/08/2017</ORG_SUBTYPECD_EFF_FROM_DATE>\n"
      + "                <ORG_SUBTYPECD_EFF_FROM_ESTFLAG>4</ORG_SUBTYPECD_EFF_FROM_ESTFLAG>\n"
      + "                <ORG_SUB_TYPE_SRC></ORG_SUB_TYPE_SRC>\n"
      + "                <ORG_SUB_TYPE_SRC_TYPE>25</ORG_SUB_TYPE_SRC_TYPE>\n"
      + "                <ORG_FOUNDED_YEAR>012017</ORG_FOUNDED_YEAR>\n"
      + "                <ORG_FOUNDED_YEAR_SRC></ORG_FOUNDED_YEAR_SRC>\n"
      + "                <ORG_FOUNDED_YEAR_SRC_TYPE>25</ORG_FOUNDED_YEAR_SRC_TYPE>\n"
      + "                <ORG_WEBSITE>http://www.thomsonreuters.com</ORG_WEBSITE>\n"
      + "                <ORG_WEBSITE_SRC></ORG_WEBSITE_SRC>\n"
      + "                <ORG_WEBSITE_SRC_TYPE>25</ORG_WEBSITE_SRC_TYPE>\n"
      + "                <ORG_IPO_DATE></ORG_IPO_DATE>\n"
      + "                <ORG_IS_PUBLIC>1</ORG_IS_PUBLIC>\n"
      + "                <ORG_IS_ACTIVE>1</ORG_IS_ACTIVE>\n"
      + "                <ORG_IS_ACTIVE_EFF_FROM_DATE>03/08/2017</ORG_IS_ACTIVE_EFF_FROM_DATE>\n"
      + "                <ORG_IS_ACTIVE_EFFFROM_EST_FLAG>4</ORG_IS_ACTIVE_EFFFROM_EST_FLAG>\n"
      + "                <ORG_INACTIVE_DATE></ORG_INACTIVE_DATE>\n"
      + "                <ORG_INACTIVE_DATE_FLAG></ORG_INACTIVE_DATE_FLAG>\n"
      + "                <ORG_INACTIVE_EVENT></ORG_INACTIVE_EVENT>\n"
      + "                <ORG_INACTIVE_EVENT_SRC></ORG_INACTIVE_EVENT_SRC>\n"
      + "                <ORG_INACTIVE_EVENT_SRC_TYPE></ORG_INACTIVE_EVENT_SRC_TYPE>\n"
      + "                <ORG_COUNTRY_OF_DOMICILE>US</ORG_COUNTRY_OF_DOMICILE>\n"
      + "                <ORG_COUNTRY_DOMICILE_SRC></ORG_COUNTRY_DOMICILE_SRC>\n"
      + "                <ORG_COUNTRY_DOMICILE_SRC_TYPE>25</ORG_COUNTRY_DOMICILE_SRC_TYPE>\n"
      + "                <ORG_IMMEDIATE_PARENT_ORGID></ORG_IMMEDIATE_PARENT_ORGID>\n"
      + "                <ORG_IMMEDIATE_PARENT_SRC></ORG_IMMEDIATE_PARENT_SRC>\n"
      + "                <ORG_IMMEDIATE_PARENT_SRC_TYPE></ORG_IMMEDIATE_PARENT_SRC_TYPE>\n"
      + "                <ORG_CP_TAX_FILE_ID></ORG_CP_TAX_FILE_ID>\n"
      + "                <ORG_CP_TAX_FILE_ID_SRC></ORG_CP_TAX_FILE_ID_SRC>\n"
      + "                <ORG_CP_TAX_FILE_ID_SRC_TYPE></ORG_CP_TAX_FILE_ID_SRC_TYPE>\n"
      + "                <ORG_ALIAS_NAMES>ORG_ALIAS_NAMES</ORG_ALIAS_NAMES>\n"
      + "                <ORG_HEADQUARTERS_ADDRESS>ORG_HEADQUARTERS_ADDRESS</ORG_HEADQUARTERS_ADDRESS>\n"
      + "                <ORG_IDENTIFIERS>ORG_IDENTIFIERS</ORG_IDENTIFIERS>\n"
      + "          </Attributes>\n"
      + "          <ORG_HEADQUARTERS_ADDRESS>\n"
      + "                <ORG_HQ_ADD_LINE1></ORG_HQ_ADD_LINE1>\n"
      + "                <ORG_HQ_ADD_LINE1_TRANS></ORG_HQ_ADD_LINE1_TRANS>\n"
      + "                <ORG_HQ_ADD_LINE1_ENG></ORG_HQ_ADD_LINE1_ENG>\n"
      + "                <ORG_HQ_ADD_LINE2></ORG_HQ_ADD_LINE2>\n"
      + "                <ORG_HQ_ADD_LINE2_TRANS></ORG_HQ_ADD_LINE2_TRANS>\n"
      + "                <ORG_HQ_ADD_LINE2_ENG></ORG_HQ_ADD_LINE2_ENG>\n"
      + "                <ORG_HQ_ADD_LINE3></ORG_HQ_ADD_LINE3>\n"
      + "                <ORG_HQ_ADD_LINE3_TRANS></ORG_HQ_ADD_LINE3_TRANS>\n"
      + "                <ORG_HQ_ADD_LINE3_ENG></ORG_HQ_ADD_LINE3_ENG>\n"
      + "                <ORG_HQ_ADD_LINE4></ORG_HQ_ADD_LINE4>\n"
      + "                <ORG_HQ_ADD_LINE4_TRANS></ORG_HQ_ADD_LINE4_TRANS>\n"
      + "                <ORG_HQ_ADD_LINE4_ENG></ORG_HQ_ADD_LINE4_ENG>\n"
      + "                <ORG_HQ_ADD_LINE5></ORG_HQ_ADD_LINE5>\n"
      + "                <ORG_HQ_ADD_LINE5_TRANS></ORG_HQ_ADD_LINE5_TRANS>\n"
      + "                <ORG_HQ_ADD_LINE5_ENG></ORG_HQ_ADD_LINE5_ENG>\n"
      + "                <ORG_HQ_ADD_LANG_CULTURE_CD>en-US</ORG_HQ_ADD_LANG_CULTURE_CD>\n"
      + "                <ORG_HQ_ADD_LANG_CD>en</ORG_HQ_ADD_LANG_CD>\n"
      + "                <ORG_HQ_ADD_LANG_SCR_CD>Latn</ORG_HQ_ADD_LANG_SCR_CD>\n"
      + "                <ORG_HQ_ADD_CITY></ORG_HQ_ADD_CITY>\n"
      + "                <ORG_HQ_ADD_CITY_TRANS></ORG_HQ_ADD_CITY_TRANS>\n"
      + "                <ORG_HQ_ADD_CITY_ENG></ORG_HQ_ADD_CITY_ENG>\n"
      + "                <ORG_HQ_ADD_STATE></ORG_HQ_ADD_STATE>\n"
      + "                <ORG_HQ_ADD_STATE_TRANS></ORG_HQ_ADD_STATE_TRANS>\n"
      + "                <ORG_HQ_ADD_STATE_ENG></ORG_HQ_ADD_STATE_ENG>\n"
      + "                <ORG_HQ_ADD_COUNTRY_CD>US</ORG_HQ_ADD_COUNTRY_CD>\n"
      + "                <ORG_HQ_ADD_COUNTRY_CD_TRANS></ORG_HQ_ADD_COUNTRY_CD_TRANS>\n"
      + "                <ORG_HQ_ADD_COUNTRY_CD_ENG></ORG_HQ_ADD_COUNTRY_CD_ENG>\n"
      + "                <ORG_HQ_ADD_POSTAL_CODE></ORG_HQ_ADD_POSTAL_CODE>\n"
      + "                <ORG_HQ_ADD_SRC></ORG_HQ_ADD_SRC>\n"
      + "                <ORG_HQ_ADD_SRC_TYPE>25</ORG_HQ_ADD_SRC_TYPE>\n"
      + "                <IFE_ROWID>-382546</IFE_ROWID>\n"
      + "                <ActionType>T</ActionType>\n"
      + "          </ORG_HEADQUARTERS_ADDRESS>\n"
      + "          <ORG_IDENTIFIERS>\n"
      + "                <ID_VALUE>51609876</ID_VALUE>\n"
      + "                <ID_TYPE>1</ID_TYPE>\n"
      + "                <IFE_ROWID>95405164</IFE_ROWID>\n"
      + "                <ActionType>D</ActionType>\n"
      + "          </ORG_IDENTIFIERS>\n"
      + "          <Overrides>\n"
      + "                <RuleID>163</RuleID>\n"
      + "          </Overrides>\n"
      + "          <Overrides>\n"
      + "                <RuleID>201</RuleID>\n"
      + "          </Overrides>\n"
      + "          <Overrides>\n"
      + "                <RuleID>221</RuleID>\n"
      + "          </Overrides>\n"
      + "          <Overrides>\n"
      + "                <RuleID>159</RuleID>\n"
      + "          </Overrides>\n"
      + "    </EntityPayload>\n"
      + "  </ifePayloadXml>\n"
      + "</IfeGatewayResponse>";

  private final String apiError = "<ApiError>"
      + "  <name>Validation failed</name>"
      + "  <status>400</status>"
      + "  <message>[Provider.PermId.value] Provider Perm Id does not exist</message>"
      + "  <id>7a0046e4-e743-45be-ae1f-c393c94146a3</id>"
      + "</ApiError>";

  @Before
  public void setUp() throws Exception {
    restService = new CallRestService();
    String orgXmlStr = "<xml-element></xml-element>";

    restTemplate = new RestTemplate();
    mockServer = MockRestServiceServer.createServer(restTemplate);

    String host = "https://datta-tembare.com";
    url = host + "/rest-service/v1/payload/generate/?";

    //set Header for post request, pass api_key
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
    headers.set("X-TR-API-APP-ID", "sec-key");
    request = new HttpEntity<String>(orgXmlStr, headers);
  }

  @Test
  public void callRestServiceSuccess() throws Exception {
    builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("masteringIntent", "OVERWRITE")
        .queryParam("providerPermId", 1002471571)
        .queryParam("defaultSourceType", 25);
    mockServer.expect(requestTo(builder.build().encode().toUri()))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(success, MediaType.APPLICATION_JSON));

    restService.restTemplate = restTemplate;
    Map<String, Object> llOrgProps = new HashMap<>();
    llOrgProps.put("ORG_ID", 123L);
    llOrgProps.put("AUTHORITY_ID", 123456L);
    llOrgProps.put("REQUEST_DISPOSITION", 309L);

    String response = restService.callRestService(PCFPZAWYA, llOrgProps);
    mockServer.verify();
    assertThat(response, CoreMatchers.containsString(success));
  }

  @Test
  public void callIfeGatewayApiError() throws Exception {
    builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("masteringIntent", "OVERWRITE")
        .queryParam("providerPermId", 100) //Incorrect permId
        .queryParam("defaultSourceType", 25);
    mockServer.expect(requestTo(builder.build().encode().toUri()))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withServerError().body(apiError));

    restService.restTemplate = restTemplate;
    Map<String, Object> llOrgProps = new HashMap<>();
    llOrgProps.put("ORG_ID", 123L);
    llOrgProps.put("AUTHORITY_ID", 123456L);
    llOrgProps.put("REQUEST_DISPOSITION", 309L);

    String response = restService.callRestService(PCFPZAWYA, llOrgProps);

    mockServer.verify();
    assertThat(response, CoreMatchers.containsString(apiError));
  }

}

