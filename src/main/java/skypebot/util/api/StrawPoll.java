package skypebot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrawPoll {

    private static final Gson gson = new Gson();

    @Getter
    private String title;
    @Getter
    private Map<String, Integer> options;
    @Getter
    private boolean multivoting, captcha;
    @Getter
    private int id;


    public StrawPoll(String title, List<String> options) {
        this.title = title;
        this.options = new HashMap<>();
        options.forEach(o -> this.options.put(o, 0));
    }

    public StrawPoll(int id) {
        this.id = id;
    }

    public void create() {
        JsonObject obj = new JsonObject();
        obj.addProperty("title", title);
        JsonArray arr = new JsonArray();
        options.forEach((k, v) -> arr.add(new JsonPrimitive(k.replace("\n", ""))));
        obj.add("options", arr);
        obj.addProperty("multi", multivoting);
        obj.addProperty("permissive", false);

        REST rest = new REST("http://strawpoll.me/api/v2/polls");
        rest.setRequestProperty("Content-Type", "application/json");
        rest.setPayload(gson.toJson(obj));
        System.out.println(rest.getAsJsonObject());
        id = rest.getAsJsonObject().get("id").getAsInt();
    }

    public void refresh() {
        REST rest = new REST("https://strawpoll.me/api/v2/polls/" + id);
        JsonObject obj = rest.getAsJsonObject();
        if (title == null) {
            title = obj.get("title").getAsString();
        }
        options = new HashMap<>();
        JsonArray picks = obj.getAsJsonArray("options");
        JsonArray votes = obj.getAsJsonArray("votes");
        for (int i = 0; i < picks.size(); i++) {
            options.put(picks.get(i).getAsString(), votes.get(i).getAsInt());
        }
        multivoting = obj.get("multi").getAsBoolean();
        captcha = obj.get("captcha").getAsBoolean();
    }

    public String getUrl() {
        return "http://strawpoll.me/" + id;
    }
}