package network.zenon.utils;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import com.jsoniter.JsonIterator;

public final class JsonConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        return JsonIterator.deserialize(source.toString(), targetType);
    }
}
