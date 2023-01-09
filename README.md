# Zenon SDK for Java

[![build](https://img.shields.io/github/actions/workflow/status/KingGorrin/znn_sdk_java/build.yml?branch=main)](https://github.com/KingGorrin/znn_sdk_java/actions/workflows/build.yml) [![codecov](https://img.shields.io/codecov/c/github/KingGorrin/znn_sdk_java?token=8WB4pa15fM)](https://codecov.io/gh/KingGorrin/znn_sdk_java) ![GitHub](https://img.shields.io/github/license/KingGorrin/znn_sdk_java)

Reference implementation for the Zenon SDK for Java. Compatible with the Zenon Alphanet - Network of Momentum Phase 1. 
It provides a simple integration with any Java based projects.

## Requirements

- JDK 1.8 or higher.


## Download and Install

To download and install Zenon SDK you currently have the following options.

### JAR

Download the following JARs and add them to your classpath:

- `znn-java-sdk-0.0.3`

## Usage

### Connect Node

```java
import network.zenon.Zenon;

Zenon.getInstance().getClient().connect("ws://nodes.zenon.place:35998");
...
Zenon.getInstance().getClient().close();
```

### Generate wallet

```java
import network.zenon.Zenon;

String wallet = "name";
String passphrase = "secret";

Zenon znn = Zenon.getInstance();

znn.defaultKeyStorePath = 
	znn.getKeyStoreManager().createNew(passphrase, wallet);
znn.defaultKeyStore = 
	znn.getKeyStoreManager().readKeyStore(passphrase, znn.defaultKeyStorePath);
```

### Generate wallet from mnemonic

```java
import network.zenon.Zenon;

String wallet = "name";
String passphrase = "secret";
String mnemonic =
      "route become dream access impulse price inform obtain engage ski believe awful absent pig thing vibrant possible exotic flee pepper marble rural fire fancy";

Zenon znn = Zenon.getInstance();
      
znn.defaultKeyStorePath = 
	znn.getKeyStoreManager().createFromMnemonic(mnemonic, passphrase, wallet);
znn.defaultKeyStore = 
	znn.getKeyStoreManager().readKeyStore(passphrase, znn.defaultKeyStorePath);
```

### Sending a transaction

```java
import network.zenon.Zenon;

String wallet = "name";
String passphrase = "secret";
String mnemonic = "route become dream access impulse price inform obtain engage ski believe awful absent pig thing vibrant possible exotic flee pepper marble rural fire fancy";

Zenon znn = Zenon.getInstance();

znn.defaultKeyStorePath = 
        znn.getKeyStoreManager().createFromMnemonic(mnemonic, passphrase, wallet);
znn.defaultKeyStore = 
        znn.getKeyStoreManager().readKeyStore(passphrase, znn.defaultKeyStorePath);
znn.defaultKeyPair = 
        znn.defaultKeyStore.getKeyPair(); // Use primary address

znn.getClient().connect("ws://192.168.1.112:35998");

znn.send(znn.getEmbedded().getPillar().collectReward());

znn.getClient().close();
```

### API

```java
import network.zenon.Zenon;
import network.zenon.model.primitives.Address;
import network.zenon.model.nom.AccountInfo;

// Connect to node
Zenon.getInstance().getClient()
	.connect("ws://nodes.zenon.place:35998");

// Create an address
Address address = Address
	.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402");

// Get account info by address
AccountInfo info = Zenon.getInstance().getLedger()
	.getAccountInfoByAddress(address);

// Close node connection
Zenon.getInstance().getClient().close();

// Print account info
System.out.println("AccountInfo: " + info.toString());
```

## Contributing

Please check [CONTRIBUTING](./CONTRIBUTING.md) for more details.

## License

The MIT License (MIT). Please check [LICENSE](./LICENSE) for more information.
