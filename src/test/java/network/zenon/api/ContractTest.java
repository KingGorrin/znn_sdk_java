package network.zenon.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import network.zenon.TestHelper;
import network.zenon.api.embedded.*;
import network.zenon.client.Client;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;

public class ContractTest {
    public class TestClient implements Client {

        public TestClient() {
        }

        @Override
        public <R> R sendRequest(String method, Object params, Class<R> resultClass) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object sendRequest(String method, Object params) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sendNotification(String method, Object params) {
            throw new UnsupportedOperationException();
        }
    }

    public class EmbeddedApiFixture {
        private final EmbeddedApi api;

        public EmbeddedApiFixture() {
            this.api = new EmbeddedApi(new TestClient());
        }

        public EmbeddedApi getApi() {
            return this.api;
        }
    }

    @Nested
    public class Spork {
        private SporkApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getSpork();
        }

        @Test
        public void whenCreateSporkExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsp0rkxxxxxxxxxxxxxxxx956u48", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "tgLjEQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACVRlc3RTcG9yawAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABRUaGlzIGlzIGEgdGVzdCBzcG9yawAAAAAAAAAAAAAAAA==");

            // Execute
            AccountBlockTemplate block = this.api.createSpork(
                    "TestSpork", "This is a test spork");

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
        
        @Test
        public void whenActivateSporkExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsp0rkxxxxxxxxxxxxxxxx956u48", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "JcVOllnkOgCVs2M3kRjJ4oOEQtwrHqWWJLGK6txc1mFWB5Qe");

            // Execute
            AccountBlockTemplate block = this.api.activateSpork(
                    Hash.parse("59e43a0095b363379118c9e2838442dc2b1ea59624b18aeadc5cd6615607941e"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }
    
    @Nested
    public class Htlc {
        private HtlcApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getHtlc();
        }

        @Test
        public void whenCreateHtlcExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxhtlcxxxxxxxxxxxxxxxxxygecvw", "zts1znnxxxxxxxxxxxxx9z4ulx", 10000000000L,
                    "GIDoZgAAAAAAAAAAAAAAAABhJkXCFzgm8ajy065dg9QtXqD7AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGNs2EoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIN5UOmyrjbW9wIbRcguXsPCXRYhBzQJk14k1DjsHWH9b");

