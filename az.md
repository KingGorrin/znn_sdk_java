# Zenon Java SDK - Accelerator-Z project proposal

**Description:**

Development of a Zenon SDK written for the standard Java compiler. Having a Java SDK will help us in on-boarding new developers, who in-turn will help expand the Zenon ecosystem.

**URL:** 
https://github.com/KingGorrin/znn_sdk_java/blob/main/az.md

**Team**:

The team consists out of one developer known under the handle @KingGorrin aka CryptoFish. I'm experienced with Java and I've got a complete understanding of the Zenon Dart SDK. In earlier work I've recently released 100% functional implementation of the Zenon .NET SDK. The details and work of the project can be found at https://github.com/KingGorrin/znn_sdk_csharp. The Zenon .NET SDK can be considered my PoW and commitment to the Zenon community.

More of my work related experience can be found here:

https://forum.zenon.org/t/around-the-table/349/4?u=cryptofish

**Goal:**

While the initial goal of this proposal will concentrate on implementing a version of the SDK for the standard Java compiler, extra attention will be made to make it compatible with Kotlin. A general Java coding style will be used and like the Zenon .NET SDK, closely stick with the SDKs provided by the Zenon Core Dev team.

## Phase 1 - API and Models

The goals of this phase is to implement all basic functionality for making RPC calls to the Zenon network. The includes connecting with a RPC client, JSON parsing and making it available through a model.

- Utils
- Primitives
	- SHA3
	- Bech32
- Models
	- NoM
	- Embedded
- API
	- RPC interfaces

## Phase 2 - Contracts

This phase extends the basic API functionality by implementing the contract interfaces and underlying Application Binary Interface.

- Application Binary Interface (Abi)
- API
	- Contract interfaces

## Phase 3 - Wallet

The last phase binds everything together by implementing the wallet functionality and algorithms involved.

This phase will cover all crypto related aspects of the SDK:

- Argon2 (Key derivation and password hashing)
- Ed25519
- Encryption (AES)
- Bip32 (Key structures) and Bip39 (Mnemonic Seed Phrase)
- Wallet (KeyPair, KeyStore, KeyFile)
- PoW

# Funding
Total Requested Funding: 1500 ZNN
Project Duration: 80 to 100 hours

How did you calculate your budget?
Based on my work on the Zenon .NET SDK I can make an accurate estimation of the total amount of work needed.

## Phase 1 - API and Models
Funding Request: 50% (750 ZNN)

Duration: 40-50 hours

## Phase 2 - Contracts
Funding Request: 25% (375 ZNN)

Duration: 20-25 hours

## Phase 3 - Wallet
Funding Request: 25% (375 ZNN)

Duration: 20-25 hours

# Duration
As I am a full time employee and a dad I cannot work full-time on this project. The work on the Zenon .NET SDK took exactly 1 month, but took a tole on my private time. My intentions are to take 3 months of time to finish this project, 1 month for each phase.
