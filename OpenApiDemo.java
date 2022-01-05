import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class OpenApiDemo {
    public static void main(String[] args) {
        HttpRequest response = HttpRequest.post("https://api.zhuangxiang.com/connect/token") // URL
                .header("Cookie", "Abp.TenantId={your tenant id}") // 设置Cookie，使用租户id
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8") // 设置HTTP Body类型
                .send("client_id={your app id}&client_secret={your app secret}&grant_type=password&username={your account name(admin)}&password={your password}"); // 填写app-id、app-secret、账号及密码，然后发送请求
        JSONParser parser = new JSONParser(); // Json解析器
        String access_token = null;
        try {
            access_token = (String) (((JSONObject) (parser.parse(response.body()))).get("access_token")); // 从响应中获取access_token
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject taskdata = new JSONObject(); // 构造要计算的数据
        taskdata.put("type", 0);
        JSONObject cargo = new JSONObject(); // 货物数据
        cargo.put("name", "cargo1");
        cargo.put("length", 1.1);
        cargo.put("width", 0.8);
        cargo.put("height", 0.6);
        cargo.put("weight", 0.5);
        cargo.put("quantity", 99);
        JSONArray cargoes = new JSONArray();
        cargoes.add(cargo);
        taskdata.put("packingCargoes", cargoes);
        JSONObject container = new JSONObject(); // 容器数据
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
        inputs.put("taskData", taskdata);
        response = HttpRequest.post("https://openapi.zhuangxiang.com/OptimizeLoadingTask")
                .header("Authorization", "bearer " + access_token).header("content-type", "application/json")
                .send(inputs.toJSONString()); // 发送请求，使用access_token进行认证，Body为要计算的数据
        System.out.println(response.body());
    }
}
