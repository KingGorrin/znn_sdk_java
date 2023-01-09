package network.zenon.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.jsoniter.any.Any;

import network.zenon.Constants;
import network.zenon.TestHelper;
import network.zenon.api.embedded.AcceleratorApi;
import network.zenon.api.embedded.HtlcApi;
import network.zenon.api.embedded.PillarApi;
import network.zenon.api.embedded.PlasmaApi;
import network.zenon.api.embedded.SentinelApi;
import network.zenon.api.embedded.SporkApi;
import network.zenon.api.embedded.StakeApi;
import network.zenon.api.embedded.SwapApi;
import network.zenon.api.embedded.TokenApi;
import network.zenon.client.Client;
import network.zenon.model.embedded.DelegationInfo;
import network.zenon.model.embedded.FusionEntryList;
import network.zenon.model.embedded.GetRequiredParam;
import network.zenon.model.embedded.GetRequiredResponse;
import network.zenon.model.embedded.HtlcInfo;
import network.zenon.model.embedded.HtlcInfoList;
import network.zenon.model.embedded.Phase;
import network.zenon.model.embedded.PillarEpochHistoryList;
import network.zenon.model.embedded.PillarInfo;
import network.zenon.model.embedded.PillarInfoList;
import network.zenon.model.embedded.PillarVote;
import network.zenon.model.embedded.PlasmaInfo;
import network.zenon.model.embedded.Project;
import network.zenon.model.embedded.ProjectList;
import network.zenon.model.embedded.RewardHistoryList;
import network.zenon.model.embedded.SentinelInfo;
import network.zenon.model.embedded.SentinelInfoList;
import network.zenon.model.embedded.SporkList;
import network.zenon.model.embedded.StakeList;
import network.zenon.model.embedded.SwapAssetEntry;
import network.zenon.model.embedded.SwapLegacyPillarEntry;
import network.zenon.model.embedded.UncollectedReward;
import network.zenon.model.embedded.VoteBreakdown;
import network.zenon.model.nom.AccountBlock;
import network.zenon.model.nom.AccountBlockList;
import network.zenon.model.nom.BlockTypeEnum;
import network.zenon.model.nom.Momentum;
import network.zenon.model.nom.MomentumList;
import network.zenon.model.nom.TokenList;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.JsonConverter;

public class ApiTest {
    public class DefaultClientRequest implements ClientRequest {
        private final String methodName;
        private final Object params;

        public DefaultClientRequest(String methodName, Object params) {
            this.methodName = methodName;
            this.params = params;
        }

        @Override
        public void validate(String methodName, Object params) {
            assertEquals(this.methodName, methodName);
            assertEquals(Any.wrap(this.params), Any.wrap(params));
        }
    }

    public interface ClientRequest {
        void validate(String methodName, Object params);
    }

    public interface ClientResponse {
        String invoke() throws IOException;
    }

    public class TestClient implements Client {
        private ClientRequest request;
        private ClientResponse response;

        public TestClient() {
            this.response = new ClientResponse() {
                @Override
                public String invoke() {
                    return null;
                }
            };
        }

