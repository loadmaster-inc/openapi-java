import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class OpenApiDemo {
    public static void main(String[] args) {
        HttpRequest response = HttpRequest.post("https://api.zhuangxiang.com/connect/token")
                .header("Cookie", "Abp.TenantId={your tenant id}")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .send("client_id={your app id}&client_secret={your app secret}&grant_type=password&username={your account name(admin)}&password={your password}");
        JSONParser parser = new JSONParser();
        String access_token = null;
        try {
            access_token = (String) (((JSONObject) (parser.parse(response.body()))).get("access_token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject taskdata = new JSONObject();
        taskdata.put("type", 0);
        JSONObject cargo = new JSONObject();
        cargo.put("name", "cargo1");
        cargo.put("length", 1.1);
        cargo.put("width", 0.8);
        cargo.put("height", 0.6);
        cargo.put("weight", 0.5);
        cargo.put("quantity", 99);
        JSONArray cargoes = new JSONArray();
        cargoes.add(cargo);
        taskdata.put("packingCargoes", cargoes);
        JSONObject container = new JSONObject();
        container.put("name", "20ft");
        container.put("InnerX", 2.35);
        container.put("InnerY", 2.38);
        container.put("InnerZ", 5.89);
        container.put("Maxload", 21000);
        JSONArray containers = new JSONArray();
        containers.add(container);
        taskdata.put("packingContainers", containers);
        taskdata.put("interimContainers", new JSONArray());
        taskdata.put("loadingOptions", new JSONObject());
        taskdata.put("interimOptions", new JSONObject());
        taskdata.put("predefinedModels", new JSONArray());
        taskdata.put("pointedContainers", new JSONArray());
        taskdata.put("skuCargoes", new JSONArray());
        JSONObject inputs = new JSONObject();
        inputs.put("taskData", inputs);
        response = HttpRequest.post("https://openapi.zhuangxiang.com/OptimizeLoadingTask")
                .header("Authorization", "bearer " + access_token).header("content-type", "application/json")
                .send(inputs.toJSONString());
        System.out.println(response.body());
    }
}
