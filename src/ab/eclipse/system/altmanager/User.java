package ab.eclipse.system.altmanager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class User {
    public String name;
    public UUID uuid;

    public User(String name) {
        this.name = name;
        try {
            uuid = resolveUUID(name);
        } catch (Exception exc) {
            uuid = UUID.randomUUID();
        }
    }

    public static UUID resolveUUID(String name) {
        UUID uUID;
        InputStreamReader in;
        try {
            in = new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return UUID.randomUUID();
        }
        try {
            uUID = UUID.fromString(new Gson().fromJson(in, JsonObject.class).get("id").getAsString().replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        } catch (Throwable uuid) {
            try {
                try {
                    in.close();
                }
                catch (Throwable throwable) {
                    uuid.addSuppressed(throwable);
                }
                throw uuid;
            } catch (Throwable ignored) {
                return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uUID;
    }
}
