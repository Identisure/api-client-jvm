package se.idkollen.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.idkollen.client.models.AgeVerificationRequest;
import se.idkollen.client.models.AgeVerificationStatus;
import se.idkollen.client.models.BankIdNoAuthRequest;
import se.idkollen.client.models.BankIdNoSignRequest;
import se.idkollen.client.models.BankIdNoStatus;
import se.idkollen.client.models.BankIdSeAuthRequest;
import se.idkollen.client.models.BankIdSePhoneAuthRequest;
import se.idkollen.client.models.BankIdSePhoneStatus;
import se.idkollen.client.models.BankIdSeSignRequest;
import se.idkollen.client.models.BankIdSeStatus;
import se.idkollen.client.models.BankIdSeVerifyRequest;
import se.idkollen.client.models.FrejaAuthRequest;
import se.idkollen.client.models.FrejaSignRequest;
import se.idkollen.client.models.FrejaStatus;
import se.idkollen.client.models.FtnAuthRequest;
import se.idkollen.client.models.FtnStatus;
import se.idkollen.client.models.MitIdAuthRequest;
import se.idkollen.client.models.MitIdSignRequest;
import se.idkollen.client.models.MitIdStatus;
import se.idkollen.client.models.VippsAuthRequest;
import se.idkollen.client.models.VippsStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EndToEndTest {
    private static final Path FIXTURES = Path.of(System.getProperty("idkollen.fixtures", "../../fixtures"));
    private static final PollOptions FAST_POLL = new PollOptions(1, 5_000);
    private static final String AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString("cid:secret".getBytes());

    // Annotations off so models serialize as their plain public fields for comparison with fixtures.
    private static final ObjectMapper PLAIN = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();

    private MockWebServer server;
    private IdkollenClient client;

    record Flow(
        String fixtureDir,
        String path,
        Function<IdkollenClient, CompletableFuture<?>> start,
        BiFunction<IdkollenClient, String, CompletableFuture<?>> waitFor,
        Class<?> pending,
        Class<?> completed,
        Class<?> failed,
        Class<?> unknown
    ) {
        @Override
        public String toString() {
            return path;
        }
    }

    static Stream<Flow> authFlows() {
        return Stream.of(
            new Flow(
                "bankid_se", "/v3/bankid-se/auth",
                c -> c.bankIdSe().auth(new BankIdSeAuthRequest().setRefId("order-42")),
                (c, id) -> c.bankIdSe().waitForAuth(id, FAST_POLL),
                BankIdSeStatus.Pending.class, BankIdSeStatus.Completed.class,
                BankIdSeStatus.Failed.class, BankIdSeStatus.Unknown.class
            ),
            new Flow(
                "bankid_no", "/v3/bankid-no/auth",
                c -> c.bankIdNo().auth(new BankIdNoAuthRequest().setRefId("order-42")),
                (c, id) -> c.bankIdNo().waitForAuth(id, FAST_POLL),
                BankIdNoStatus.Pending.class, BankIdNoStatus.Completed.class,
                BankIdNoStatus.Failed.class, BankIdNoStatus.Unknown.class
            ),
            new Flow(
                "freja", "/v3/freja/auth",
                c -> c.freja().auth(new FrejaAuthRequest().setRefId("order-42")),
                (c, id) -> c.freja().waitForAuth(id, FAST_POLL),
                FrejaStatus.Pending.class, FrejaStatus.Completed.class,
                FrejaStatus.Failed.class, FrejaStatus.Unknown.class
            ),
            new Flow(
                "mitid", "/v3/mitid/auth",
                c -> c.mitId().auth(new MitIdAuthRequest().setRefId("order-42")),
                (c, id) -> c.mitId().waitForAuth(id, FAST_POLL),
                MitIdStatus.Pending.class, MitIdStatus.Completed.class,
                MitIdStatus.Failed.class, MitIdStatus.Unknown.class
            ),
            new Flow(
                "ftn", "/v3/ftn/auth",
                c -> c.ftn().auth(new FtnAuthRequest().setRefId("order-42")),
                (c, id) -> c.ftn().waitForAuth(id, FAST_POLL),
                FtnStatus.Pending.class, FtnStatus.Completed.class,
                FtnStatus.Failed.class, FtnStatus.Unknown.class
            ),
            new Flow(
                "vipps", "/v3/vipps/auth",
                c -> c.vipps().auth(new VippsAuthRequest().setRefId("order-42")),
                (c, id) -> c.vipps().waitForAuth(id, FAST_POLL),
                VippsStatus.Pending.class, VippsStatus.Completed.class,
                VippsStatus.Failed.class, VippsStatus.Unknown.class
            )
        );
    }

    static Stream<Flow> signFlows() {
        return Stream.of(
            new Flow(
                "bankid_se", "/v3/bankid-se/sign",
                c -> c.bankIdSe().sign(new BankIdSeSignRequest().setText("Sign this").setRefId("order-42")),
                (c, id) -> c.bankIdSe().waitForSign(id, FAST_POLL),
                BankIdSeStatus.Pending.class, BankIdSeStatus.Completed.class,
                BankIdSeStatus.Failed.class, BankIdSeStatus.Unknown.class
            ),
            new Flow(
                "bankid_no", "/v3/bankid-no/sign",
                c -> c.bankIdNo().sign(new BankIdNoSignRequest().setText("Sign this").setRefId("order-42")),
                (c, id) -> c.bankIdNo().waitForSign(id, FAST_POLL),
                BankIdNoStatus.Pending.class, BankIdNoStatus.Completed.class,
                BankIdNoStatus.Failed.class, BankIdNoStatus.Unknown.class
            ),
            new Flow(
                "freja", "/v3/freja/sign",
                c -> c.freja().sign(new FrejaSignRequest().setText("Sign this").setRefId("order-42")),
                (c, id) -> c.freja().waitForSign(id, FAST_POLL),
                FrejaStatus.Pending.class, FrejaStatus.Completed.class,
                FrejaStatus.Failed.class, FrejaStatus.Unknown.class
            ),
            new Flow(
                "mitid", "/v3/mitid/sign",
                c -> c.mitId().sign(new MitIdSignRequest().setText("Sign this").setRefId("order-42")),
                (c, id) -> c.mitId().waitForSign(id, FAST_POLL),
                MitIdStatus.Pending.class, MitIdStatus.Completed.class,
                MitIdStatus.Failed.class, MitIdStatus.Unknown.class
            )
        );
    }

    @BeforeEach
    void startServer() throws IOException {
        server = new MockWebServer();
        server.start();
        client = new IdkollenClientBuilder("cid", "secret")
            .baseUrl(server.url("/").toString())
            .build();
    }

    @AfterEach
    void stopServer() throws IOException {
        server.shutdown();
    }

    @ParameterizedTest
    @MethodSource("authFlows")
    void authCompletes(Flow flow) throws Exception {
        var completed = runFlow(flow, "auth_pending.json", "auth_completed.json");

        assertInstanceOf(flow.completed(), completed);
        assertMatchesFixture(fixture(flow.fixtureDir() + "/auth_completed.json"), completed);
    }

    @ParameterizedTest
    @MethodSource("authFlows")
    void authFails(Flow flow) throws Exception {
        var failed = runFlow(flow, "auth_pending.json", "auth_failed.json");

        assertInstanceOf(flow.failed(), failed);
        assertEquals("CANCELLED", plain(failed).get("error").asText());
        assertMatchesFixture(fixture(flow.fixtureDir() + "/auth_failed.json"), failed);
    }

    @ParameterizedTest
    @MethodSource("authFlows")
    void authReturnsUnknownStatus(Flow flow) throws Exception {
        var unknown = runFlow(flow, "auth_pending.json", "auth_unknown.json");

        assertInstanceOf(flow.unknown(), unknown);
        assertEquals("SOMETHING_NEW", plain(unknown).get("status").asText());
    }

    @ParameterizedTest
    @MethodSource("signFlows")
    void signCompletes(Flow flow) throws Exception {
        var completed = runFlow(flow, "auth_pending.json", "sign_completed.json");

        assertInstanceOf(flow.completed(), completed);
        assertMatchesFixture(fixture(flow.fixtureDir() + "/sign_completed.json"), completed);
    }

    @Test
    void bankIdSePhoneAuthCompletes() throws Exception {
        enqueue(201, "bankid_se/phone_auth_pending.json");
        enqueue(200, "bankid_se/phone_auth_pending.json");
        enqueue(200, "bankid_se/auth_completed.json");

        var started = client.bankIdSe()
            .phoneAuth(new BankIdSePhoneAuthRequest().setSsn("199001011239"))
            .get(5, TimeUnit.SECONDS);
        var pending = assertInstanceOf(BankIdSePhoneStatus.Pending.class, started);
        var result = client.bankIdSe().waitForAuth(pending.id, FAST_POLL).get(5, TimeUnit.SECONDS);

        assertRequest("POST", "/v3/bankid-se/phone/auth");
        assertRequest("GET", "/v3/bankid-se/auth/" + pending.id);
        assertRequest("GET", "/v3/bankid-se/auth/" + pending.id);
        assertInstanceOf(BankIdSeStatus.Completed.class, result);
        assertMatchesFixture(fixture("bankid_se/auth_completed.json"), result);
    }

    @Test
    void bankIdSeVerify() throws Exception {
        enqueue(200, "bankid_se/verify.json");

        var result = client.bankIdSe()
            .verify(new BankIdSeVerifyRequest().setQrCode("qr-code"))
            .get(5, TimeUnit.SECONDS);

        assertRequest("POST", "/v3/bankid-se/verify");
        assertMatchesFixture(fixture("bankid_se/verify.json"), result);
    }

    @Test
    void ageVerificationCompletes() throws Exception {
        var result = runAgeVerification("completed.json");

        assertInstanceOf(AgeVerificationStatus.Completed.class, result);
        assertMatchesFixture(fixture("age_verification/completed.json"), result);
    }

    @Test
    void ageVerificationFails() throws Exception {
        var result = runAgeVerification("failed.json");

        assertInstanceOf(AgeVerificationStatus.Failed.class, result);
        assertMatchesFixture(fixture("age_verification/failed.json"), result);
    }

    @Test
    void ageVerificationReturnsUnknownStatus() throws Exception {
        var result = runAgeVerification("unknown.json");

        assertInstanceOf(AgeVerificationStatus.Unknown.class, result);
        assertEquals("SOMETHING_NEW", plain(result).get("status").asText());
    }

    @Test
    void documentUpload() throws Exception {
        enqueue(201, "document/upload.json");

        var result = client.document()
            .upload("%PDF-1.7".getBytes(), "contract.pdf")
            .get(5, TimeUnit.SECONDS);

        var request = assertRequest("POST", "/v3/document");
        assertTrue(request.getHeader("Content-Type").startsWith("multipart/form-data"));
        assertMatchesFixture(fixture("document/upload.json"), result);
    }

    @Test
    void apiErrorSurfacesAsIdkollenError() throws Exception {
        enqueue(400, "error.json");

        var thrown = assertThrows(
            CompletionException.class,
            () -> client.ftn().auth(new FtnAuthRequest()).join()
        );

        var error = assertInstanceOf(IdkollenError.class, thrown.getCause());
        assertEquals("api", error.code);
        assertEquals(400, error.status);
        assertTrue(error.getMessage().contains("Invalid request"));
        assertRequest("POST", "/v3/ftn/auth");
    }

    private Object runFlow(Flow flow, String pendingFixture, String resultFixture) throws Exception {
        var dir = flow.fixtureDir();
        enqueue(201, dir + "/" + pendingFixture);
        enqueue(200, dir + "/" + pendingFixture);
        enqueue(200, dir + "/" + resultFixture);

        var started = flow.start().apply(client).get(5, TimeUnit.SECONDS);
        assertInstanceOf(flow.pending(), started);
        assertMatchesFixture(fixture(dir + "/" + pendingFixture), started);

        var id = plain(started).get("id").asText();
        var result = flow.waitFor().apply(client, id).get(5, TimeUnit.SECONDS);

        var start = assertRequest("POST", flow.path());
        assertEquals("order-42", PLAIN.readTree(start.getBody().readUtf8()).get("refId").asText());
        assertRequest("GET", flow.path() + "/" + id);
        assertRequest("GET", flow.path() + "/" + id);

        return result;
    }

    private Object runAgeVerification(String resultFixture) throws Exception {
        enqueue(201, "age_verification/pending.json");
        enqueue(200, "age_verification/pending.json");
        enqueue(200, "age_verification/" + resultFixture);

        var started = client.bankIdSe()
            .ageVerification(new AgeVerificationRequest().setMinAge(18))
            .get(5, TimeUnit.SECONDS);
        var pending = assertInstanceOf(AgeVerificationStatus.Pending.class, started);
        assertMatchesFixture(fixture("age_verification/pending.json"), pending);

        var result = client.bankIdSe().waitForAgeVerification(pending.id, FAST_POLL).get(5, TimeUnit.SECONDS);

        assertRequest("POST", "/v3/bankid-se/age-verification");
        assertRequest("GET", "/v3/bankid-se/age-verification/" + pending.id);
        assertRequest("GET", "/v3/bankid-se/age-verification/" + pending.id);

        return result;
    }

    private void enqueue(int status, String fixturePath) throws IOException {
        server.enqueue(new MockResponse()
            .setResponseCode(status)
            .setHeader("Content-Type", "application/json")
            .setBody(Files.readString(FIXTURES.resolve(fixturePath))));
    }

    private RecordedRequest assertRequest(String method, String path) throws InterruptedException {
        var request = server.takeRequest(5, TimeUnit.SECONDS);
        assertEquals(method + " " + path, request.getMethod() + " " + request.getPath());
        assertEquals(AUTHORIZATION, request.getHeader("Authorization"));
        return request;
    }

    private static JsonNode fixture(String path) throws IOException {
        return PLAIN.readTree(FIXTURES.resolve(path).toFile());
    }

    private static JsonNode plain(Object value) {
        return PLAIN.valueToTree(value);
    }

    private static void assertMatchesFixture(JsonNode expected, Object actual) {
        var node = plain(actual);
        expected.properties().forEach(field -> {
            if (!field.getKey().equals("status")) {
                assertEquals(field.getValue(), node.get(field.getKey()), field.getKey());
            }
        });
    }
}
