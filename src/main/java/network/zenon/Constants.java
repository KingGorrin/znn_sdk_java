package network.zenon;

import java.time.Duration;
import java.util.regex.Pattern;

public class Constants {
    // Global constants
    public static final String ZNN_SDK_VERSION = "0.0.4";
    public static final String ZNN_ROOT_DIRECTORY = "znn";

    // https://github.com/zenon-network/go-zenon/blob/b2e6a98fa154d763571bb7af6b1c685d0d82497d/zenon/zenon.go#L41
    public static final int NET_ID = 1; // Alphanet network identifier
    public static final int CHAIN_ID = 1; // Alphanet chain identifier

    public static String ZNN_DEFAULT_DIRECTORY = ZnnPaths.DEFAULT.getMain();
    public static String ZNN_DEFAILT_WALLET_DIRECTORY = ZnnPaths.DEFAULT.getWallet();
    public static String ZNN_DEFAULT_CACHE_DIRECTORY = ZnnPaths.DEFAULT.getCache();

    // Client constants
    public static final int DEFAULT_HTTP_PORT = 35997;
    public static final int DEFAULT_WS_PORT = 35998;
    public static final int NUM_RETRIES = 10;
    public static final int RPC_MAX_PAGE_SIZE = 1024;
    public static final int MEMORY_POOL_PAGE_SIZE = 50;

    // NoM constants
    public static final int ZNN_DECIMALS = 8;
    public static final int QSR_DECIMALS = 8;
    public static final long ONE_ZNN = 1 * 100000000;
    public static final long ONE_QSR = 1 * 100000000;
    public static final Duration INTERVAL_BETWEEN_MOMENTUMS = Duration.ofSeconds(10);

    // Embedded constants
    public static final long GENESIS_TIMESTAMP = 1637755200;

    // Plasma
    public static final long FUSE_MIN_QSR_AMOUNT = 10 * ONE_QSR;
    public static final int MIN_PLASMA_AMOUNT = 21000;

    // Pillar
    public static final long PILLAR_REGISTER_ZNN_AMOUNT = 15000 * ONE_ZNN;
    public static final long PILLAR_REGISTER_QSR_AMOUNT = 150000 * ONE_QSR;
    public static final long PILLAR_NAME_MAX_LENGTH = 40;
    public static final Pattern PILLAR_NAME_REG_EXP = Pattern.compile("^([a-zA-Z0-9]+[-._]?)*[a-zA-Z0-9]$");

    // Sentinel
    public static final long SENTINEL_REGISTER_ZNN_AMOUNT = 5000 * ONE_ZNN;
    public static final long SENTINEL_REGISTER_QSR_AMOUNT = 50000 * ONE_QSR;

    // Staking
    public static final long STAKE_MIN_ZNN_AMOUNT = ONE_ZNN;
    public static final long STAKE_TIME_UNIT_SEC = 30 * 24 * 60 * 60;
    public static final long STAKE_TIME_MAX_SEC = 12 * STAKE_TIME_UNIT_SEC;
    public static final String STAKE_UNIT_DURATION_NAME = "month";

    // Token
    public static final long TOKEN_ZTS_ISSUE_FEE_IN_ZNN = ONE_ZNN;
    public static final long TOKEN_NAME_MAX_LENGTH = 40;
    public static final long TOKEN_SYMBOL_MAX_LENGTH = 10;
    public static final String[] TOKEN_SYMBOL_EXCEPTIONS = new String[] { "ZNN", "QSR" };

    public static final Pattern TOKEN_NAME_REG_EXP = Pattern.compile("^([a-zA-Z0-9]+[-._]?)*[a-zA-Z0-9]$");
    public static final Pattern TOKEN_SYMBOL_REG_EXP = Pattern.compile("^[A-Z0-9]+$");
    public static final Pattern TOKEN_DOMAIN_REG_EXP = Pattern
            .compile("^([A-Za-z0-9][A-Za-z0-9-]{0,61}[A-Za-z0-9].)+[A-Za-z]{2,}$");

    // Accelerator
    public static final int PROJECT_DESCRIPTION_MAX_LENGTH = 240;
    public static final int PROJECT_NAME_MAX_LENGTH = 30;
    public static final int PROJECT_CREATION_FEE_IN_ZNN = 1;
    public static final int PROJECT_VOTING_STATUS = 0;
    public static final int PROJECT_ACTIVE_STATUS = 1;
    public static final int PROJECT_PAID_STATUS = 2;
    public static final int PROJECT_CLOSED_STATUS = 3;
    public static final Pattern PROJECT_URL_REG_EXP = Pattern
            .compile("^[a-zA-Z0-9]{2,60}.[a-zA-Z]{1,6}([a-zA-Z0-9()@:%_\\+.~#?&/=-]{0,100})$");

    // Swap
    public static final int SWAP_ASSET_DECAY_TIMESTAMP_START = 1645531200;
    public static final int SWAP_ASSET_DECAY_EPOCHS_OFFSET = 30 * 3;
    public static final int SWAP_ASSET_DECAY_TICK_EPOCHS = 30;
    public static final int SWAP_ASSET_DECAY_TICK_VALUE_PERCENTAGE = 10;
}