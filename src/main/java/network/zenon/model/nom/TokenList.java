package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import network.zenon.model.JsonConvertible;
import network.zenon.model.nom.json.JTokenList;
import network.zenon.utils.JsonUtils;

public class TokenList implements JsonConvertible<JTokenList> {
    private final long count;
    private final List<Token> list;

    public TokenList(JTokenList json) {
        this.count = json.count;
        this.list = json.list != null
                ? Collections.unmodifiableList(json.list.stream().map(x -> new Token(x)).collect(Collectors.toList()))
                : Collections.emptyList();
    }

    public TokenList(long count, List<Token> list) {
        this.count = count;
        this.list = Collections.unmodifiableList(list);
    }

    public long getCount() {
        return this.count;
    }

    public List<Token> getList() {
        return this.list;
    }

    @Override
    public JTokenList toJson() {
        JTokenList json = new JTokenList();
        json.count = this.count;
        json.list = this.list.stream().map(Token::toJson).collect(Collectors.toList());
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}