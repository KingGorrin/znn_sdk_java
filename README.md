# Zenon SDK for Java

[![build](https://img.shields.io/github/workflow/status/KingGorrin/znn_sdk_java/build)](https://github.com/KingGorrin/znn_sdk_java/actions/workflows/build.yml) [![codecov](https://img.shields.io/codecov/c/github/KingGorrin/znn_sdk_java?token=8WB4pa15fM)](https://codecov.io/gh/KingGorrin/znn_sdk_java)

Reference implementation for the Zenon SDK for Java. Compatible with the Zenon Alphanet - Network of Momentum Phase 0. 
It provides a simple integration with any Java based projects.

## Installation

## Usage

### API

```java
import network.zenon.Zenon;
import network.zenon.model.primitives.Address;
import network.zenon.model.nom.AccountInfo;

// Connect to node
Zenon.getClient()
	.connect("ws://nodes.zenon.place:35998");

// Create an address
Address address = Address
	.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402");

// Get account info by address
AccountInfo info = Zenon.getLedger()
	.getAccountInfoByAddress(address);

// Close node connection
Zenon.getClient().close();

// Print account info
System.out.println("AccountInfo: " + info.toString());
```

## Contributing

Please check [CONTRIBUTING](./CONTRIBUTING.md) for more details.

## License

The MIT License (MIT). Please check [LICENSE](./LICENSE) for more information.
