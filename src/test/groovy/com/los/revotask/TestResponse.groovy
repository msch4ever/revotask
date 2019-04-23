package com.los.revotask

import com.google.gson.Gson

class TestResponse {
    
    final String body
    final int status
    
    TestResponse(int status, String body) {
        this.status = status
        this.body = body
    }
    
    Map<String, String> json() {
        return new Gson().fromJson(body, HashMap.class)
    }
}