        @Override
        public <R> R sendRequest(String method, Object params, Class<R> resultClass) {
            this.request.validate(method, params);
            try {
                return com.jsoniter.JsonIterator.deserialize(this.response.invoke(), resultClass);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public Object sendRequest(String method, Object params) {
            this.request.validate(method, params);
            try {
                String response = this.response.invoke();
                return com.jsoniter.JsonIterator.deserialize(response);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        public void sendNotification(String method, Object params) {
            throw new UnsupportedOperationException();
        }

        public TestClient withRequest(String methodName, Object params) {
            this.request = new DefaultClientRequest(methodName, params);
            return this;
        }

        public TestClient withResponse(String response) {
            this.response = new ClientResponse() {
                @Override
                public String invoke() {
                    return response;
                }
            };
            return this;
        }

        public TestClient withNullResponse() {
            this.response = new ClientResponse() {
                @Override
                public String invoke() {
                    return null;
                }
            };
            return this;
        }

        public TestClient withEmptyResponse() {
            this.response = new ClientResponse() {
                @Override
                public String invoke() {
                    return "{ }";
                }
            };
            return this;
        }

        public TestClient withExceptionResponse(IOException ex) {
            this.response = new ClientResponse() {
                @Override
                public String invoke() throws IOException {
                    throw ex;
                }
            };
            return this;
        }

        public TestClient withResourceTextResponse(String resourceName) {
            this.response = new ClientResponse() {
                @Override
                public String invoke() throws IOException {
                    return TestHelper.getRosourceText(resourceName);
                }
            };
            return this;
        }
    }

    @Nested
    public class Spork {
        @Nested
        public class GetAll {
            private final String methodName;

            public GetAll() {
                this.methodName = "embedded.spork.getAll";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "0, 1024" })
            public void emptyResponse(int pageIndex, int pageSize) {
                // Setup
                SporkApi api = new SporkApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                SporkList result = api.getAll(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "0, 1024, 'api/embedded/spork/getAll.json'" })
            public void listResponse(int pageIndex, int pageSize, String resourceName) {
                // Setup
                SporkApi api = new SporkApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                SporkList result = api.getAll(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }
    }
    
    @Nested
    public class Htlc {
        @Nested
        public class GetHtlcInfoById {
            private final String methodName;

            public GetHtlcInfoById() {
                this.methodName = "embedded.htlc.getHtlcInfoById";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'11ac76e40cc23674300f68ca87f5ebeb7210fc327fd43f35081b75a839c9c632', 'api/embedded/htlc/getHtlcInfoById.json'" })
            public void singleResponse(String id, String resourceName) {
                // Setup
                Hash hash = Hash.parse(id);
                HtlcApi api = new HtlcApi(
                        new TestClient().withRequest(this.methodName, new Object[] { hash.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                HtlcInfo result = api.getHtlcInfoById(hash);

                // Validate
                assertNotNull(result);
                assertEquals(hash, result.getId());
            }
        }

        @Nested
        public class GetHtlcInfosByTimeLockedAddress {
            private final String methodName;

            public GetHtlcInfosByTimeLockedAddress() {
                this.methodName = "embedded.htlc.getHtlcInfosByTimeLockedAddress";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                HtlcApi api = new HtlcApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                HtlcInfoList result = api.getHtlcInfosByTimeLockedAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7', 0, 1024, 'api/embedded/htlc/getHtlcInfosByTimeLockedAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                HtlcApi api = new HtlcApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                HtlcInfoList result = api.getHtlcInfosByTimeLockedAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetHtlcInfosByHashLockedAddress {
            private final String methodName;

            public GetHtlcInfosByHashLockedAddress() {
                this.methodName = "embedded.htlc.getHtlcInfosByHashLockedAddress";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                HtlcApi api = new HtlcApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                HtlcInfoList result = api.getHtlcInfosByHashLockedAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqjnwjjpnue8xmmpanz6csze6tcmtzzdtfsww7', 0, 1024, 'api/embedded/htlc/getHtlcInfosByHashLockedAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                HtlcApi api = new HtlcApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                HtlcInfoList result = api.getHtlcInfosByHashLockedAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }
    }
    
    @Nested
    public class Accelerator {
        @Nested
        public class GetAll {
            private final String methodName;

            public GetAll() {
                this.methodName = "embedded.accelerator.getAll";
            }

            @Test
            @DisplayName("Empty Response")
            public void emptyResponse() {
                // Setup
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { 0, Constants.RPC_MAX_PAGE_SIZE })
                                .withEmptyResponse());

                // Execute
                ProjectList result = api.getAll();

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "0, 1024, 'api/embedded/accelerator/getAll.json'" })
            public void listResponse(int pageIndex, int pageSize, String resourceName) {
                // Setup
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                ProjectList result = api.getAll();

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetProjectById {
            private final String methodName;

            public GetProjectById() {
                this.methodName = "embedded.accelerator.getProjectById";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'2acf48da2bcc420b4d997299807d8b50b4229871214069c9afc39bdad0e27a76', 'api/embedded/accelerator/getProjectById.json'" })
            public void singleResponse(String id, String resourceName) {
                // Setup
                Hash hash = Hash.parse(id);
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { hash.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                Project result = api.getProjectById(hash);

                // Validate
                assertNotNull(result);
                assertEquals(hash, result.getId());
            }
        }

        @Nested
        public class GetPhaseById {
            private final String methodName;

            public GetPhaseById() {
                this.methodName = "embedded.accelerator.getPhaseById";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'54946aa46c4f8d4bb5a98b2aca8e90311d9388f2d47ab754ad012cb5cd40448d', 'api/embedded/accelerator/getPhaseById.json'" })
            public void singleResponse(String id, String resourceName) {
                // Setup
                Hash hash = Hash.parse(id);
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { hash.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                Phase result = api.getPhaseById(hash);

                // Validate
                assertNotNull(result);
                assertEquals(hash, result.getId());
            }
        }

        @Nested
        public class GetPillarVotes {
            private final String methodName;

            public GetPillarVotes() {
                this.methodName = "embedded.accelerator.getPillarVotes";
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "'',  [], 'api/embedded/accelerator/getPillarVotes.json'" })
            public void ListResponse(String name, @ConvertWith(JsonConverter.class) String[] hashes,
                    String resourceName) {
                // Setup
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { name, hashes })
                                .withResourceTextResponse(resourceName));

                // Execute
                List<PillarVote> result = api.getPillarVotes(name, hashes);

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetVoteBreakdown {
            private final String methodName;

            public GetVoteBreakdown() {
                this.methodName = "embedded.accelerator.getVoteBreakdown";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'54946aa46c4f8d4bb5a98b2aca8e90311d9388f2d47ab754ad012cb5cd40448d', 'api/embedded/accelerator/getVoteBreakdown.json'" })
            public void singleResponse(String id, String resourceName) {
                // Setup
                Hash hash = Hash.parse(id);
                AcceleratorApi api = new AcceleratorApi(
                        new TestClient().withRequest(this.methodName, new Object[] { hash.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                VoteBreakdown result = api.getVoteBreakdown(hash);

                // Validate
                assertNotNull(result);
                assertEquals(hash, result.getId());
            }
        }
    }

    @Nested
    public class Pillar {
        @Nested
        public class GetDepositedQsr {
            private final String methodName;

            public GetDepositedQsr() {
                this.methodName = "embedded.pillar.getDepositedQsr";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqz642m6sk9qpa4896etljzv6phlspmx3ctkl6', '100'" })
            public void singleResponse(String address, String response) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString() }).withResponse(response));

                // Execute
                long result = api.getDepositedQsr(addr);

                // Validate
                assertEquals(100, result);
            }
        }

        @Nested
        public class GetUncollectedReward {
            private final String methodName;

            public GetUncollectedReward() {
                this.methodName = "embedded.pillar.getUncollectedReward";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'z1qprz0pjd7tykyxsycr2l4escty4nm2p5f8ct6f', 'api/embedded/pillar/getUncollectedReward.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                UncollectedReward result = api.getUncollectedReward(addr);

                // Validate
                assertNotNull(result);
                assertEquals(addr, result.getAddress());
            }
        }

        @Nested
        public class GetFrontierRewardByPage {
            private final String methodName;

            public GetFrontierRewardByPage() {
                this.methodName = "embedded.pillar.getFrontierRewardByPage";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024, 'api/embedded/pillar/getFrontierRewardByPage.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetQsrRegistrationCost {
            private final String methodName;

            public GetQsrRegistrationCost() {
                this.methodName = "embedded.pillar.getQsrRegistrationCost";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "25000000000000" })
            public void singleResponse(String response) {
                // Setup
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, null).withResponse(response));

                // Execute
                long result = api.getQsrRegistrationCost();

                // Validate
                assertEquals(25000000000000L, result);
            }
        }

        @Nested
        public class GetAll {
            private final String methodName;

            public GetAll() {
                this.methodName = "embedded.pillar.getAll";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "0, 1024" })
            public void emptyResponse(int pageIndex, int pageSize) {
                // Setup
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { pageIndex, pageSize }).withEmptyResponse());

                // Execute
                PillarInfoList result = api.getAll(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "0, 1024, 'api/embedded/pillar/getAll.json'" })
            public void listResponse(int pageIndex, int pageSize, String resourceName) {
                // Setup
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                PillarInfoList result = api.getAll(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetByOwner {
            private final String methodName;

            public GetByOwner() {
                this.methodName = "embedded.pillar.getByOwner";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qzlaadsmar8pm0rdfwkctvxc8n2g5gaadxvmqj'" })
            public void emptyResponse(String address) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString() }).withResponse("[]"));

                // Execute
                List<PillarInfo> result = api.getByOwner(addr);

                // Validate
                assertNotNull(result);
                assertTrue(result.isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "'z1qzlaadsmar8pm0rdfwkctvxc8n2g5gaadxvmqj', 'api/embedded/pillar/getByOwner.json'" })
            public void listResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                List<PillarInfo> result = api.getByOwner(addr);

                // Validate
                assertNotNull(result);
                assertFalse(result.isEmpty());
            }
        }

        @Nested
        public class GetByName {
            private final String methodName;

            public GetByName() {
                this.methodName = "embedded.pillar.getByName";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'SultanOfStaking', 'api/embedded/pillar/getByName.json'" })
            public void singleResponse(String name, String resourceName) {
                // Setup
                PillarApi api = new PillarApi(new TestClient().withRequest(this.methodName, new Object[] { name })
                        .withResourceTextResponse(resourceName));

                // Execute
                PillarInfo result = api.getByName(name);

                // Validate
                assertNotNull(result);
                assertEquals(name, result.getName());
            }
        }

        @Nested
        public class CheckNameAvailability {
            private final String methodName;

            public CheckNameAvailability() {
                this.methodName = "embedded.pillar.checkNameAvailability";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'AllYourPillarBelongToUs', 'true'" })
            public void singleResponse(String name, String response) {
                // Setup
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { name }).withResponse(response));

                // Execute
                boolean result = api.checkNameAvailability(name);

                // Validate
                assertTrue(result);
            }
        }

        @Nested
        public class GetDelegatedPillar {
            private final String methodName;

            public GetDelegatedPillar() {
                this.methodName = "embedded.pillar.getDelegatedPillar";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qzlaadsmar8pm0rdfwkctvxc8n2g5gaadxvmqj', 'api/embedded/pillar/getDelegatedPillar.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { address.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                DelegationInfo result = api.getDelegatedPillar(addr);

                // Validate
                assertNotNull(result);
                assertNotNull(result.getName());
                assertFalse(result.getName().isEmpty());
            }
        }

        @Nested
        public class GetPillarEpochHistory {
            private final String methodName;

            public GetPillarEpochHistory() {
                this.methodName = "embedded.pillar.getPillarEpochHistory";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'SultanOfStaking', 0, 1024" })
            public void emptyResponse(String name, int pageIndex, int pageSize) {
                // Setup
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { name, pageIndex, pageSize }).withEmptyResponse());

                // Execute
                PillarEpochHistoryList result = api.getPillarEpochHistory(name, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "'SultanOfStaking', 0, 1024, 'api/embedded/pillar/getPillarEpochHistory.json'" })
            public void listResponse(String name, int pageIndex, int pageSize, String resourceName) {
                // Setup
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { name, pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                PillarEpochHistoryList result = api.getPillarEpochHistory(name, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetPillarsHistoryByEpoch {
            private final String methodName;

            public GetPillarsHistoryByEpoch() {
                this.methodName = "embedded.pillar.getPillarsHistoryByEpoch";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "140, 0, 1024" })
            public void EmptyResponseAsync(int epoch, int pageIndex, int pageSize) {
                // Setup
                PillarApi api = new PillarApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { epoch, pageIndex, pageSize }).withEmptyResponse());

                // Execute
                PillarEpochHistoryList result = api.getPillarsHistoryByEpoch(epoch, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "140, 0, 1024, 'api/embedded/pillar/getPillarsHistoryByEpoch.json'" })
            public void listResponse(int epoch, int pageIndex, int pageSize, String resourceName) {
                // Setup
                PillarApi api = new PillarApi(
                        new TestClient().withRequest(this.methodName, new Object[] { epoch, pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                PillarEpochHistoryList result = api.getPillarsHistoryByEpoch(epoch, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }
    }

    @Nested
    public class Plasma {
        @Nested
        public class Get {
            private final String methodName;

            public Get() {
                this.methodName = "embedded.plasma.get";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', 'api/embedded/plasma/get.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PlasmaApi api = new PlasmaApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                PlasmaInfo result = api.get(addr);

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetEntriesByAddress {
            private final String methodName;

            public GetEntriesByAddress() {
                this.methodName = "embedded.plasma.getEntriesByAddress";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                PlasmaApi api = new PlasmaApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                FusionEntryList result = api.getEntriesByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', 0, 1024, 'api/embedded/plasma/getEntriesByAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                PlasmaApi api = new PlasmaApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                FusionEntryList result = api.getEntriesByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetPlasmaByQsr {
            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "100.21, 210441" })
            public void singleResponse(double qsrAmount, long expectedResult) {
                // Setup
                PlasmaApi api = new PlasmaApi(new TestClient());

                // Execute
                long result = api.getPlasmaByQsr(qsrAmount);

                // Validate
                assertEquals(expectedResult, result);
            }
        }

        @Nested
        public class GetRequiredPoWForAccountBlock {
            public GetRequiredPoWForAccountBlock() {
                this.methodName = "embedded.plasma.getRequiredPoWForAccountBlock";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', USER_SEND, 'z1qzawsrc0mu5kz7lvrfj2mmr4scuernadycqz30', [], 'api/embedded/plasma/getRequiredPoWForAccountBlock.json'" })
            public void singleResponse(String address, BlockTypeEnum blockType, String toAddress,
                    @ConvertWith(JsonConverter.class) byte[] data, String resourceName) {
                // Setup
                GetRequiredParam param = new GetRequiredParam(Address.parse(address), blockType,
                        Address.parse(toAddress), data);
                PlasmaApi api = new PlasmaApi(
                        new TestClient().withRequest(this.methodName, new Object[] { param.toJson() })
                                .withResourceTextResponse(resourceName));

                // Execute
                GetRequiredResponse result = api.getRequiredPoWForAccountBlock(param);

                // Validate
                assertNotNull(result);
            }
        }
    }

    @Nested
    public class Sentinel {
        @Nested
        public class GetAllActive {
            private final String methodName;

            public GetAllActive() {
                this.methodName = "embedded.sentinel.getAllActive";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "0, 1024" })
            public void emptyResponse(int pageIndex, int pageSize) {
                // Setup
                SentinelApi api = new SentinelApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { pageIndex, pageSize }).withEmptyResponse());

                // Execute
                SentinelInfoList result = api.getAllActive(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "0, 1024, 'api/embedded/sentinel/getAllActive.json'" })
            public void listResponse(int pageIndex, int pageSize, String resourceName) {
                // Setup
                SentinelApi api = new SentinelApi(
                        new TestClient().withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                SentinelInfoList result = api.getAllActive(pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetByOwner {
            private final String methodName;

            public GetByOwner() {
                this.methodName = "embedded.sentinel.getByOwner";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqz642m6sk9qpa4896etljzv6phlspmx3ctkl6', 'api/embedded/sentinel/getByOwner.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                SentinelApi api = new SentinelApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                SentinelInfo result = api.getByOwner(addr);

                // Validate
                assertNotNull(result);
                assertEquals(addr, result.getOwner());
            }
        }

        @Nested
        public class GetDepositedQsr {
            private final String methodName;

            public GetDepositedQsr() {
                this.methodName = "embedded.sentinel.getDepositedQsr";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqz642m6sk9qpa4896etljzv6phlspmx3ctkl6', 0" })
            public void singleResponse(String address, String response) {
                // Setup
                Address addr = Address.parse(address);
                SentinelApi api = new SentinelApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString() }).withResponse(response));

                // Execute
                long result = api.getDepositedQsr(addr);

                // Validate
                assertEquals(0, result);
            }
        }

        @Nested
        public class GetUncollectedReward {
            private final String methodName;

            public GetUncollectedReward() {
                this.methodName = "embedded.sentinel.getUncollectedReward";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'z1qrg4nt69dakvw9wc3pmcvnuax70uj04wt4dxk4', 'api/embedded/sentinel/getUncollectedReward.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                SentinelApi api = new SentinelApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                UncollectedReward result = api.getUncollectedReward(addr);

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetFrontierRewardByPage {
            private final String methodName;

            public GetFrontierRewardByPage() {
                this.methodName = "embedded.sentinel.getFrontierRewardByPage";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qrg4nt69dakvw9wc3pmcvnuax70uj04wt4dxk4', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                SentinelApi api = new SentinelApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qrg4nt69dakvw9wc3pmcvnuax70uj04wt4dxk4', 0, 1024, 'api/embedded/sentinel/getFrontierRewardByPage.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                SentinelApi api = new SentinelApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }
    }

    @Nested
    public class Stake {
        @Nested
        public class GetEntriesByAddress {
            private final String methodName;

            public GetEntriesByAddress() {
                this.methodName = "embedded.stake.getEntriesByAddress";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                StakeApi api = new StakeApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                StakeList result = api.getEntriesByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024, 'api/embedded/stake/getEntriesByAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                StakeApi api = new StakeApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                StakeList result = api.getEntriesByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetFrontierRewardByPage {
            private final String methodName;

            public GetFrontierRewardByPage() {
                this.methodName = "embedded.stake.getFrontierRewardByPage";
            }

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                StakeApi api = new StakeApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 1024, 'api/embedded/stake/getFrontierRewardByPage.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                StakeApi api = new StakeApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                RewardHistoryList result = api.getFrontierRewardByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetUncollectedReward {
            private final String methodName;

            public GetUncollectedReward() {
                this.methodName = "embedded.stake.getUncollectedReward";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 'api/embedded/stake/getUncollectedReward.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                StakeApi api = new StakeApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                UncollectedReward result = api.getUncollectedReward(addr);

                // Validate
                assertNotNull(result);
                assertEquals(addr, result.getAddress());
            }
        }
    }

    @Nested
    public class Swap {
        @Nested
        public class GetAssets {
            private final String methodName;

            public GetAssets() {
                this.methodName = "embedded.swap.getAssets";
            }

            @Test
            @DisplayName("Empty Response")
            public void emptyResponse() {
                // Setup
                SwapApi api = new SwapApi(new TestClient().withRequest(this.methodName, null).withEmptyResponse());

                // Execute
                List<SwapAssetEntry> result = api.getAssets();

                // Validate
                assertNotNull(result);
                assertTrue(result.isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "'api/embedded/swap/getAssets.json'" })
            public void ListResponse(String resourceName) {
                // Setup
                SwapApi api = new SwapApi(
                        new TestClient().withRequest(this.methodName, null).withResourceTextResponse(resourceName));

                // Execute
                List<SwapAssetEntry> result = api.getAssets();

                // Validate
                assertNotNull(result);
                assertFalse(result.isEmpty());
            }
        }

        @Nested
        public class GetAssetsByKeyIdHash {
            private final String methodName;

            public GetAssetsByKeyIdHash() {
                this.methodName = "embedded.swap.getAssetsByKeyIdHash";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'ee383852b44821e633bb46d9eb1d6e62a5629b5c78c7a1f4958026c4bbef1d41', 'api/embedded/swap/getAssetsByKeyIdHash.json'" })
            public void singleResponse(String keyIdHash, String resourceName) {
                // Setup
                Hash hash = Hash.parse(keyIdHash);
                SwapApi api = new SwapApi(
                        new TestClient().withRequest(this.methodName, new Object[] { hash.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                SwapAssetEntry result = api.getAssetsByKeyIdHash(hash);

                // Validate
                assertNotNull(result);
                assertEquals(hash, result.getKeyIdHash());
            }
        }

        @Nested
        public class GetLegacyPillars {
            private final String methodName;

            public GetLegacyPillars() {
                this.methodName = "embedded.swap.getLegacyPillars";
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'api/embedded/swap/getLegacyPillars.json'" })
            public void ArrayResponseAsync(String resourceName) {
                // Setup
                SwapApi api = new SwapApi(
                        new TestClient().withRequest(this.methodName, null).withResourceTextResponse(resourceName));

                // Execute
                List<SwapLegacyPillarEntry> result = api.getLegacyPillars();

                // Validate
                assertNotNull(result);
                assertFalse(result.isEmpty());
            }
        }
    }

    @Nested
    public class Token {
        @Nested
        public class GetAll {
            private final String methodName;

            public GetAll() {
                this.methodName = "embedded.token.getAll";
            }

            @Test
            @DisplayName("Empty Response")
            public void emptyResponse() {
                // Setup
                TokenApi api = new TokenApi(
                        new TestClient().withRequest(this.methodName, new Object[] { 0, Constants.RPC_MAX_PAGE_SIZE })
                                .withEmptyResponse());

                // Execute
                TokenList result = api.getAll();

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "0, 1024, 'api/embedded/token/getAll.json'" })
            public void listResponse(int pageIndex, int pageSize, String resourceName) {
                // Setup
                TokenApi api = new TokenApi(
                        new TestClient().withRequest(this.methodName, new Object[] { pageIndex, pageSize })
                                .withResourceTextResponse(resourceName));

                // Execute
                TokenList result = api.getAll();

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetByOwner {
            public GetByOwner() {
                this.methodName = "embedded.token.getByOwner";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "'z1qpgdtn89u9365jr7ltdxu29fy52pnzwe4fl7zc', 0, 1024, 'api/embedded/token/getByOwner.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                TokenApi api = new TokenApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                TokenList result = api.getByOwner(addr);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetByZts {
            public GetByZts() {
                this.methodName = "embedded.token.getByZts";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'zts10esqvg76658fhzg9t5ctd3', 'api/embedded/token/getByZts.json'" })
            public void singleResponse(String tokenStandard, String resourceName) {
                // Setup
                TokenStandard zts = TokenStandard.parse(tokenStandard);
                TokenApi api = new TokenApi(
                        new TestClient().withRequest(this.methodName, new Object[] { zts.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                network.zenon.model.nom.Token result = api.getByZts(zts);

                // Validate
                assertNotNull(result);
            }
        }
    }

    @Nested
    public class Ledger {
        @Nested
        public class GetUnconfirmedBlocksByAddress {
            public GetUnconfirmedBlocksByAddress() {
                this.methodName = "ledger.getUnconfirmedBlocksByAddress";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                AccountBlockList result = api.getUnconfirmedBlocksByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqylpfyyrj5t8pe99n4nf4x634vxse96fg7sge', 0, 1024, 'api/ledger/getUnconfirmedBlocksByAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                AccountBlockList result = api.getUnconfirmedBlocksByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetUnreceivedBlocksByAddress {
            public GetUnreceivedBlocksByAddress() {
                this.methodName = "ledger.getUnreceivedBlocksByAddress";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qq6g4jptwwjmp0ym38n0juru2zzygap6r42pc0', 0, 1024" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                AccountBlockList result = api.getUnreceivedBlocksByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qq6g4jptwwjmp0ym38n0juru2zzygap6r42pc0', 0, 1024, 'api/ledger/getUnreceivedBlocksByAddress.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                AccountBlockList result = api.getUnreceivedBlocksByAddress(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetFrontierAccountBlock {
            public GetFrontierAccountBlock() {
                this.methodName = "ledger.getFrontierAccountBlock";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 'api/ledger/getFrontierAccountBlock.json'" })
            public void singleResponse(String address, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, new Object[] { addr.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                AccountBlock result = api.getFrontierAccountBlock(addr);

                // Validate
                assertNotNull(result);
                assertEquals(addr, result.getAddress());
            }
        }

        @Nested
        public class GetAccountBlockByHash {
            public GetAccountBlockByHash() {
                this.methodName = "ledger.getAccountBlockByHash";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'7bb60617d15329480db329c1f32066c45e3b1e767d171502494ae82e35e69409', 'api/ledger/getAccountBlockByHash.json'" })
            public void singleResponse(String hash, String resourceName) {
                // Setup
                Hash h = Hash.parse(hash);
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, new Object[] { h.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                AccountBlock result = api.getAccountBlockByHash(h);

                // Validate
                assertNotNull(result);
                assertEquals(h, result.getHash());
            }
        }

        @Nested
        public class GetAccountBlocksByHeight {
            public GetAccountBlocksByHeight() {
                this.methodName = "ledger.getAccountBlocksByHeight";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 500, 50" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                AccountBlockList result = api.getAccountBlocksByHeight(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 500, 50, 'api/ledger/getAccountBlocksByHeight.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));
                // Execute
                AccountBlockList result = api.getAccountBlocksByHeight(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        @Nested
        public class GetAccountBlocksByPage {
            public GetAccountBlocksByPage() {
                this.methodName = "ledger.getAccountBlocksByPage";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 10" })
            public void emptyResponse(String address, int pageIndex, int pageSize) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withEmptyResponse());

                // Execute
                AccountBlockList result = api.getAccountBlocksByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({
                    "'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y', 0, 10, 'api/ledger/getAccountBlocksByPage.json'" })
            public void listResponse(String address, int pageIndex, int pageSize, String resourceName) {
                // Setup
                Address addr = Address.parse(address);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { addr.toString(), pageIndex, pageSize })
                        .withResourceTextResponse(resourceName));

                // Execute
                AccountBlockList result = api.getAccountBlocksByPage(addr, pageIndex, pageSize);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }

        public class GetFrontierMomentum {
            public GetFrontierMomentum() {
                this.methodName = "ledger.getFrontierMomentum";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "'api/ledger/getFrontierMomentum.json'" })
            public void singleResponse(String resourceName) {
                // Setup
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, null).withResourceTextResponse(resourceName));

                // Execute
                Momentum result = api.getFrontierMomentum();

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetMomentumBeforeTime {
            public GetMomentumBeforeTime() {
                this.methodName = "ledger.getMomentumBeforeTime";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Null Response")
            @CsvSource({ "0" })
            public void nullResponse(long time) {
                // Setup
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, new Object[] { time }).withNullResponse());

                // Execute
                Momentum result = api.getMomentumBeforeTime(time);

                // Validate
                assertNull(result);
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({ "1652798090, 'api/ledger/getMomentumBeforeTime.json'" })
            public void singleResponse(long time, String resourceName) {
                // Setup
                LedgerApi api = new LedgerApi(new TestClient().withRequest(this.methodName, new Object[] { time })
                        .withResourceTextResponse(resourceName));

                // Execute
                Momentum result = api.getMomentumBeforeTime(time);

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetMomentumByHash {
            public GetMomentumByHash() {
                this.methodName = "ledger.getMomentumByHash";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Null Response")
            @CsvSource({ "'230506e0ec25306a70aa5b685e68dc791b4eb7c41202b9eb813336f21d54a637'" })
            public void nullResponse(String hash) {
                // Setup
                Hash h = Hash.parse(hash);
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { h.toString() }).withNullResponse());

                // Execute
                Momentum result = api.getMomentumByHash(h);

                // Validate
                assertNull(result);
            }

            @ParameterizedTest
            @DisplayName("Single Response")
            @CsvSource({
                    "'230506e0ec25306a70aa5b685e68dc791b4eb7c41202b9eb813336f21d54a637', 'api/ledger/getMomentumByHash.json'" })
            public void singleResponse(String hash, String resourceName) {
                // Setup
                Hash h = Hash.parse(hash);
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, new Object[] { h.toString() })
                                .withResourceTextResponse(resourceName));

                // Execute
                Momentum result = api.getMomentumByHash(h);

                // Validate
                assertNotNull(result);
            }
        }

        @Nested
        public class GetMomentumsByHeight {
            public GetMomentumsByHeight() {
                this.methodName = "ledger.getMomentumsByHeight";
            }

            private final String methodName;

            @ParameterizedTest
            @DisplayName("Empty Response")
            @CsvSource({ "140, 10" })
            public void emptyResponse(long height, long count) {
                // Setup
                LedgerApi api = new LedgerApi(new TestClient()
                        .withRequest(this.methodName, new Object[] { height, count }).withEmptyResponse());

                // Execute
                MomentumList result = api.getMomentumsByHeight(height, count);

                // Validate
                assertNotNull(result);
                assertEquals(0, result.getCount());
                assertTrue(result.getList().isEmpty());
            }

            @ParameterizedTest
            @DisplayName("List Response")
            @CsvSource({ "140, 10, 'api/ledger/getMomentumsByHeight.json'" })
            public void listResponse(long height, long count, String resourceName) {
                // Setup
                LedgerApi api = new LedgerApi(
                        new TestClient().withRequest(this.methodName, new Object[] { height, count })
                                .withResourceTextResponse(resourceName));

                // Execute
                MomentumList result = api.getMomentumsByHeight(height, count);

                // Validate
                assertNotNull(result);
                assertTrue(result.getCount() > 0);
                assertFalse(result.getList().isEmpty());
            }
        }
    }
}