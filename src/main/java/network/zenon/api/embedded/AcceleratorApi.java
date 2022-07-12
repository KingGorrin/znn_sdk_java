package network.zenon.api.embedded;

import java.util.ArrayList;
import java.util.List;

import com.jsoniter.spi.TypeLiteral;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.Phase;
import network.zenon.model.embedded.PillarVote;
import network.zenon.model.embedded.Project;
import network.zenon.model.embedded.ProjectList;
import network.zenon.model.embedded.VoteBreakdown;
import network.zenon.model.embedded.json.JPhase;
import network.zenon.model.embedded.json.JPillarVote;
import network.zenon.model.embedded.json.JProject;
import network.zenon.model.embedded.json.JProjectList;
import network.zenon.model.embedded.json.JVoteBreakdown;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.AmountUtils;
import network.zenon.utils.JsonUtils;

public class AcceleratorApi {
    private final Client client;

    public AcceleratorApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public ProjectList getAll() {
        return this.getAll(0, Constants.RPC_MAX_PAGE_SIZE);
    }

    public ProjectList getAll(int pageIndex, int pageSize) {
        JProjectList response = this.client.sendRequest("embedded.accelerator.getAll",
                new Object[] { pageIndex, pageSize }, JProjectList.class);
        return new ProjectList(response);
    }

    public Project getProjectById(Hash id) {
        JProject response = this.client.sendRequest("embedded.accelerator.getProjectById",
                new Object[] { id.toString() }, JProject.class);
        return new Project(response);
    }

    public Phase getPhaseById(Hash id) {
        Object response = this.client.sendRequest("embedded.accelerator.getPhaseById", new Object[] { id.toString() });
        JPhase result = JPhase.fromAny(JsonUtils.deserializeAny(response.toString()));
        return new Phase(result);
    }

    public List<PillarVote> getPillarVotes(String name, String[] hashes) {
        Object response = this.client.sendRequest("embedded.accelerator.getPillarVotes", new Object[] { name, hashes });
        List<JPillarVote> result = JsonUtils.deserialize(response.toString(), new TypeLiteral<List<JPillarVote>>() {
        });
        List<PillarVote> list = new ArrayList<>();
        for (JPillarVote item : result) {
            list.add(new PillarVote(item));
        }
        return list;
    }

    public VoteBreakdown getVoteBreakdown(Hash id) {
        JVoteBreakdown response = this.client.sendRequest("embedded.accelerator.getVoteBreakdown",
                new Object[] { id.toString() }, JVoteBreakdown.class);
        return new VoteBreakdown(response);
    }

    // Contract methods
    public AccountBlockTemplate createProject(String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, TokenStandard.ZNN_ZTS,
                AmountUtils.extractDecimals(Constants.PROJECT_CREATION_FEE_IN_ZNN, Constants.ZNN_DECIMALS),
                Definitions.ACCELERATOR.encodeFunction("CreateProject", name, description, url, znnFundsNeeded,
                        qsrFundsNeeded));
    }

    public AccountBlockTemplate addPhase(Hash id, String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.ACCELERATOR.encodeFunction("AddPhase", id.getBytes(), name, description, url,
                        znnFundsNeeded, qsrFundsNeeded));
    }

    public AccountBlockTemplate updatePhase(Hash id, String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.ACCELERATOR.encodeFunction("UpdatePhase", id.getBytes(), name, description, url,
                        znnFundsNeeded, qsrFundsNeeded));
    }

    public AccountBlockTemplate donate(long amount, TokenStandard zts) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, zts, amount,
                Definitions.ACCELERATOR.encodeFunction("Donate"));
    }

    public AccountBlockTemplate voteByName(Hash id, String pillarName, int vote) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.ACCELERATOR.encodeFunction("VoteByName", id.getBytes(), pillarName, vote));
    }

    public AccountBlockTemplate voteByProdAddress(Hash id, int vote) {
        return AccountBlockTemplate.callContract(Address.ACCELERATOR_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.ACCELERATOR.encodeFunction("VoteByProdAddress", id.getBytes(), vote));
    }
}