package network.zenon.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import network.zenon.TestHelper;
import network.zenon.model.embedded.Project;
import network.zenon.model.embedded.ProjectList;
import network.zenon.model.embedded.json.JProject;
import network.zenon.model.embedded.json.JProjectList;
import network.zenon.model.nom.AccountBlock;
import network.zenon.model.nom.AccountBlockList;
import network.zenon.model.nom.AccountInfo;
import network.zenon.model.nom.DetailedMomentumList;
import network.zenon.model.nom.Momentum;
import network.zenon.model.nom.MomentumList;
import network.zenon.model.nom.TokenList;
import network.zenon.model.nom.json.JAccountBlock;
import network.zenon.model.nom.json.JAccountBlockList;
import network.zenon.model.nom.json.JAccountInfo;
import network.zenon.model.nom.json.JDetailedMomentumList;
import network.zenon.model.nom.json.JMomentum;
import network.zenon.model.nom.json.JMomentumList;
import network.zenon.model.nom.json.JTokenList;

@DisplayName("Model Tests")
public class ModelTest {
    private static <D, M> List<Arguments> getArguments(String resourceName, Class<D> dataType, Class<M> modelType)
            throws IOException {

        String json = TestHelper.getRosourceText(resourceName);

        Any jsonArray = JsonIterator.deserialize(json);

        List<Arguments> list = new ArrayList<>();

        for (Any item : jsonArray.asList()) {
            list.add(Arguments.of(item.toString(), dataType, modelType));
        }

        return list;
    }

    private static Stream<Arguments> modelTestData() throws IOException {
        return Stream
                .of(getArguments("model/embedded/project.json", JProject.class, Project.class),
                        getArguments("model/embedded/projectList.json", JProjectList.class, ProjectList.class),
                        getArguments("model/nom/accountBlock.json", JAccountBlock.class, AccountBlock.class),
                        getArguments("model/nom/accountBlockList.json", JAccountBlockList.class,
                                AccountBlockList.class),
                        getArguments("model/nom/accountInfo.json", JAccountInfo.class, AccountInfo.class),
                        getArguments("model/nom/detailedMomentumList.json", JDetailedMomentumList.class,
                                DetailedMomentumList.class),
                        getArguments("model/nom/momentum.json", JMomentum.class, Momentum.class),
                        getArguments("model/nom/momentumList.json", JMomentumList.class, MomentumList.class),
                        getArguments("model/nom/tokenList.json", JTokenList.class, TokenList.class))
                .flatMap(Collection::stream);
    }

    @ParameterizedTest
    @MethodSource(value = "modelTestData")
    public void whenCreateModelExpectSuccess(String originalJson, Class<?> dataType, Class<?> modelType) {
        // Execute
        Object data = JsonIterator.deserialize(originalJson, dataType);

        // Validate
        assertDoesNotThrow(() -> {
            modelType.getDeclaredConstructor(dataType).newInstance(data);
        });
    }

    @ParameterizedTest
    @MethodSource(value = "modelTestData")
    public void whenConvertModelExpectSuccess(String originalJson, Class<?> dataType, Class<?> modelType) {
        // Execute
        assertDoesNotThrow(() -> {
            Object data = JsonIterator.deserialize(originalJson, dataType);
            Method toJson = modelType.getMethod("toJson");
            Object model = modelType.getDeclaredConstructor(dataType).newInstance(data);
            Object json = toJson.invoke(model);
        });
    }
}