package com.los.revotask.util

import com.los.revotask.model.user.User
import spock.lang.Specification

class JsonUtilsSpec extends Specification {
    
    void 'Should marshallize entity to Json'() {
        setup :
            User user = new User('testUser')
            String expectedJson = '{"userId":0,"userName":"testUser","account":{"accountId":0,"accountName":"Main account","balance":0}}'
        when:
            String result = JsonUtils.toJson(user)
        then:
            result == expectedJson
    }
}
