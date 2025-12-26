package com.security.auth_app_backend.helpers;

import java.util.UUID;

public class UserHelper {
    public static UUID parseUuid(String uuid){
        return UUID.fromString(uuid);
    }
}
