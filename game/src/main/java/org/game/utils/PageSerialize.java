package org.game.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageSerialize extends StdSerializer<Page> {

    public PageSerialize(){ this(null);}

    public PageSerialize(Class<Page> t){
        super(t);
    }


    @Override
    public void serialize(Page value, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("total", value.getTotalElements());
        json.writeNumberField("offset", value.getPageable().getOffset());
        json.writeNumberField("limit", value.getPageable().getPageSize());
        json.writeNumberField("actual_page", value.getPageable().getPageNumber());
        json.writeBooleanField("first", value.isFirst());
        json.writeBooleanField("last", value.isLast());
        json.writeFieldName("data");
        provider.defaultSerializeValue(value.getContent(), json);
        json.writeEndObject();
    }

}