            // Execute
            AccountBlockTemplate block = this.api.createHtlc(
                    TokenStandard.ZNN_ZTS, 10000000000L,
                    Address.parse("z1qpsjv3wzzuuzdudg7tf6uhvr6sk4ag8me42ua4"), 1668077642L, 
                    0, 32, BytesUtils.fromHexString("de543a6cab8db5bdc086d1720b97b0f097458841cd0264d789350e3b07587f5b"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
        
        @Test
        public void whenReclaimHtlcExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxhtlcxxxxxxxxxxxxxxxxxygecvw", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "2KuUoVnkOgCVs2M3kRjJ4oOEQtwrHqWWJLGK6txc1mFWB5Qe");

            // Execute
            AccountBlockTemplate block = this.api.reclaimHtlc(
                    Hash.parse("59e43a0095b363379118c9e2838442dc2b1ea59624b18aeadc5cd6615607941e"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
        
        @Test
        public void whenUnlockHtlcExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxhtlcxxxxxxxxxxxxxxxxxygecvw", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "CyR9cZ9uMIjiaH5kL0wPPyn7IZEfpcoaumbcBUP7xu7gFmTtAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGWFsbCB5b3VyIHpubiBiZWxvbmcgdG8gdXMAAAAAAAAA");

            // Execute
            AccountBlockTemplate block = this.api.unlockHtlc(
                    Hash.parse("9f6e3088e2687e642f4c0f3f29fb21911fa5ca1aba66dc0543fbc6eee01664ed"),
                    "all your znn belong to us".getBytes());

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }
    
    @Nested
    public class Accelerator {
        private AcceleratorApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getAccelerator();
        }

        @Test
        public void whenCreateProjectExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(100000000,
                    "d8BEtgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHRqUogAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEjCc5UAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAC1Rlc3RQcm9qZWN0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABdUaGlzIGlzIGEgdGVzdCBwcm9qZWN0LgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASaHR0cDovL2NvbnRvc28uY29tAAAAAAAAAAAAAAAAAAA=");

            // Execute
            AccountBlockTemplate block = this.api.createProject("TestProject", "This is a test project.",
                    "http://contoso.com", 500000000000L, 5000000000000L);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenAddPhaseExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(0,
                    "x+E93G3mL6f0xYfRv9un3QDR8bphbwGcFLEDZ8t+ePcWW2HwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdGpSiAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASMJzlQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALVGVzdFByb2plY3QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAF1RoaXMgaXMgYSB0ZXN0IHByb2plY3QuAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABJodHRwOi8vY29udG9zby5jb20AAAAAAAAAAAAAAAAAAA==");
            ;

            // Execute
            AccountBlockTemplate block = this.api.addPhase(
                    Hash.parse("6de62fa7f4c587d1bfdba7dd00d1f1ba616f019c14b10367cb7e78f7165b61f0"), "TestProject",
                    "This is a test project.", "http://contoso.com", 500000000000L, 5000000000000L);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenUpdatePhaseExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(0,
                    "wdfTI23mL6f0xYfRv9un3QDR8bphbwGcFLEDZ8t+ePcWW2HwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdGpSiAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASMJzlQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALVGVzdFByb2plY3QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAF1RoaXMgaXMgYSB0ZXN0IHByb2plY3QuAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABJodHRwOi8vY29udG9zby5jb20AAAAAAAAAAAAAAAAAAA==");
            ;

            // Execute
            AccountBlockTemplate block = this.api.updatePhase(
                    Hash.parse("6de62fa7f4c587d1bfdba7dd00d1f1ba616f019c14b10367cb7e78f7165b61f0"), "TestProject",
                    "This is a test project.", "http://contoso.com", 500000000000L, 5000000000000L);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenDonateExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(50000000000L, "y3+LKg==");

            // Execute
            AccountBlockTemplate block = this.api.donate(50000000000L, TokenStandard.ZNN_ZTS);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenVoteByNameExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(0,
                    "XGwQZG3mL6f0xYfRv9un3QDR8bphbwGcFLEDZ8t+ePcWW2HwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHQ29udG9zbwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=");
            ;

            // Execute
            AccountBlockTemplate block = this.api.voteByName(
                    Hash.parse("6de62fa7f4c587d1bfdba7dd00d1f1ba616f019c14b10367cb7e78f7165b61f0"), "Contoso", 1);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenVoteByProdAddressExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(0,
                    "kO0AHG3mL6f0xYfRv9un3QDR8bphbwGcFLEDZ8t+ePcWW2HwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAE=");

            // Execute
            AccountBlockTemplate block = this.api.voteByProdAddress(
                    Hash.parse("6de62fa7f4c587d1bfdba7dd00d1f1ba616f019c14b10367cb7e78f7165b61f0"), 1);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Pillar {
        private PillarApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getPillar();
        }

        @Test
        public void whenRegisterExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 1500000000000L,
                    "ZE3pJwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACgAAAAAAAAAAAAAAAAAJKCmPF9nrfyBcVaEn+x1LNaMHIAAAAAAAAAAAAAAAAAkoKY8X2et/IFxVoSf7HUs1owcgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB0NvbnRvc28AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            // Execute
            AccountBlockTemplate block = this.api.register("Contoso",
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"),
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"), 60, 60);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenRegisterLegacyExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 1500000000000L,
                    "5FiCBwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADgAAAAAAAAAAAAAAAAAJKCmPF9nrfyBcVaEn+x1LNaMHIAAAAAAAAAAAAAAAAAkoKY8X2et/IFxVoSf7HUs1owcgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdDb250b3NvAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsT0x1UlVrdzg2bDh1Q2RubTBpRzBKMktwQUxpUVVqaFFFZmx2dXQ1M0hTVT0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABYL2dmYTI2UFA0YXV6c2xScEh2TEZ4cVM0MHF4MG1vM2wrVEx3cWQ4RDB2QlIzOWI0Y2dSLzFGQlJubFFiSVhhQ04zTC8vUE51TVBVRGRienE1cEdFQ1E9PQAAAAAAAAAA");

            // Execute
            AccountBlockTemplate block = this.api.registerLegacy("Contoso",
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"),
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"),
                    "OLuRUkw86l8uCdnm0iG0J2KpALiQUjhQEflvut53HSU=",
                    "/gfa26PP4auzslRpHvLFxqS40qx0mo3l+TLwqd8D0vBR39b4cgR/1FBRnlQbIXaCN3L//PNuMPUDdbzq5pGECQ==", 60, 60);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenUpdatePillarExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "3grjSwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACgAAAAAAAAAAAAAAAAAJKCmPF9nrfyBcVaEn+x1LNaMHIAAAAAAAAAAAAAAAAAkoKY8X2et/IFxVoSf7HUs1owcgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB0NvbnRvc28AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            // Execute
            AccountBlockTemplate block = this.api.updatePillar("Contoso",
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"),
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"), 60, 60);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenRevokeExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "lWMTBgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdDb250b3NvAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==");

            // Execute
            AccountBlockTemplate block = this.api.revoke("Contoso");

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenDelegateExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "fC1dbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdDb250b3NvAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==");

            // Execute
            AccountBlockTemplate block = this.api.delegate("Contoso");

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenUndelegateExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "folSyA==");

            // Execute
            AccountBlockTemplate block = this.api.undelegate();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenCollectRewardExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "r0PT8A==");

            // Execute
            AccountBlockTemplate block = this.api.collectReward();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenDepositQsrExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1qsrxxxxxxxxxxxxxmrhjll", 5000, "1JV39A==");

            // Execute
            AccountBlockTemplate block = this.api.depositQsr(5000);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenWithdrawQsrExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "s9ZY/Q==");

