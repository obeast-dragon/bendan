package com.obeast.auth.json;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private Object headerMap;
    private Object apiMap;

    public Response(Object headerMap, Object apiMap) {
        this.headerMap = headerMap;
        this.apiMap = apiMap;
    }


}