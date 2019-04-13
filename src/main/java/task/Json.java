package task;


import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Json {

    public static JSONRPC2Request createQuerry(int numsInQuerry) {
        String method = "generateIntegers";
        Map<String, Object> params = new HashMap<>();
        params.put("apiKey", "b37b6a35-5b07-446c-bf8b-cb2218150e5e");
        params.put("n", numsInQuerry);
        params.put("min", 0);
        params.put("max", 1);
        params.put("replacement", true);
        String id = "42";

        JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
        //System.out.println(reqOut);

        return reqOut;
    }

    public static JSONArray callQuerry(int numsInQuerry){
        // The JSON-RPC 2.0 server URL
        URL serverURL = null;
        try {
            serverURL = new URL("https://api.random.org/json-rpc/2/invoke");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create new JSON-RPC 2.0 client session
        JSONRPC2Session mySession = new JSONRPC2Session(serverURL);

        JSONRPC2Response response = null;

        try {
            response = mySession.send(createQuerry(numsInQuerry));

        } catch (JSONRPC2SessionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        if (response.indicatesSuccess()) {
            //System.out.println(response.getResult());
            JSONObject obj = (JSONObject) response.getResult();
            Map map = (Map)obj.get("random");
            JSONArray arr = (JSONArray)map.get("data");
            //System.out.println(arr);
            return arr;
        }
        else
            System.out.println(response.getError().getMessage());
        return null;
    }
}
