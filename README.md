# Zenon Sdk for Java

[![build](https://img.shields.io/github/workflow/status/kingGorrin/znn_sdk_java/Zenon.Sdk.Java)](https://github.com/KingGorrin/znn_sdk_java/actions/workflows/build.yml) [![codecov](https://img.shields.io/codecov/c/github/KingGorrin/znn_sdk_java?token=8WB4pa15fM)](https://codecov.io/gh/KingGorrin/znn_sdk_java)

Reference implementation for the Zenon SDK for Java. Compatible with the Zenon Alphanet - Network of Momentum Phase 0. 
It provides a simple integration with any Java based projects.

## Installation

## Usage

### API

```java
import network.zenon.api.LedgerApi;
import network.zenon.client.WsClient;
import network.zenon.model.nom.AccountInfo;
import network.zenon.model.primitives.Address;

String url = "ws://nodes.zenon.place:35998";
Address address = Address.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402");

WsClient client = new WsClient(url);
client.connect();
			
LedgerApi ledger = new LedgerApi(client);
AccountInfo info = ledger.getAccountInfoByAddress(address);
			
System.out.println("AccountInfo: " + info.toString());
			
client.close();
```

## Contributing

Please check [CONTRIBUTING](./CONTRIBUTING.md) for more details.

## License

The MIT License (MIT). Please check [LICENSE](./LICENSE) for more information.
