package com.github.dreamhead.moco.parser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.dreamhead.moco.MocoEventAction;
import com.google.common.base.Objects;

import static com.github.dreamhead.moco.Moco.get;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GetSetting {
    private TextContainer url;

    public MocoEventAction createAction() {
        return get(url.asResource());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .omitNullValues()
                .add("url", url)
                .toString();
    }
}
