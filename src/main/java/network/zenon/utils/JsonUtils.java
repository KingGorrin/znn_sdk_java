package network.zenon.utils;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.TypeLiteral;

public class JsonUtils {
    public static String serialize(Object value) {
        return JsonStream.serialize(value);
    }
    
    public static Any deserializeAny(String value) {
        return JsonIterator.deserialize(value);
    }
    
    public static <T> T deserialize(String value, TypeLiteral<T> typeLiteral) {
        return JsonIterator.deserialize(value).as(typeLiteral);
    }
    
    public static <T> T deserialize(String value, Class<T> clazz) {
        return JsonIterator.deserialize(value).as(clazz);
    }
}