            // Execute
            AccountBlockTemplate block = this.api.withdrawQsr();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Plasma {
        private PlasmaApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getPlasma();
        }

        @Test
        public void whenFuseExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxplasmaxxxxxxxxxxxxxxxxsctrp", "zts1qsrxxxxxxxxxxxxxmrhjll", 5000,
                    "WslC6AAAAAAAAAAAAAAAAACSgpjxfZ638gXFWhJ/sdSzWjBy");

            // Execute
            AccountBlockTemplate block = this.api.fuse(Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"), 5000);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenCancelExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxplasmaxxxxxxxxxxxxxxxxsctrp", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "+cqdw+RDjuO7nxgX9KH1v0RAB2s4nTCjuz0GTzn6LJlVNtjV");

            // Execute
            AccountBlockTemplate block = this.api
                    .cancel(Hash.parse("e4438ee3bb9f1817f4a1f5bf4440076b389d30a3bb3d064f39fa2c995536d8d5"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Sentinel {
        private SentinelApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getSentinel();
        }

        @Test
        public void whenRegisterExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r", "zts1znnxxxxxxxxxxxxx9z4ulx", 500000000000L,
                    "TdI1Fw==");

            // Execute
            AccountBlockTemplate block = this.api.register();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenRevokeExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "WDY+JA==");

            // Execute
            AccountBlockTemplate block = this.api.revoke();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenCollectRewardExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "r0PT8A==");

            // Execute
            AccountBlockTemplate block = this.api.collectReward();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenDepositQsrExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r", "zts1qsrxxxxxxxxxxxxxmrhjll", 5000, "1JV39A==");

            // Execute
            AccountBlockTemplate block = this.api.depositQsr(5000);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenWithdrawQsrExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "s9ZY/Q==");

            // Execute
            AccountBlockTemplate block = this.api.withdrawQsr();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Stake {
        private StakeApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getStake();
        }

        @Test
        public void whenStakeExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxstakexxxxxxxxxxxxxxxxjv8v62", "zts1znnxxxxxxxxxxxxx9z4ulx", 5000,
                    "2AKEWgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ40A");

            // Execute
            AccountBlockTemplate block = this.api.stake(2592000, 5000);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenCancelExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxstakexxxxxxxxxxxxxxxxjv8v62", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "WpL+MuRDjuO7nxgX9KH1v0RAB2s4nTCjuz0GTzn6LJlVNtjV");

            // Execute
            AccountBlockTemplate block = this.api
                    .cancel(Hash.parse("e4438ee3bb9f1817f4a1f5bf4440076b389d30a3bb3d064f39fa2c995536d8d5"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenUndelegateExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxstakexxxxxxxxxxxxxxxxjv8v62", "zts1znnxxxxxxxxxxxxx9z4ulx", 0, "r0PT8A==");

            // Execute
            AccountBlockTemplate block = this.api.collectReward();

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Swap {
        private SwapApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getSwap();
        }

        @Test
        public void whenRetrieveAssetsExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxswapxxxxxxxxxxxxxxxxxxl4yww", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "R/EsgQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALE9MdVJVa3c4Nmw4dUNkbm0waUcwSjJLcEFMaVFVamhRRWZsdnV0NTNIU1U9AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWC9nZmEyNlBQNGF1enNsUnBIdkxGeHFTNDBxeDBtbzNsK1RMd3FkOEQwdkJSMzliNGNnUi8xRkJSbmxRYklYYUNOM0wvL1BOdU1QVURkYnpxNXBHRUNRPT0AAAAAAAAAAA==");

            // Execute
            AccountBlockTemplate block = this.api.retrieveAssets("OLuRUkw86l8uCdnm0iG0J2KpALiQUjhQEflvut53HSU=",
                    "/gfa26PP4auzslRpHvLFxqS40qx0mo3l+TLwqd8D0vBR39b4cgR/1FBRnlQbIXaCN3L//PNuMPUDdbzq5pGECQ==");

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }

    @Nested
    public class Token {
        private TokenApi api;

        @BeforeEach
        public void initFixture() {
            this.api = new EmbeddedApiFixture().getApi().getToken();
        }

        @Test
        public void whenIssueTokenExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0", "zts1znnxxxxxxxxxxxxx9z4ulx", 100000000,
                    "vEELkQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH//////////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf/////////8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHQ29udG9zbwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA0NUUwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABJodHRwOi8vY29udG9zby5jb20AAAAAAAAAAAAAAAAAAA==");

            // Execute
            AccountBlockTemplate block = this.api.issueToken("Contoso", "CTS", "http://contoso.com", Long.MAX_VALUE,
                    Long.MAX_VALUE, 8, true, true, false);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenMintTokenExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "zXD5vAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB3xw+baYTJUN1AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf/////////8AAAAAAAAAAAAAAAAAkoKY8X2et/IFxVoSf7HUs1owcg==");

            // Execute
            AccountBlockTemplate block = this.api.mintToken(TokenStandard.parse("zts1q803c0nd5cfj2sm4fv0yga"),
                    9223372036854775807L, Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"));

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenBurnTokenExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0", "zts1q803c0nd5cfj2sm4fv0yga", 9223372036854775807L,
                    "M5WrlA==");

            // Execute
            AccountBlockTemplate block = this.api.burnToken(TokenStandard.parse("zts1q803c0nd5cfj2sm4fv0yga"),
                    9223372036854775807L);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }

        @Test
        public void whenUpdateTokenExpectResultToEqual() {
            // Setup
            AccountBlockTemplate expectedResult = TestHelper.createAccountBlockTemplate(
                    "z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0", "zts1znnxxxxxxxxxxxxx9z4ulx", 0,
                    "KjzzLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB3xw+baYTJUN1AAAAAAAAAAAAAAAAAJKCmPF9nrfyBcVaEn+x1LNaMHIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            // Execute
            AccountBlockTemplate block = this.api.updateToken(TokenStandard.parse("zts1q803c0nd5cfj2sm4fv0yga"),
                    Address.parse("z1qzfg9x830k0t0us9c4dpyla36je45vrj8wjlxx"), false, false);

            // Validate
            assertEquals(block.toString(), expectedResult.toString());
        }
    }
}
