package sample.netroid.vincestyling.com.commondemo.network.toolbox;


import sample.netroid.vincestyling.com.commondemo.network.utils.Network;
import sample.netroid.vincestyling.com.commondemo.network.utils.RequestQueue;

public class Volley {

    public static RequestQueue newRequestQueue() {
        Network network = new BasicNetwork(new HttpUrlConnectionStack());
        RequestQueue queue = new RequestQueue(network);
        queue.start();
        return queue;
    }

}
