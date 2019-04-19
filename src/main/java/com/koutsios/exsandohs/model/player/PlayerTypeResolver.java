package com.koutsios.exsandohs.model.player;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.koutsios.exsandohs.exception.PlayerTypeResolverException;
import java.io.IOException;

public class PlayerTypeResolver extends TypeIdResolverBase {
  @Override
  public String idFromValue(Object value) {
    return ((Player) value).getPlayerType().toString();
  }

  @Override
  public String idFromValueAndType(Object value, Class<?> suggestedType) {
    throw new PlayerTypeResolverException("required for serialization only");
  }

  @Override
  public JavaType typeFromId(DatabindContext context, String id) throws IOException {
    switch (id) {
      case "HUMAN":
        return context.getTypeFactory().constructType(new TypeReference<HumanPlayer>() {
        });
      case "DUMB":
        return context.getTypeFactory().constructType(new TypeReference<DumbComputerPlayer>() {
        });
      default:
        throw new IllegalArgumentException(id + " not known");
    }
  }

  @Override
  public JsonTypeInfo.Id getMechanism() {
    return JsonTypeInfo.Id.CUSTOM;
  }
}
