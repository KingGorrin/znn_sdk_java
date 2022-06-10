# Zenon Sdk for Java

Reference implementation for the Zenon SDK for Java. Compatible with the Zenon Alphanet - Network of Momentum Phase 0. 
It provides a simple integration with any Java based projects.

## Installation

## Usage

### API

```java
package network.zenon;

import network.zenon.api.LedgerApi;
import network.zenon.client.WsClient;
import network.zenon.model.nom.AccountInfo;
import network.zenon.model.primitives.Address;

import java.io.IOException;

String url = "ws://nodes.zenon.place:35998";
Address address = Address.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402");

WsClient client = new WsClient(url);
client.connect();
			
LedgerApi ledger = new LedgerApi(client);
AccountInfo info = ledger.GetAccountInfoByAddress(address);
			
System.out.println("AccountInfo: " + info.toString());
			
client.close();
```

## Contributing

Please check [CONTRIBUTING](./CONTRIBUTING.md) for more details.

## License

The MIT License (MIT). Please check [LICENSE](./LICENSE) for more information.
