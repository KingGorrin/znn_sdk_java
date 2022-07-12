package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JProjectList;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class ProjectList implements JsonConvertible<JProjectList> {
    private final long count;
    private final List<Project> list;

    public ProjectList(JProjectList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new Project(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public ProjectList(long count, List<Project> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<Project> getList() {
        return this.list;
    }

    @Override
    public JProjectList toJson() {
        JProjectList json = new JProjectList();
        json.count = this.count;
        json.list = this.list.stream().map(Project::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }

    public Project findId(Hash id) {
        return this.list.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public Project findProjectByPhaseId(Hash id) {
        for (int i = 0; i < this.list.size(); i++) {
            for (int j = 0; j < this.list.get(i).getPhaseIds().size(); i++) {
                if (id == this.list.get(i).getPhaseIds().get(j))
                    return this.list.get(i);
            }
        }
        return null;
    }
}
