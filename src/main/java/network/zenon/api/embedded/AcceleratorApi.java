package network.zenon.api.embedded;

import java.io.IOException;
import java.util.List;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;

import network.zenon.Constants;
import network.zenon.client.IClient;
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
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;

public class AcceleratorApi {
    private final IClient client;

    public AcceleratorApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
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
        try {
            Object response = this.client.sendRequest("embedded.accelerator.getPhaseById",
                    new Object[] { id.toString() });
            JsonIterator iterator = JsonIterator.parse(response.toString());
            return new Phase(JPhase.fromJObject(iterator.readAny()));
        } catch (IOException e) {
            return null;
        }
    }

    public List<PillarVote> getPillarVotes(String name, String[] hashes) {
        try {
            Object response = this.client.sendRequest("embedded.accelerator.getPillarVotes",
                    new Object[] { name, hashes });
            JsonIterator iterator = JsonIterator.parse(response.toString());
            List<JPillarVote> result = iterator.read(new TypeLiteral<List<JPillarVote>>() {
            });
            return result.stream().map(x -> new PillarVote(x)).toList();
        } catch (IOException e) {
            return null;
        }
    }

    public VoteBreakdown getVoteBreakdown(Hash id) {
        JVoteBreakdown response = this.client.sendRequest("embedded.accelerator.getVoteBreakdown",
                new Object[] { id.toString() }, JVoteBreakdown.class);
        return new VoteBreakdown(response);
    }

    // Contract methods
    public AccountBlockTemplate CreateProject(String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate AddPhase(Hash id, String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate UpdatePhase(Hash id, String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate Donate(long amount, TokenStandard zts) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate VoteByName(Hash id, String pillarName, int vote) {
        throw new UnsupportedOperationException();
    }

    public AccountBlockTemplate VoteByProdAddress(Hash id, int vote) {
        throw new UnsupportedOperationException();
    }
}